package me.aroze.colornames.reader.impl;

import me.aroze.colornames.ColorNames;
import me.aroze.colornames.NamedColor;
import me.aroze.colornames.reader.ColorFileReader;
import me.aroze.colornames.util.ColorUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVColorFileReader extends ColorFileReader {
    private static final int SIZE = 32768;

    private final InputStreamReader inputStreamReader;

    public CSVColorFileReader(final InputStreamReader inputStreamReader) {
        this.inputStreamReader = inputStreamReader;
    }

    @Override
    public List<NamedColor> read() {
        final List<NamedColor> colors = new ArrayList<>();
        String line;
        boolean isFirstLine = true;

        try(final BufferedReader buffered = new BufferedReader(inputStreamReader, SIZE)) {
            while((line = buffered.readLine()) != null) {
                if(isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                int commaIndex = line.indexOf(',');
                String name = line.substring(0, commaIndex);
                String hex = line.substring(commaIndex + 1);
                colors.add(new NamedColor(name, hex));
            }

            return colors;
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }


//    @Override
//    public void read(InputStreamReader reader) {
//        try(final BufferedReader buffered = new BufferedReader(reader, 32768)) {
//            List<NamedColor> colors = new ArrayList<>();
//
//            String line;
//            boolean isFirstLine = true;
//            while((line = buffered.readLine()) != null) {
//                if(isFirstLine) {
//                    isFirstLine = false;
//                    continue;
//                }
//
//                int commaIndex = line.indexOf(',');
//                String name = line.substring(0, commaIndex);
//                String hex = line.substring(commaIndex + 1);
//
//                double[] lab = ColorUtil.hexToLAB(hex);
//                colors.add(new NamedColor(
//                    name,
//
//                ))
//            }
//
//        } catch(IOException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
}
