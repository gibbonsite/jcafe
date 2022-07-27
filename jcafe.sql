-- -----------------------------------------------------
-- Table cafe.user_roles
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user_roles (
  user_role_id SERIAL NOT NULL ,
  role VARCHAR(15) NULL,
  PRIMARY KEY (user_role_id))

;


-- -----------------------------------------------------
-- Table cafe.user_states
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user_states (
  user_state_id SERIAL NOT NULL,
  state VARCHAR(10) NULL,
  PRIMARY KEY (user_state_id))

;


-- -----------------------------------------------------
-- Table cafe.users
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS users (
  user_id SERIAL NOT NULL,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  login VARCHAR(16) NOT NULL,
  password VARCHAR(40) NOT NULL,
  email VARCHAR(40) NOT NULL,
  phone INT NULL,
  birthday DATE NOT NULL,
  cash DECIMAL(8,2) NOT NULL,
  loyal_score_bonus DECIMAL(8,2) NOT NULL,
  user_state_id INT NOT NULL,
  user_role_id INT NOT NULL,
  PRIMARY KEY (user_id),
  CONSTRAINT fk_users_user_role1
    FOREIGN KEY (user_role_id)
    REFERENCES user_roles (user_role_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_users_user_status1
    FOREIGN KEY (user_state_id)
    REFERENCES user_states (user_state_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE)

;


-- -----------------------------------------------------
-- Table cafe.order_states
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS order_states (
  order_state_id SERIAL NOT NULL,
  order_state VARCHAR(20) NOT NULL,
  PRIMARY KEY (order_state_id))
;


-- -----------------------------------------------------
-- Table cafe.orders
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS orders (
  order_id SERIAL NOT NULL,
  user_id INT NOT NULL,
  order_date TIMESTAMP NOT NULL,
  order_state_id INT NOT NULL, 
  total_cost DECIMAL(8,2) NOT NULL,
  payment_type_id INT NOT NULL,
  address VARCHAR(200) NOT NULL,
  user_comment VARCHAR(200) NULL,
  PRIMARY KEY (order_id),
  CONSTRAINT fk_orders_users1
    FOREIGN KEY (user_id)
    REFERENCES users (user_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_orders_order_states1
    FOREIGN KEY (order_state_id)
    REFERENCES order_states (order_state_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_orders_payment_types1
    FOREIGN KEY (payment_type_id)
    REFERENCES payment_types (payment_type_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
;



CREATE TABLE IF NOT EXISTS payment_types (
  payment_type_id SERIAL NOT NULL ,
  payment_type VARCHAR(20) NOT NULL,
  PRIMARY KEY (payment_type_id))

;


-- -----------------------------------------------------
-- Table cafe.sections
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS sections (
  section_id SERIAL NOT NULL,
  section_name VARCHAR(20) NOT NULL,
  enabled BOOLEAN NOT NULL,
  PRIMARY KEY (section_id))

;


-- -----------------------------------------------------
-- Table cafe.menu
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS menu (
  menu_id SERIAL NOT NULL,
  name VARCHAR(50) NOT NULL,
  section_id INT NOT NULL,
  picture_path VARCHAR(200) NULL,
  description VARCHAR(200) NOT NULL,
  weight INT NOT NULL,
  loyal_score DECIMAL(8,2) NOT NULL,
  price DECIMAL(8,2) NOT NULL,
  enabled BOOLEAN NOT NULL,
  PRIMARY KEY (menu_id),
  CONSTRAINT fk_menu_sections1
    FOREIGN KEY (section_id)
    REFERENCES sections (section_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE)

;


-- -----------------------------------------------------
-- Table cafe.orders_menu
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS orders_menu (
  order_id INT NOT NULL,
  menu_id INT NOT NULL,
  menu_number INT NULL,
  PRIMARY KEY (order_id, menu_id),
  CONSTRAINT fk_orders_has_menu_orders1
    FOREIGN KEY (order_id)
    REFERENCES orders (order_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT fk_orders_has_menu_menu1
    FOREIGN KEY (menu_id)
    REFERENCES menu (menu_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)

;
