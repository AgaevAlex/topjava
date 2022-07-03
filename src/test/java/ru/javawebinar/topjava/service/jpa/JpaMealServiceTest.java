package ru.javawebinar.topjava.service.jpa;

import org.slf4j.Logger;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static org.slf4j.LoggerFactory.getLogger;

@ActiveProfiles("jpa")
public class JpaMealServiceTest extends AbstractMealServiceTest {
    private static final Logger log = getLogger("result");

    private static final StringBuilder results = new StringBuilder();
}
