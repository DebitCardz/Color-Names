package me.aroze.colornames;

import me.aroze.colornames.reader.ColorFileReader;
import me.aroze.colornames.reader.impl.CSVColorFileReader;
import me.aroze.colornames.tree.KDTree;
import me.aroze.colornames.util.ColorUtil;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

public class ColorNames {
    private static final String CSV_FILE_NAME = "colornames.csv";

    private KDTree.Node root;

    private final ColorFileReader reader;
    private final AtomicBoolean initialized = new AtomicBoolean(false);

    public ColorNames() {
        super();

        final InputStream inputStream = getClass().getResourceAsStream(File.separator + CSV_FILE_NAME);
        if(inputStream == null) {
            throw new RuntimeException("Could not find " + CSV_FILE_NAME);
        }

        final InputStreamReader reader = new InputStreamReader(inputStream);
        this.reader = new CSVColorFileReader(reader);
    }

    public ColorNames(final ColorFileReader reader) {
        this.reader = reader;
    }


    /**
     * Initialize Color Names.
     * @throws IllegalStateException if color names has already been initialized.
     */
    public void initialize() {
        if(!initialized.compareAndSet(false, true)) {
            throw new IllegalStateException("Color Names has already been initialized.");
        }

        this.root = KDTree.createTree(
            reader.read(),
            0
        );
    }

    public String closestColorName(float lightness, float aComponent, float bComponent) {
        SearchContext ctx = new SearchContext();
        ctx.searchNearest(this.root, lightness, aComponent, bComponent, 0);

        KDTree.Node bestNode = ctx.getBestNode();
        if(bestNode == null) {
            throw new IllegalStateException("No colors found.");
        }

        return bestNode.getColor().getName();
    }

    public String closestColorName(int r, int g, int b) {
        float[] lab = ColorUtil.rgbToLAB(r, g, b);
        return this.closestColorName(lab[0], lab[1], lab[2]);
    }

    public String closestColorName(String hexString) {
        String parsedHexString = hexString;
        if(!parsedHexString.startsWith("#")) {
            parsedHexString = "#" + parsedHexString;
        }

        float[] lab = ColorUtil.hexToLAB(parsedHexString);
        return this.closestColorName(lab[0], lab[1], lab[2]);
    }

    public static class SearchContext {
        private double minimumDistance = Float.POSITIVE_INFINITY;
        private KDTree.Node bestNode;

        public void searchNearest(
            KDTree.Node node,
            float lightness,
            float aComponent,
            float bComponent,
            int depth
        ) {
            if(node == null) {
                return;
            }

            int axis = depth % 3;
            float dim = switch(axis) {
                case 0 -> lightness - node.getColor().getLightness();
                case 1 -> aComponent - node.getColor().getAComponent();
                default -> bComponent - node.getColor().getBComponent();
            };

            double dist = Math.sqrt(
                (lightness - node.getColor().getLightness()) * (lightness - node.getColor().getLightness()) +
                    (aComponent - node.getColor().getAComponent()) * (aComponent - node.getColor().getAComponent()) +
                    (bComponent - node.getColor().getBComponent()) * (bComponent - node.getColor().getBComponent())
            );

            if(dist < minimumDistance) {
                minimumDistance = dist;
                bestNode = node;
            }

            KDTree.Node first;
            if(dim <= 0) {
                first = node.getLeft();
            } else {
                first = node.getRight();
            }

            KDTree.Node second;
            if(dim <= 0) {
                second = node.getRight();
            } else {
                second = node.getLeft();
            }

            this.searchNearest(first, lightness, aComponent, bComponent, depth + 1);
            if(dim * dim < minimumDistance) {
                this.searchNearest(second, lightness, aComponent, bComponent, depth + 1);
            }
        }

        public KDTree.Node getBestNode() {
            return bestNode;
        }
    }
}