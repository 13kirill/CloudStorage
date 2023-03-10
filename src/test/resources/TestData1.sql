
INSERT INTO users
(id,
username,
 password,
 role)
VALUES
    (1,
    'username1',
     'password1',
     'ROLE_USER');
SET @user_id1 = LAST_INSERT_ID();
INSERT INTO stored_file
(filename,
 size,
 hash,
 fileUUID,
 user_id)
VALUES
    ('filename1',
     10,
     'hash1',
     'uuid1',
     1);
INSERT INTO stored_file
(filename,
 size,
 hash,
 fileUUID,
 user_id)
VALUES
    ('filename11',
     15,
     'hash11',
     'uuid11',
     1);