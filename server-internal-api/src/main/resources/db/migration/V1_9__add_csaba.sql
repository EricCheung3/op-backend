-- User Account for Csaba
INSERT INTO user_account (id, version, email, password, trusted_account, activated, first_name, last_name, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250005', 0, 'csaba.szepesvari@gmail.com', 
    '$2a$10$R10wLcxuhvX/MFl.wwoU5OO1yWOSr2jlGmYKJ7ouAgHNN7TMOzgey', true, true, 'Csaba', 'Szepesvári', 'admin', NULL);
INSERT INTO user_role (user_account_id, role)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250005', 'ROLE_USER');

-- Admin Account for Csaba
INSERT INTO admin_account (id, version, username, password, email, activated, first_name, last_name, title, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250005', 0, 'csaba', '$2a$10$R10wLcxuhvX/MFl.wwoU5OO1yWOSr2jlGmYKJ7ouAgHNN7TMOzgey', 
    'csaba.szepesvari@groundtruthinc.com', true, 'Csaba', 'Szepesvári', 'CEO', 'admin', NULL);
INSERT INTO admin_role (admin_account_id, role)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250005', 'ROLE_SUPER_ADMIN'),
       ('3b50a3fc-f813-11e4-a322-1697f9250005', 'ROLE_USER_MANAGER'),
       ('3b50a3fc-f813-11e4-a322-1697f9250005', 'ROLE_STORE_ADMIN');
