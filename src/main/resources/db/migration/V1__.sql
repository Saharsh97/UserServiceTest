CREATE TABLE `role`
(
    id      INT AUTO_INCREMENT NOT NULL,
    deleted BIT(1) NOT NULL,
    name    VARCHAR(255) NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE token
(
    id        INT AUTO_INCREMENT NOT NULL,
    deleted   BIT(1) NOT NULL,
    value     VARCHAR(255) NULL,
    user_id   INT NULL,
    expiry_at datetime NULL,
    CONSTRAINT pk_token PRIMARY KEY (id)
);

CREATE TABLE user
(
    id                INT AUTO_INCREMENT NOT NULL,
    deleted           BIT(1) NOT NULL,
    name              VARCHAR(255) NULL,
    email             VARCHAR(255) NULL,
    hash_password     VARCHAR(255) NULL,
    is_email_verified BIT(1) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    user_id  INT NOT NULL,
    roles_id INT NOT NULL
);

ALTER TABLE user_roles
    ADD CONSTRAINT uc_user_roles_roles UNIQUE (roles_id);

ALTER TABLE token
    ADD CONSTRAINT FK_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (roles_id) REFERENCES `role` (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES user (id);