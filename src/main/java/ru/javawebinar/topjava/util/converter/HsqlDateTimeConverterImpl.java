package ru.javawebinar.topjava.util.converter;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
@Profile("hsqldb")
public class HsqlDateTimeConverterImpl implements DateTimeConverter<Timestamp> {

    @Override
    public Timestamp convertDateTime(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }
}
