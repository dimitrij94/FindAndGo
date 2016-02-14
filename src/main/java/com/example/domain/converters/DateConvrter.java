package com.example.domain.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by Dmitrij on 07.02.2016.
 */
@Converter(autoApply = true)
public class DateConvrter implements AttributeConverter<Date,LocalDate>{

    @Override
    public LocalDate convertToDatabaseColumn(Date attribute) {
        return attribute==null? null : attribute.toLocalDate();
    }

    @Override
    public Date convertToEntityAttribute(LocalDate dbData) {
        return dbData==null ? null : Date.valueOf(dbData);
    }
}
