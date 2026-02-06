-- 1) USER table
CREATE TABLE "user" (
  user_id        BIGSERIAL PRIMARY KEY,
  full_name      VARCHAR(120) NOT NULL,
  email          VARCHAR(255) NOT NULL UNIQUE,
  password_hash  VARCHAR(255) NOT NULL,
  created_at     TIMESTAMP NOT NULL DEFAULT NOW(),
  phone          VARCHAR(30),
  status         VARCHAR(30) NOT NULL DEFAULT 'active',
  updated_at     TIMESTAMP NOT NULL DEFAULT NOW(),
  profile_picture TEXT
);

-- 2) CATEGORY table (self-referencing)
CREATE TABLE category (
  category_id        BIGSERIAL PRIMARY KEY,
  name               VARCHAR(120) NOT NULL,
  parent_category_id BIGINT NULL,
  CONSTRAINT fk_category_parent
    FOREIGN KEY (parent_category_id) REFERENCES category(category_id)
    ON DELETE SET NULL
);

-- 3) LISTING table
CREATE TABLE listing (
  listing_id   BIGSERIAL PRIMARY KEY,
  condition    VARCHAR(50) NOT NULL,
  location     VARCHAR(120),
  created_at   TIMESTAMP NOT NULL DEFAULT NOW(),
  status       VARCHAR(30) NOT NULL DEFAULT 'active',
  title        VARCHAR(150) NOT NULL,
  description  TEXT,
  price        NUMERIC(10,2) NOT NULL CHECK (price >= 0),
  user_id      BIGINT NOT NULL,
  category_id  BIGINT NOT NULL,
  CONSTRAINT fk_listing_user
    FOREIGN KEY (user_id) REFERENCES "user"(user_id)
    ON DELETE CASCADE,
  CONSTRAINT fk_listing_category
    FOREIGN KEY (category_id) REFERENCES category(category_id)
    ON DELETE RESTRICT
);

-- 4) LISTING_IMAGE table
CREATE TABLE listing_image (
  image_id    BIGSERIAL PRIMARY KEY,
  image_path  TEXT NOT NULL,
  sort_order  INT NOT NULL DEFAULT 1 CHECK (sort_order >= 1),
  listing_id  BIGINT NOT NULL,
  CONSTRAINT fk_image_listing
    FOREIGN KEY (listing_id) REFERENCES listing(listing_id)
    ON DELETE CASCADE
);

-- no duplicate sort_order per listing
CREATE UNIQUE INDEX ux_listing_image_order
  ON listing_image(listing_id, sort_order);

-- 5) WANTED_ITEM table
CREATE TABLE wanted_item (
  item_id      BIGSERIAL PRIMARY KEY,
  title        VARCHAR(150) NOT NULL,
  description  TEXT,
  created_at   TIMESTAMP NOT NULL DEFAULT NOW(),
  category_id  BIGINT NOT NULL,
  user_id      BIGINT NOT NULL,
  CONSTRAINT fk_wanted_category
    FOREIGN KEY (category_id) REFERENCES category(category_id)
    ON DELETE RESTRICT,
  CONSTRAINT fk_wanted_user
    FOREIGN KEY (user_id) REFERENCES "user"(user_id)
    ON DELETE CASCADE
);

-- indexes
CREATE INDEX ix_listing_user     ON listing(user_id);
CREATE INDEX ix_listing_category ON listing(category_id);
CREATE INDEX ix_wanted_user      ON wanted_item(user_id);
CREATE INDEX ix_wanted_category  ON wanted_item(category_id);

