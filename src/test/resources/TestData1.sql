INSERT INTO users
(username,
 password,
 role)
VALUES
    ('username1',
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
     @user_id1);
INSERT INTO stored_file
(filename,
 size,
 hash,
 fileUUID,
 user_id)
VALUES
    ('filename2',
     15,
     'hash2',
     'uuid2',
     @user_id1);