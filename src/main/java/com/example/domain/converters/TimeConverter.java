package com.example.domain.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Time;
import java.time.LocalTime;

/**
 * Created by Dmitrij on 07.02.2016.
 */
@Converter(autoApply = true)
public class TimeConverter implements AttributeConverter<Time,LocalTime>{

    @Override
    public LocalTime convertToDatabaseColumn(Time attribute) {
        return attribute!=null ? attribute.toLocalTime():null;
    }

    @Override
    public Time convertToEntityAttribute(LocalTime dbData) {
        return dbData!=null ? java.sql.Time.valueOf(dbData):null;
    }
}
