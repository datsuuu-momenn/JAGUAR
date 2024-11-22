INSERT INTO tasks (title) VALUES ('This is the task-1');
INSERT INTO tasks (title) VALUES ('This is the task-2');
INSERT INTO tasks (title) VALUES ('This is the task-3');
INSERT INTO tasks (title) VALUES ('This is the task-4');
INSERT INTO tasks (title) VALUES ('This is the task-5');


INSERT INTO users (username, password) VALUES ('admin', 'admin123');

-- スキルデータの初期化
INSERT INTO skills (skill_id, skill_name, damage, accuracy, critical_rate, skill_type, description) VALUES (1, 'リーフブレード', 70, 95, 10, 'GRASS', '');
INSERT INTO skills (skill_id, skill_name, damage, accuracy, critical_rate, skill_type, description) VALUES (2, 'ブレイブバード', 120, 85, 15, 'FLYING', '');
INSERT INTO skills (skill_id, skill_name, damage, accuracy, critical_rate, skill_type, description) VALUES (3, 'はっぱカッター', 55, 100, 5, 'GRASS', '');
INSERT INTO skills (skill_id, skill_name, damage, accuracy, critical_rate, skill_type, description) VALUES (4, 'マヒルノツメ', 65, 95, 10, 'NORMAL', '');