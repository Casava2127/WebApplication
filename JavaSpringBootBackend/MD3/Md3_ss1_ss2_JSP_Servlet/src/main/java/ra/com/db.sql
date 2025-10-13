CREATE DATABASE ecommerce_db;

USE ecommerce_db;

CREATE TABLE Categories (
                            catalog_id INT PRIMARY KEY AUTO_INCREMENT,
                            category_name VARCHAR(255) NOT NULL UNIQUE,
                            catalog_description VARCHAR(255),
                            catalog_status BIT DEFAULT 1
);

DELIMITER &&

CREATE PROCEDURE find_all_categories()
BEGIN
    SELECT * FROM Categories;
END &&

DELIMITER ;

select  * from Categories;

DELIMITER &&
create procedure  create_new_catalog(
    name_in varchar(255),
    description_in text,
    status_in bit
)
BEGIN
    INSERT INTO Categories (catalog_name, catalog_description, catalog_status)
    VALUES (name_in, description_in, status_in);
END &&
DELIMITER ;

# CALL create_new_catalog('Electronics', 'All electronic devices', 1);

# tương tu viet cho find_catalog_by_id, update_catalog, delete_catalog(?)
DELIMITER &&

CREATE PROCEDURE find_catalog_by_id(IN catalog_id_in INT)
BEGIN
    SELECT * FROM Categories WHERE catalog_id = catalog_id_in;
END &&

DELIMITER ;

DELIMITER &&

CREATE PROCEDURE update_catalog(
    IN catalog_id_in INT,
    IN name_in VARCHAR(255),
    IN description_in TEXT,
    IN status_in BIT
)
BEGIN
    UPDATE Categories
    SET catalog_name = name_in,
        catalog_description = description_in,
        catalog_status = status_in
    WHERE catalog_id = catalog_id_in;
END &&

DELIMITER ;


DELIMITER &&

CREATE PROCEDURE delete_catalog(IN catalog_id_in INT)
BEGIN
    DELETE FROM Categories WHERE catalog_id = catalog_id_in;
END &&

DELIMITER ;
