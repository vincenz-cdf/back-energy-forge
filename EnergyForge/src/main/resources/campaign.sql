insert into app_user (email, login, password, coin, energy) values ('overloadPatri@abc.com', 'batch_energy_forge', 'OoOA&é00xAeç', 0, 0);

--Brigand Thug, 25HEAD, 40BODY
INSERT INTO CHARACTER (name, level, hp, armor, max_hp, max_armor, atk, sprite_path, character_type, morale, max_morale, experience_point, user_id)
VALUES ('Training Partner', 1, 50, 0, 50, 0, 8, 'assets/sprites/enemies/trainer1.png', 'TRAINING1', 4, 4, 15, 1);

INSERT INTO CHARACTER (name, level, hp, armor, max_hp, max_armor, atk, sprite_path, character_type, morale, max_morale, experience_point, user_id)
VALUES ('Training Partner', 1, 50, 0, 50, 0, 8, 'assets/sprites/enemies/trainer1.png', 'TRAINING2', 4, 4, 15, 1);
insert into equipped_item (character_id, equipment_id, slot)
values (2, 22, 'RIGHTHAND');

INSERT INTO CHARACTER (name, level, hp, armor, max_hp, max_armor, atk, sprite_path, character_type, morale, max_morale, experience_point, user_id)
VALUES ('Sword Newbie', 1, 50, 0, 50, 0, 8, 'assets/sprites/enemies/trainer1.png', 'TRAINING3', 4, 4, 15, 1);
insert into equipped_item (character_id, equipment_id, slot)
values (3, 4, 'LEFTHAND');

INSERT INTO CHARACTER (name, level, hp, armor, max_hp, max_armor, atk, sprite_path, character_type, morale, max_morale, experience_point, user_id)
VALUES ('Bandit Outlaw', 1, 55, 50, 55, 50, 8, '', 'BRIGAND_THUG', 4, 4, 15, 1);

INSERT INTO CHARACTER (name, level, hp, armor, max_hp, max_armor, atk, sprite_path, character_type, morale, max_morale, experience_point, user_id)
VALUES ('Bandit Hunter', 2, 55, 25, 55, 25, 8, '', 'BRIGAND_POACHER', 4, 4, 17, 1);