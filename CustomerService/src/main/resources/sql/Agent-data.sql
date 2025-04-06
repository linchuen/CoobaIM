INSERT INTO im.t_user
(id, created_time, email, name, password, `role`, avatar)
VALUES(2, NOW(), 'agent@example.com', 'agent', 'b33aed8f3134996703dc39f9a7c95783', 'ROLE_AGENT', '');

INSERT INTO im.t_agent
(id, created_time, department, is_default, is_disable, name, updated_time, user_id)
VALUES(1, NOW(), 'customer support', 0, 0, 'agent', NOW(), 2);

INSERT INTO im.t_user (created_time, email, name, password, `role`)
VALUES(4, NOW(), 'bot@example.com', 'bot', 'fabcaa97871555b68aa095335975e613', 'ROLE_AGENT');

INSERT INTO im.t_agent
(id, created_time, department, is_default, is_disable, name, updated_time, user_id)
VALUES(2, NOW(), 'ai', 1, 0, 'agent', NOW(), 4);