INSERT INTO roles (id, name) VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_USER'), (3, 'ROLE_GUEST');
INSERT INTO users (id, email, enabled, first_name, last_name, password) VALUES (1, 'admin', true, 'Василий', 'Уткин', '$2a$10$qBbUiJjYQ/GtbpI1soPDWu7XUOqMmSCMV/CELCaDxRhdLKNU8vAxW');
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1), (1, 2);
ALTER SEQUENCE users_id_seq RESTART WITH 2;