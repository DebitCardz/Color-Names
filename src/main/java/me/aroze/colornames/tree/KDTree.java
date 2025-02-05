package me.aroze.colornames.tree;

import me.aroze.colornames.NamedColor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class KDTree {
    public static Node createTree(final Collection<NamedColor> colors, int depth) {
        if (colors.isEmpty()) {
            return null;
        }

        int axis = depth % 3;
        List<NamedColor> points = new ArrayList<>(colors);
        Comparator<NamedColor> comparator = switch (axis) {
            case 0 -> Comparator.comparingDouble(NamedColor::getLightness);
            case 1 -> Comparator.comparingDouble(NamedColor::getAComponent);
            default -> Comparator.comparingDouble(NamedColor::getBComponent); // 2?
        };

        points.sort(comparator);
        
        int mid = points.size() / 2;
        NamedColor medianColor = points.get(mid);
        Node node = new Node(medianColor);

        List<NamedColor> leftPoints = points.subList(0, mid);
        node.setLeft(createTree(leftPoints, depth + 1));

        List<NamedColor> rightPoints = points.subList(mid + 1, points.size());
        node.setRight(createTree(rightPoints, depth + 1));

        return node;
    }

    public static class Node {
        private final NamedColor color;
        private Node left;
        private Node right;

        public Node(final NamedColor color) {
            this.color = color;
        }

        public NamedColor getColor() {
            return color;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }
}