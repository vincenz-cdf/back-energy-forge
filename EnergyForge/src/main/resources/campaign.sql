insert into app_user (email, login, password, coin, energy) values ('overloadPatri@abc.com', 'batch_energy_forge', 'OoOA&é00xAeç', 0, 0);

--Brigand Thug, 25HEAD, 40BODY
INSERT INTO CHARACTER (name, level, hp, armor, max_hp, max_armor, atk, sprite_path, character_type, morale, max_morale, experience_point, user_id, recovering)
VALUES ('Training Partner', 1, 50, 0, 50, 0, 8, 'assets/sprites/enemies/trainer1.png', 'TRAINING1', 4, 4, 15, 1, false);

INSERT INTO CHARACTER (name, level, hp, armor, max_hp, max_armor, atk, sprite_path, character_type, morale, max_morale, experience_point, user_id, recovering)
VALUES ('Shieldbearer', 1, 50, 0, 50, 0, 8, 'assets/sprites/enemies/trainer1.png', 'TRAINING2', 4, 4, 15, 1, false);
insert into equipped_item (character_id, equipment_id, slot)
values (2, 22, 'RIGHTHAND');

INSERT INTO CHARACTER (name, level, hp, armor, max_hp, max_armor, atk, sprite_path, character_type, morale, max_morale, experience_point, user_id, recovering)
VALUES ('Sword Newbie', 1, 50, 0, 50, 0, 35, 'assets/sprites/enemies/trainer1.png', 'TRAINING3', 4, 4, 15, 1, false);
insert into equipped_item (character_id, equipment_id, slot)
values (3, 24, 'LEFTHAND');

INSERT INTO CHARACTER (name, level, hp, armor, max_hp, max_armor, atk, sprite_path, character_type, morale, max_morale, experience_point, user_id, recovering)
VALUES ('Armored Partner', 1, 50, 300, 50, 300, 8, 'assets/sprites/enemies/trainer2.png', 'TRAINING4', 4, 4, 15, 1, false);

INSERT INTO CHARACTER (name, level, hp, armor, max_hp, max_armor, atk, sprite_path, character_type, morale, max_morale, experience_point, user_id, recovering)
VALUES ('Bandit Outlaw', 1, 55, 50, 55, 50, 8, '', 'BRIGAND_THUG', 4, 4, 15, 1, false);

INSERT INTO CHARACTER (name, level, hp, armor, max_hp, max_armor, atk, sprite_path, character_type, morale, max_morale, experience_point, user_id, recovering)
VALUES ('Bandit Hunter', 2, 55, 25, 55, 25, 8, '', 'BRIGAND_POACHER', 4, 4, 17, 1, false);