
INSERT INTO users
(id,
username,
 password,
 role)
VALUES
    (4,
    'username4',
     'password4',
     'ROLE_USER');
     SET @user_id4 = LAST_INSERT_ID();
INSERT INTO stored_file
(filename,
 size,
 hash,
 fileUUID,
 user_id)
VALUES
    ('filename4',
     10,
     'hash4',
     'uuid4',
     4);