CREATE TABLE `schedule`
(
    `id`         BIGINT      NOT NULL COMMENT 'Auto_Increment',
    `user_name`  VARCHAR(50) NOT NULL,
    `title`      VARCHAR(50) NOT NULL,
    `content`    VARCHAR(50) NOT NULL,
    `created_at` DATETIME    NOT NULL,
    `updated_at` DATETIME    NOT NULL
);

CREATE TABLE `comment`
(
    `id`          BIGINT       NOT NULL COMMENT 'Auto_Increment',
    `content`     VARCHAR(250) NOT NULL,
    `schedule_id` BIGINT       NOT NULL COMMENT 'FK',
    `user_id`     BIGINT       NOT NULL COMMENT 'FK',
    `created_At`  DATETIME     NOT NULL,
    `updated_at`  DATETIME     NOT NULL
);
