package ru.javawebinar.topjava.util.converter;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.Profiles.POSTGRES_DB;

@Component
@Profile(POSTGRES_DB)
public class PostgresDateTimeConverter implements DateTimeConverter<LocalDateTime> {

    @Override
    public LocalDateTime convertDateTime(LocalDateTime localDateTime) {
        return localDateTime;
    }
}
