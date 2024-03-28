package com.hotel.converter;

import com.hotel.exception_handler.exception.InvalidDataFormatException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;


@Component
public class StringToInstantConverter implements Converter<String, Instant> {

    @Override
    public Instant convert(String stringDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(stringDate).toInstant();
        } catch (ParseException e) {
            //TODO: InvalidDateFormatExc
            throw new InvalidDataFormatException(stringDate,e);
        }

    }
}

