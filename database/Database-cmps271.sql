CREATE TABLE users (
  user_id INT PRIMARY KEY,
  full_name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  phone VARCHAR(20),
  status VARCHAR(20) NOT NULL,
  profile_picture VARCHAR(255)
);

CREATE TABLE category (
  category_id INT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE listing (
  listing_id INT PRIMARY KEY,
  title VARCHAR(100) NOT NULL,
  description TEXT NOT NULL,
  condition VARCHAR(50) NOT NULL,
  location VARCHAR(100),
  price DECIMAL(10,2) NOT NULL,
  status VARCHAR(20) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  seller_id INT NOT NULL,
  category_id INT NOT NULL,
  FOREIGN KEY (seller_id) REFERENCES users(user_id),
  FOREIGN KEY (category_id) REFERENCES category(category_id)
);

CREATE TABLE listing_image (
  image_id INT PRIMARY KEY,
  image_path VARCHAR(255) NOT NULL,
  sort_order INT,
  listing_id INT NOT NULL,
  FOREIGN KEY (listing_id) REFERENCES listing(listing_id)
);

CREATE TABLE wanted_item (
  item_id INT PRIMARY KEY,
  title VARCHAR(100) NOT NULL,
  description TEXT NOT NULL,
  created_at TIMESTAMP NOT NULL,
  category_id INT NOT NULL,
  user_id INT NOT NULL,
  FOREIGN KEY (category_id) REFERENCES category(category_id),
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE cart (
  cart_id INT PRIMARY KEY,
  user_id INT NOT NULL UNIQUE,
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE cart_item (
  cart_item_id INT PRIMARY KEY,
  cart_id INT NOT NULL,
  listing_id INT NOT NULL,
  unit_price DECIMAL(10,2) NOT NULL,
  quantity INT NOT NULL CHECK (quantity > 0),
  FOREIGN KEY (cart_id) REFERENCES cart(cart_id),
  FOREIGN KEY (listing_id) REFERENCES listing(listing_id),
  UNIQUE (cart_id, listing_id)
);
