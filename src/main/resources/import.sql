INSERT INTO users (username, name, password, role) values ('user', 'soy user', '$2a$10$9WbwyMx3bzQFVJK.Z74M4OFlROF73OfeiN6es1xFtBKN2JDpMBwty', 'CUSTOMER');
INSERT INTO users (username, name, password, role) values ('admin', 'soy admin', '$2a$10$9z/aOtoWP.wFE5HeuXD5H.ZnfAxxkiDUVIFikNOtcx8TgYY0kg6ve', 'ADMINISTRATOR');

-- CREACIÓN DE CATEGORIAS
INSERT INTO categories (name, status) VALUES ('Electrónica', 'ENABLED');
INSERT INTO categories (name, status) VALUES ('Ropa', 'ENABLED');
INSERT INTO categories (name, status) VALUES ('Deportes', 'ENABLED');
INSERT INTO categories (name, status) VALUES ('Hogar', 'ENABLED');

-- CREACIÓN DE PRODUCTOS
INSERT INTO products (name, price, status, category_id) VALUES ('Smartphone', 500.00, 'ENABLED', 1);
INSERT INTO products (name, price, status, category_id) VALUES ('Auriculares Bluetooth', 50.00, 'DISABLED', 1);
INSERT INTO products (name, price, status, category_id) VALUES ('Tablet', 300.00, 'ENABLED', 1);

INSERT INTO products (name, price, status, category_id) VALUES ('Camiseta', 25.00, 'ENABLED', 2);
INSERT INTO products (name, price, status, category_id) VALUES ('Pantalones', 35.00, 'ENABLED', 2);
INSERT INTO products (name, price, status, category_id) VALUES ('Zapatos', 45.00, 'ENABLED', 2);

INSERT INTO products (name, price, status, category_id) VALUES ('Balón de Fútbol', 20.00, 'ENABLED', 3);
INSERT INTO products (name, price, status, category_id) VALUES ('Raqueta de Tenis', 80.00, 'DISABLED', 3);

INSERT INTO products (name, price, status, category_id) VALUES ('Aspiradora', 120.00, 'ENABLED', 4);
INSERT INTO products (name, price, status, category_id) VALUES ('Licuadora', 50.00, 'ENABLED', 4);