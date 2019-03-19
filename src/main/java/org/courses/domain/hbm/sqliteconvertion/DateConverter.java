package org.courses.domain.hbm.sqliteconvertion;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DateConverter implements AttributeConverter<DateTime, String> {
    @Override
    public String convertToDatabaseColumn(DateTime dateTime) {
        if (null == dateTime)
            return null;

        return dateTime
                .withZone(DateTimeZone.UTC)
                .toString();
    }

    @Override
    public DateTime convertToEntityAttribute(String s) {
        if (null == s || "" == s)
            return null;

        return DateTime.parse(s);
    }
}
