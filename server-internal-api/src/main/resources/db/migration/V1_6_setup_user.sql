-- Hengshuai
INSERT INTO user_profile (id, version, first_name, last_name, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250001', 0, 'Hengshuai', 'Yao', 'admin', NULL);
INSERT INTO user_account (id, version, profile_id, email, password, trusted_account, activated, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250001', 0, '3b50a3fc-f813-11e4-a322-1697f9250001',
    'hengshuai@groundtruth.club', '$2a$10$R10wLcxuhvX/MFl.wwoU5OO1yWOSr2jlGmYKJ7ouAgHNN7TMOzgey', true, true, 'admin', NULL);
INSERT INTO user_role (user_account_id, role)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250001', 'ROLE_USER');

-- Yuan
INSERT INTO user_profile (id, version, first_name, last_name, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250002', 0, 'Yuan', 'Ji', 'admin', NULL);
INSERT INTO user_account (id, version, profile_id, email, password, trusted_account, activated, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250002', 0, '3b50a3fc-f813-11e4-a322-1697f9250002',
    'jiwhiz@gmail.com', '$2a$10$R10wLcxuhvX/MFl.wwoU5OO1yWOSr2jlGmYKJ7ouAgHNN7TMOzgey', true, true, 'admin', NULL);
INSERT INTO user_role (user_account_id, role)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250002', 'ROLE_USER');

-- Haipeng
INSERT INTO user_profile (id, version, first_name, last_name, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250003', 0, 'Haipeng', 'Li', 'admin', NULL);
INSERT INTO user_account (id, version, profile_id, email, password, trusted_account, activated, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250003', 0, '3b50a3fc-f813-11e4-a322-1697f9250003',
    'solittlework.li@gmail.com', '$2a$10$R10wLcxuhvX/MFl.wwoU5OO1yWOSr2jlGmYKJ7ouAgHNN7TMOzgey', true, true, 'admin', NULL);
INSERT INTO user_role (user_account_id, role)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250003', 'ROLE_USER');

-- Hu
INSERT INTO user_profile (id, version, first_name, last_name, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250004', 0, 'Hu', 'Zhang', 'admin', NULL);
INSERT INTO user_account (id, version, profile_id, email, password, trusted_account, activated, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250004', 0, '3b50a3fc-f813-11e4-a322-1697f9250004',
    'tigzhanghit@gmail.com', '$2a$10$R10wLcxuhvX/MFl.wwoU5OO1yWOSr2jlGmYKJ7ouAgHNN7TMOzgey', true, true, 'admin', NULL);
INSERT INTO user_role (user_account_id, role)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250004', 'ROLE_USER');

-- Reka
INSERT INTO user_profile (id, version, first_name, last_name, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250005', 0, 'Réka', 'Szepesvári', 'admin', NULL);
INSERT INTO user_account (id, version, profile_id, email, password, trusted_account, activated, created_by, last_modified_time)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250005', 0, '3b50a3fc-f813-11e4-a322-1697f9250005',
    'szepi.92@gmail.com', '$2a$10$R10wLcxuhvX/MFl.wwoU5OO1yWOSr2jlGmYKJ7ouAgHNN7TMOzgey', true, true, 'admin', NULL);
INSERT INTO user_role (user_account_id, role)
VALUES ('3b50a3fc-f813-11e4-a322-1697f9250005', 'ROLE_USER');
