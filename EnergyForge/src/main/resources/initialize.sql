INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Hood', 40, 'assets/sprites/equipments/armor/head/hood.png', 'HEAD');
INSERT INTO armor (equipment_id, armor_value) VALUES (1, 30);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Mouthpiece', 15, 'assets/sprites/equipments/armor/head/mouthpiece.png', 'HEAD');
INSERT INTO armor (equipment_id, armor_value) VALUES (2, 10);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Greatsword', 3200, 'assets/sprites/equipments/weapon/melee/twohanded/greatsword.png', 'ZWEIHAND');
INSERT INTO weapon (equipment_id, atk_value, is_melee) VALUES (3, 93, TRUE);
INSERT INTO equipment_ability (name, description, value, effect) VALUES ('Swing and Split', 'This weapon has 5% chance to reattack, if it succeed, has 5% chance that the second attack to deal double damage', 5, 'SWINGSPLIT');
INSERT INTO weapon_ability (weapon_id, ability_id) VALUES (3, 1);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Shortsword', 350, 'assets/sprites/equipments/weapon/melee/onehanded/shortsword.png', 'LEFTHAND');
INSERT INTO weapon (equipment_id, atk_value, is_melee) VALUES (4, 35, TRUE);
INSERT INTO equipment_ability (name, description, value, effect) VALUES ('Riposte', 'This weapon has 10% chance to reattack', 10, 'RIPOSTE');
INSERT INTO weapon_ability (weapon_id, ability_id) VALUES (4, 2);


select * from user_item

select * from app_user au 
select * from equipment;
select * from weapon;
select * from weapon_ability
select * from equipped_item ei 