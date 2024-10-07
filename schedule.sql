DROP TABLE IF EXISTS SCHEDULE;

CREATE TABLE `schedule`
(
    `id`         BIGINT      NOT NULL COMMENT 'Auto_Increment',
    `user_name`  VARCHAR(50) NOT NULL,
    `title`      VARCHAR(50) NOT NULL,
    `content`    VARCHAR(50) NOT NULL,
    `created_at` DATETIME    NOT NULL,
    `updated_at` DATETIME    NOT NULL
);
