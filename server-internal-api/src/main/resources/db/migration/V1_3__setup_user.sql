-- Hengshuai
INSERT INTO user_account (id, version, email, password, trusted_account, activated, first_name, last_name, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-xxxx-1697f9250001', 0, 'hengshuai@groundtruth.club', 
    '$2a$10$R10wLcxuhvX/MFl.wwoU5OO1yWOSr2jlGmYKJ7ouAgHNN7TMOzgey', true, true, 'Hengshuai', 'Yao', 'admin', NOW());
INSERT INTO user_role (user_account_id, role)
VALUES ('3b50a3fc-f813-11e4-xxxx-1697f9250001', 'ROLE_USER');

-- Yuan
INSERT INTO user_account (id, version, email, password, trusted_account, activated, first_name, last_name, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-xxxx-1697f9250002', 0, 'jiwhiz@gmail.com', 
    '$2a$10$R10wLcxuhvX/MFl.wwoU5OO1yWOSr2jlGmYKJ7ouAgHNN7TMOzgey', true, true, 'Yuan', 'Ji', 'admin', NOW());
INSERT INTO user_role (user_account_id, role)
VALUES ('3b50a3fc-f813-11e4-xxxx-1697f9250002', 'ROLE_USER');

-- Hu
INSERT INTO user_account (id, version, email, password, trusted_account, activated, first_name, last_name, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-xxxx-1697f9250003', 0, 'tigzhanghit@gmail.com', 
    '$2a$10$R10wLcxuhvX/MFl.wwoU5OO1yWOSr2jlGmYKJ7ouAgHNN7TMOzgey', true, true, 'Hu', 'Zhang', 'admin', NOW());
INSERT INTO user_role (user_account_id, role)
VALUES ('3b50a3fc-f813-11e4-xxxx-1697f9250003', 'ROLE_USER');

-- Reka
INSERT INTO user_account (id, version, email, password, trusted_account, activated, first_name, last_name, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-xxxx-1697f9250004', 0, 'szepi.92@gmail.com', 
    '$2a$10$R10wLcxuhvX/MFl.wwoU5OO1yWOSr2jlGmYKJ7ouAgHNN7TMOzgey', true, true, 'Réka', 'Szepesvári', 'admin', NOW());
INSERT INTO user_role (user_account_id, role)
VALUES ('3b50a3fc-f813-11e4-xxxx-1697f9250004', 'ROLE_USER');

-- Csaba
INSERT INTO user_account (id, version, email, password, trusted_account, activated, first_name, last_name, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-xxxx-1697f9250005', 0, 'csaba.szepesvari@gmail.com', 
    '$2a$10$R10wLcxuhvX/MFl.wwoU5OO1yWOSr2jlGmYKJ7ouAgHNN7TMOzgey', true, true, 'Csaba', 'Szepesvári', 'admin', NOW());
INSERT INTO user_role (user_account_id, role)
VALUES ('3b50a3fc-f813-11e4-xxxx-1697f9250005', 'ROLE_USER');
