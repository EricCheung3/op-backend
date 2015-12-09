-- Hengshuai
INSERT INTO user_account (id, version, email, password, trusted_account, activated, first_name, last_name, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250001', 0, 'hengshuai@groundtruth.club', 
    '$2a$10$R10wLcxuhvX/MFl.wwoU5OO1yWOSr2jlGmYKJ7ouAgHNN7TMOzgey', true, true, 'Hengshuai', 'Yao', 'admin', NULL);
INSERT INTO user_role (user_account_id, role)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250001', 'ROLE_USER');

-- Yuan
INSERT INTO user_account (id, version, email, password, trusted_account, activated, first_name, last_name, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250002', 0, 'jiwhiz@gmail.com', 
    '$2a$10$R10wLcxuhvX/MFl.wwoU5OO1yWOSr2jlGmYKJ7ouAgHNN7TMOzgey', true, true, 'Yuan', 'Ji', 'admin', NULL);
INSERT INTO user_role (user_account_id, role)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250002', 'ROLE_USER');

-- Hu
INSERT INTO user_account (id, version, email, password, trusted_account, activated, first_name, last_name, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250003', 0, 'tigzhanghit@gmail.com', 
    '$2a$10$R10wLcxuhvX/MFl.wwoU5OO1yWOSr2jlGmYKJ7ouAgHNN7TMOzgey', true, true, 'Hu', 'Zhang', 'admin', NULL);
INSERT INTO user_role (user_account_id, role)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250003', 'ROLE_USER');

-- Reka
INSERT INTO user_account (id, version, email, password, trusted_account, activated, first_name, last_name, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250004', 0, 'szepi.92@gmail.com', 
    '$2a$10$R10wLcxuhvX/MFl.wwoU5OO1yWOSr2jlGmYKJ7ouAgHNN7TMOzgey', true, true, 'Réka', 'Szepesvári', 'admin', NULL);
INSERT INTO user_role (user_account_id, role)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250004', 'ROLE_USER');
