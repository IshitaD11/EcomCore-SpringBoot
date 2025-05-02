CREATE TABLE category
(
    id                   BIGINT AUTO_INCREMENT NOT NULL,
    created_at           datetime NULL,
    updated_at           datetime NULL,
    state                SMALLINT NULL,
    category_name        VARCHAR(255) NULL,
    category_description VARCHAR(255) NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE product
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    created_at        datetime NULL,
    updated_at        datetime NULL,
    state             SMALLINT NULL,
    title             VARCHAR(255) NULL,
    `description`     VARCHAR(255) NULL,
    img_url           VARCHAR(255) NULL,
    price DOUBLE NULL,
    category_id       BIGINT NULL,
    is_prime_specific BIT(1) NOT NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);