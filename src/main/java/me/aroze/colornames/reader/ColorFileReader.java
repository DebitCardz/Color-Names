package me.aroze.colornames.reader;

import me.aroze.colornames.ColorNames;
import me.aroze.colornames.NamedColor;

import java.util.List;

public abstract class ColorFileReader {
    public abstract List<NamedColor> read();
}
