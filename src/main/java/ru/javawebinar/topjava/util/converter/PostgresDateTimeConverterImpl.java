package ru.javawebinar.topjava.util.converter;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Profile("postgres")
public class PostgresDateTimeConverterImpl implements DateTimeConverter<LocalDateTime> {

    @Override
    public LocalDateTime convertDateTime(LocalDateTime localDateTime) {
        return localDateTime;
    }
}
