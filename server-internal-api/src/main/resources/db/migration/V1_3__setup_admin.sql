-- Hengshuai
INSERT INTO admin_account (id, version, username, password, email, activated, first_name, last_name, title, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-yyyy-1697f9250001', 0, 'hengshuai', '$2a$10$R10wLcxuhvX/MFl.wwoU5OO1yWOSr2jlGmYKJ7ouAgHNN7TMOzgey', 
    'hengshuai.yao@groundtruthinc.com', true, 'Hengshuai', 'Yao', 'CEO', 'admin', NOW());
INSERT INTO admin_role (admin_account_id, role)
VALUES ('3b50a3fc-f813-11e4-yyyy-1697f9250001', 'ROLE_SUPER_ADMIN'),
       ('3b50a3fc-f813-11e4-yyyy-1697f9250001', 'ROLE_USER_MANAGER'),
       ('3b50a3fc-f813-11e4-yyyy-1697f9250001', 'ROLE_STORE_ADMIN');

-- Yuan
INSERT INTO admin_account (id, version, username, password, email, activated, first_name, last_name, title, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-yyyy-1697f9250002', 0, 'jiwhiz', '$2a$10$R10wLcxuhvX/MFl.wwoU5OO1yWOSr2jlGmYKJ7ouAgHNN7TMOzgey', 
    'yuan.ji@groundtruthinc.com', true, 'Yuan', 'Ji', 'CTO', 'admin', NOW());
INSERT INTO admin_role (admin_account_id, role)
VALUES ('3b50a3fc-f813-11e4-yyyy-1697f9250002', 'ROLE_SUPER_ADMIN'),
       ('3b50a3fc-f813-11e4-yyyy-1697f9250002', 'ROLE_USER_MANAGER'),
       ('3b50a3fc-f813-11e4-yyyy-1697f9250002', 'ROLE_STORE_ADMIN');

-- Hu
INSERT INTO admin_account (id, version, username, password, email, activated, first_name, last_name, title, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-yyyy-1697f9250003', 0, 'tiger', '$2a$10$R10wLcxuhvX/MFl.wwoU5OO1yWOSr2jlGmYKJ7ouAgHNN7TMOzgey',
    'hu.zhang@groundtruthinc.com', true, 'Hu', 'Zhang', 'Lead Developer', 'admin', NOW());
INSERT INTO admin_role (admin_account_id, role)
VALUES ('3b50a3fc-f813-11e4-yyyy-1697f9250003', 'ROLE_SUPER_ADMIN'),
       ('3b50a3fc-f813-11e4-yyyy-1697f9250003', 'ROLE_USER_MANAGER'),
       ('3b50a3fc-f813-11e4-yyyy-1697f9250003', 'ROLE_STORE_ADMIN');

-- Reka
INSERT INTO admin_account (id, version, username, password, email, activated, first_name, last_name, title, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-yyyy-1697f9250004', 0, 'reka', '$2a$10$R10wLcxuhvX/MFl.wwoU5OO1yWOSr2jlGmYKJ7ouAgHNN7TMOzgey', 
    'reka.szepesvari@groundtruthinc.com', true, 'Réka', 'Szepesvári', 'Design Director', 'admin', NOW());
INSERT INTO admin_role (admin_account_id, role)
VALUES ('3b50a3fc-f813-11e4-yyyy-1697f9250004', 'ROLE_SUPER_ADMIN'),
       ('3b50a3fc-f813-11e4-yyyy-1697f9250004', 'ROLE_USER_MANAGER'),
       ('3b50a3fc-f813-11e4-yyyy-1697f9250004', 'ROLE_STORE_ADMIN');

-- Csaba
INSERT INTO admin_account (id, version, username, password, email, activated, first_name, last_name, title, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-yyyy-1697f9250005', 0, 'csaba', '$2a$10$R10wLcxuhvX/MFl.wwoU5OO1yWOSr2jlGmYKJ7ouAgHNN7TMOzgey', 
    'csaba.szepesvari@groundtruthinc.com', true, 'Csaba', 'Szepesvári', 'CEO', 'admin', NOW());
INSERT INTO admin_role (admin_account_id, role)
VALUES ('3b50a3fc-f813-11e4-yyyy-1697f9250005', 'ROLE_SUPER_ADMIN'),
       ('3b50a3fc-f813-11e4-yyyy-1697f9250005', 'ROLE_USER_MANAGER'),
       ('3b50a3fc-f813-11e4-yyyy-1697f9250005', 'ROLE_STORE_ADMIN');
