INSERT INTO im.t_guest
(id, created_time, user_id)
VALUES(1, NOW(), 3);

INSERT INTO im.t_user
(id, created_time, email, name, password, `role`, avatar)
VALUES(3,  NOW(), 'guest@example.com', 'guest', '084e0343a0486ff05530df6c705c8bb4', 'ROLE_GUEST', '');

INSERT INTO im.t_user_detail (created_time, email, name, remark, tags, user_id)
VALUES(NOW(), 'guest@example.com', 'guest', '', '[]', 3);