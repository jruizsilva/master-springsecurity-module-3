-- CREACIÓN DE MODULOS
INSERT INTO modules (name, base_path) VALUES ('PRODUCT', '/products');
INSERT INTO modules (name, base_path) VALUES ('CATEGORY', '/categories');
INSERT INTO modules (name, base_path) VALUES ('CUSTOMER', '/customers');
INSERT INTO modules (name, base_path) VALUES ('AUTH', '/auth');
INSERT INTO modules (name, base_path) VALUES ('GRANTED_PERMISSION', '/granted-permissions');

-- CREACION DE OPERACIONES
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_PRODUCTS','', 'GET', false, 1);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('READ_ONE_PRODUCT','/[0-9]*', 'GET', false, 1);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('CREATE_ONE_PRODUCT','', 'POST', false, 1);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('UPDATE_ONE_PRODUCT','/[0-9]*', 'PUT', false, 1);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('DISABLE_ONE_PRODUCT','/[0-9]*/disabled', 'PUT', false, 1);

INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_CATEGORIES','', 'GET', false, 2);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('READ_ONE_CATEGORY','/[0-9]*', 'GET', false, 2);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('CREATE_ONE_CATEGORY','', 'POST', false, 2);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('UPDATE_ONE_CATEGORY','/[0-9]*', 'PUT', false, 2);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('DISABLE_ONE_CATEGORY','/[0-9]*/disabled', 'PUT', false, 2);

INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_CUSTOMERS','', 'GET', false, 3);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('REGISTER_ONE','', 'POST', true, 3);

INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('AUTHENTICATE','/login', 'POST', true, 4);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('VALIDATE-TOKEN','/validate-token', 'GET', true, 4);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('READ_MY_PROFILE','/profile','GET', false, 4);

INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_PERMISSIONS','','GET', true, 5);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('READ_ONE_PERMISSION','/[0-9]*','GET', true, 5);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('DELETE_ONE_PERMISSION','/[0-9]*','DELETE', true, 5);
INSERT INTO operations (name, path, http_method, permit_all, module_id) VALUES ('CREATE_ONE_PERMISSION','','POST', true, 5);

-- CREACIÓN DE ROLES
INSERT INTO roles (name) VALUES ('CUSTOMER');
INSERT INTO roles (name) VALUES ('ASSISTANT_ADMINISTRATOR');
INSERT INTO roles (name) VALUES ('ADMINISTRATOR');

-- CREACIÓN DE PERMISOS
INSERT INTO granted_permissions (role_id, operation_id) VALUES (1, 15);

INSERT INTO granted_permissions (role_id, operation_id) VALUES (2, 1);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (2, 2);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (2, 4);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (2, 6);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (2, 7);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (2, 9);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (2, 15);

INSERT INTO granted_permissions (role_id, operation_id) VALUES (3, 1);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (3, 2);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (3, 3);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (3, 4);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (3, 5);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (3, 6);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (3, 7);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (3, 8);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (3, 9);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (3, 10);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (3, 15);

INSERT INTO users (username, name, password, role_id) VALUES ('user', 'soy user', '$2a$10$9WbwyMx3bzQFVJK.Z74M4OFlROF73OfeiN6es1xFtBKN2JDpMBwty', 1);
INSERT INTO users (username, name, password, role_id) VALUES ('asistente', 'luis márquez', '$2a$10$URTy2dbSE09TF3fAFL2myOcKxEtLdklh6g7hRVcmkAoZMmtOZE/C2',2);
INSERT INTO users (username, name, password, role_id) VALUES ('admin', 'soy admin', '$2a$10$9z/aOtoWP.wFE5HeuXD5H.ZnfAxxkiDUVIFikNOtcx8TgYY0kg6ve',3);

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