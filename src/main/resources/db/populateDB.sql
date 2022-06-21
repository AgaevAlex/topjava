DELETE
FROM user_roles;
DELETE
FROM meals;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, userId)
VALUES ('USER', 100000),
       ('ADMIN', 100001);



INSERT INTO meals (id, description, datetime, calories, userId)
VALUES (100005, 'Завтрак', '2020-01-30 10:00:00', 500, 100000),
       (100006, 'Обед', '2020-01-30 13:00:00', 1000, 100001),
       (100007, 'Обед', '2021-01-30 15:00:00', 400, 100000),
       (100008, 'Ужин', '2020-01-25 15:00:00', 1111, 100000)




