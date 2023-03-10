
INSERT INTO users
(id,
username,
 password,
 role)
VALUES
    (3,
    'username3',
     'password3',
     'ROLE_USER');
SET @user_id3 = LAST_INSERT_ID();
INSERT INTO stored_file
(filename,
 size,
 hash,
 fileUUID,
 user_id)
VALUES
    ('filename3',
     10,
     'hash3',
     'uuid3',
     3);
INSERT INTO stored_file
(filename,
 size,
 hash,
 fileUUID,
 user_id)
VALUES
    ('filename33',
     15,
     'hash3',
     'uuid3',
     3);