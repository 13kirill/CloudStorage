INSERT INTO users
(username,
 password,
 role)
VALUES
    ('username2',
     'password2',
     'ROLE_USER');
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