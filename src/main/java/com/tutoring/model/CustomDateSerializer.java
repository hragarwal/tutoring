package com.tutoring.model;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Created by himanshu.agarwal on 19-03-2017.
 */
public class CustomDateSerializer extends JsonSerializer<Date> {

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ISO_INSTANT;

    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
        String formattedDate = dateFormat.format(date.toInstant());
        gen.writeString(formattedDate);
    }
}
