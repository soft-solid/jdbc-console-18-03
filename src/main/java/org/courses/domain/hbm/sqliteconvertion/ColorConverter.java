package org.courses.domain.hbm.sqliteconvertion;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.awt.*;

@Converter
public class ColorConverter implements AttributeConverter<Color, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Color color) {
        if (null == color)
            return null;

        return color.getRGB();
    }

    @Override
    public Color convertToEntityAttribute(Integer integer) {
        if (null == integer)
            return null;

        return new Color(integer);
    }
}
