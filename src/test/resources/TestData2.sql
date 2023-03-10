
INSERT INTO users
(id,
username,
 password,
 role)
VALUES
    (2,
    'username2',
     'password2',
     'ROLE_USER');
INSERT INTO stored_file
(filename,
 size,
 hash,
 fileUUID,
 user_id)
VALUES
    ('filename2',
     10,
     'hash2',
     'uuid2',
     2);