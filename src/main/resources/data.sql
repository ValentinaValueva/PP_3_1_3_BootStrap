INSERT INTO roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_USER');

INSERT INTO users (id, name, email, age, password) VALUES
                                                       (1, 'Admin', 'admin@mail.ru', 30, '$2a$10$4.GVXfhnm9NMHkFCN.4Up.L95rqHvyTvjH6xtrg11J/HGS7CjkdfS'),
                                                       (2, 'User', 'user@mail.ru', 20, '$2a$10$4.GVXfhnm9NMHkFCN.4Up.L95rqHvyTvjH6xtrg11J/HGS7CjkdfS');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);