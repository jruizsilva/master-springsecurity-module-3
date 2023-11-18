-- CREACIÓN DE MODULOS
INSERT INTO modules (name, base_path) VALUES ('PRODUCT', '/products');
INSERT INTO modules (name, base_path) VALUES ('CATEGORY', '/categories');
INSERT INTO modules (name, base_path) VALUES ('CUSTOMER', '/customers');
INSERT INTO modules (name, base_path) VALUES ('AUTH', '/auth');
INSERT INTO modules (name, base_path) VALUES ('GRANTED_PERMISSION', '/granted-permissions');
INSERT INTO modules (name, base_path) VALUES ('H2', '/h2-ui');
-- CREACION DE OPERACIONES PUBLICAS
INSERT INTO public_operations (name, path, http_method, module_id) VALUES ('REGISTER_ONE','', 'POST', 3);
INSERT INTO public_operations (name, path, http_method, module_id) VALUES ('AUTHENTICATE','/login', 'POST', 4);
INSERT INTO public_operations (name, path, http_method, module_id) VALUES ('LOGOUT','/logout','POST', 4);
INSERT INTO public_operations (name, path, http_method, module_id) VALUES ('VALIDATE-TOKEN','/validate-token', 'GET', 4);
INSERT INTO public_operations (name, path, http_method, module_id) VALUES ('H2_CONSOLE_ACCESS','[/\w.?&=]*','GET', 6);
INSERT INTO public_operations (name, path, http_method, module_id) VALUES ('H2_TEST_CONNECTION','[/\w.?&=]*','POST', 6);

-- CREACION DE AUTH OPERACIONES
INSERT INTO operations (name, path, http_method, module_id) VALUES ('READ_ALL_PRODUCTS','', 'GET', 1);
INSERT INTO operations (name, path, http_method, module_id) VALUES ('READ_ONE_PRODUCT','/[0-9]*', 'GET', 1);
INSERT INTO operations (name, path, http_method, module_id) VALUES ('CREATE_ONE_PRODUCT','', 'POST', 1);
INSERT INTO operations (name, path, http_method, module_id) VALUES ('UPDATE_ONE_PRODUCT','/[0-9]*', 'PUT', 1);
INSERT INTO operations (name, path, http_method, module_id) VALUES ('DISABLE_ONE_PRODUCT','/[0-9]*/disabled', 'PUT', 1);

INSERT INTO operations (name, path, http_method, module_id) VALUES ('READ_ALL_CATEGORIES','', 'GET', 2);
INSERT INTO operations (name, path, http_method, module_id) VALUES ('READ_ONE_CATEGORY','/[0-9]*', 'GET', 2);
INSERT INTO operations (name, path, http_method, module_id) VALUES ('CREATE_ONE_CATEGORY','', 'POST', 2);
INSERT INTO operations (name, path, http_method, module_id) VALUES ('UPDATE_ONE_CATEGORY','/[0-9]*', 'PUT', 2);
INSERT INTO operations (name, path, http_method, module_id) VALUES ('DISABLE_ONE_CATEGORY','/[0-9]*/disabled', 'PUT', 2);

INSERT INTO operations (name, path, http_method, module_id) VALUES ('READ_ALL_CUSTOMERS','', 'GET', 3);

INSERT INTO operations (name, path, http_method, module_id) VALUES ('READ_MY_PROFILE','/profile','GET', 4);

INSERT INTO operations (name, path, http_method, module_id) VALUES ('READ_ALL_PERMISSIONS','','GET', 5);
INSERT INTO operations (name, path, http_method, module_id) VALUES ('READ_ONE_PERMISSION','/[0-9]*','GET', 5);
INSERT INTO operations (name, path, http_method, module_id) VALUES ('DELETE_ONE_PERMISSION','/[0-9]*','DELETE', 5);
INSERT INTO operations (name, path, http_method, module_id) VALUES ('CREATE_ONE_PERMISSION','','POST', 5);

-- CREACIÓN DE ROLES
INSERT INTO roles (name) VALUES ('CUSTOMER'); -- only read his profile
INSERT INTO roles (name) VALUES ('ASSISTANT_ADMINISTRATOR'); -- only read and update operations
INSERT INTO roles (name) VALUES ('ADMINISTRATOR'); -- all operations

-- CREACIÓN DE PERMISOS
INSERT INTO granted_permissions (role_id, operation_id) VALUES (1, 15);

INSERT INTO granted_permissions (role_id, operation_id) VALUES (2, 1);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (2, 2);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (2, 4);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (2, 6);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (2, 7);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (2, 9);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (2, 12);

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
INSERT INTO granted_permissions (role_id, operation_id) VALUES (3, 11);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (3, 12);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (3, 13);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (3, 14);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (3, 15);
INSERT INTO granted_permissions (role_id, operation_id) VALUES (3, 16);

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