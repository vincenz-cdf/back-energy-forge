INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Hood', 40, 'assets/sprites/equipments/armor/head/hood.png', 'HEAD');
INSERT INTO armor (equipment_id, armor_value) VALUES (1, 30);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Mouthpiece', 15, 'assets/sprites/equipments/armor/head/mouthpiece.png', 'HEAD');
INSERT INTO armor (equipment_id, armor_value) VALUES (2, 10);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Greatsword', 3200, 'assets/sprites/equipments/weapon/melee/twohanded/greatsword.png', 'ZWEIHAND');
INSERT INTO weapon (equipment_id, atk_value, is_melee) VALUES (3, 93, TRUE);
INSERT INTO equipment_ability (name, description, value, effect) VALUES ('Swing and Split', '5% chance to reattack, if it succeed, has 5% chance that the second attack to deal double damage', 5, 'SWINGSPLIT');
INSERT INTO weapon_ability (weapon_id, ability_id) VALUES (3, 1);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Shortsword', 350, 'assets/sprites/equipments/weapon/melee/onehanded/shortsword.png', 'LEFTHAND');
INSERT INTO weapon (equipment_id, atk_value, is_melee) VALUES (4, 35, TRUE);
INSERT INTO equipment_ability (name, description, value, effect) VALUES ('Riposte', '10% chance to reattack', 10, 'RIPOSTE');
INSERT INTO weapon_ability (weapon_id, ability_id) VALUES (4, 2);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Woodcutter Axe', 400, 'assets/sprites/equipments/weapon/melee/twohanded/woodcutteraxe.png', 'ZWEIHAND');
INSERT INTO weapon (equipment_id, atk_value, is_melee) VALUES (5, 53, TRUE);
INSERT INTO equipment_ability (name, description, value, effect) VALUES ('Round Swing', '20% chance to reattack but 10% to also hit one of your neighbour, triggered a second time if one of the roll success', 20, 'ROUNDSWING');
INSERT INTO weapon_ability (weapon_id, ability_id) VALUES (5, 3);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Pitchfork', 150, 'assets/sprites/equipments/weapon/melee/twohanded/pitchfork.png', 'ZWEIHAND');
INSERT INTO weapon (equipment_id, atk_value, is_melee) VALUES (6, 40, FALSE);
INSERT INTO equipment_ability (name, description, value, effect) VALUES ('Repel', 'If no melee left in your team, apply Stagger on the highest damage dealer', 50, 'REPEL');
INSERT INTO weapon_ability (weapon_id, ability_id) VALUES (6, 4);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Hatchet', 210, 'assets/sprites/equipments/weapon/melee/onehanded/hatchet.png', 'LEFTHAND');
INSERT INTO weapon (equipment_id, atk_value, is_melee) VALUES (7, 33, TRUE);
INSERT INTO equipment_ability (name, description, value, effect) VALUES ('Shield Splitter', 'Remove one shield bonuses from enemy party', 100, 'SHIELDSPLIT');
INSERT INTO weapon_ability (weapon_id, ability_id) VALUES (7, 5);
INSERT INTO weapon_ability (weapon_id, ability_id) VALUES (5, 5);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Militia Spear', 180, 'assets/sprites/equipments/weapon/melee/onehanded/militiaspear.png', 'LEFTHAND');
INSERT INTO weapon (equipment_id, atk_value, is_melee) VALUES (8, 28, TRUE);
INSERT INTO equipment_ability (name, description, value, effect) VALUES ('Spearwall', '20% to cancel the highest attack but your spear deal 50% less damage, if the roll is a success, reroll for the next attack but your melee members can''t attack cancelled enemies', 20, 'SPEARWALL');
INSERT INTO weapon_ability (weapon_id, ability_id) VALUES (8, 6);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Bludgeon', 90, 'assets/sprites/equipments/weapon/melee/onehanded/bludgeon.png', 'LEFTHAND');
INSERT INTO weapon (equipment_id, atk_value, is_melee) VALUES (9, 28, TRUE);
INSERT INTO equipment_ability (name, description, value, effect) VALUES ('Knock Out', '75% chance to stun the highest attacker, but this weapon deals 50% if succeded', 75, 'KO');
INSERT INTO weapon_ability (weapon_id, ability_id) VALUES (9, 7);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Wooden Stick', 35, 'assets/sprites/equipments/weapon/melee/onehanded/woodenstick.png', 'LEFTHAND');
INSERT INTO weapon (equipment_id, atk_value, is_melee) VALUES (10, 20, TRUE);
INSERT INTO weapon_ability (weapon_id, ability_id) VALUES (10, 7);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Reinforced Wooden Flail', 300, 'assets/sprites/equipments/weapon/melee/onehanded/reinforcedwoodenflail.png', 'LEFTHAND');
INSERT INTO weapon (equipment_id, atk_value, is_melee) VALUES (11, 33, TRUE);
INSERT INTO equipment_ability (name, description, value, effect) VALUES ('Lash', 'If the opponent has a shield, this weapon has 100% chance to hit and deal 160% damage. If you have multiple flail members, the enemy shields has to be equivalent to trigger Lash', 160, 'LASH');
INSERT INTO weapon_ability (weapon_id, ability_id) VALUES (11, 8);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Wooden Flail', 40, 'assets/sprites/equipments/weapon/melee/onehanded/woodenflail.png', 'LEFTHAND');
INSERT INTO weapon (equipment_id, atk_value, is_melee) VALUES (12, 18, TRUE);
INSERT INTO weapon_ability (weapon_id, ability_id) VALUES (12, 8);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Butcher Cleaver', 110, 'assets/sprites/equipments/weapon/melee/onehanded/butchercleaver.png', 'LEFTHAND');
INSERT INTO weapon (equipment_id, atk_value, is_melee) VALUES (13, 28, TRUE);
INSERT INTO equipment_ability (name, description, value, effect) VALUES ('Cleave', 'If the attack is successful apply a Bleed of 6 damage directly to HP', 160, 'CLEAVE');
INSERT INTO weapon_ability (weapon_id, ability_id) VALUES (13, 9);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Pickaxe', 120, 'assets/sprites/equipments/weapon/melee/onehanded/pickaxe.png', 'LEFTHAND');
INSERT INTO weapon (equipment_id, atk_value, is_melee) VALUES (14, 23, TRUE);
INSERT INTO equipment_ability (name, description, value, effect) VALUES ('Destroy Armor', 'Gain an additional damage bonus equivalent of 33% of enemy current armor but deals maximum 10 damage on HP', 150, 'DARMOR');
INSERT INTO weapon_ability (weapon_id, ability_id) VALUES (14, 10);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Dagger', 180, 'assets/sprites/equipments/weapon/melee/onehanded/dagger.png', 'LEFTHAND');
INSERT INTO weapon (equipment_id, atk_value, is_melee) VALUES (15, 25, TRUE);
INSERT INTO equipment_ability (name, description, value, effect) VALUES ('Puncture', '15% to ignore armor', 15, 'PUNCTURE');
INSERT INTO weapon_ability (weapon_id, ability_id) VALUES (15, 11);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Shortbow', 200, 'assets/sprites/equipments/weapon/ranged/shortbow.png', 'ZWEIHAND');
INSERT INTO weapon (equipment_id, atk_value, is_melee) VALUES (16, 40, FALSE);
INSERT INTO equipment_ability (name, description, value, effect) VALUES ('Quick Shot', 'This weapon has 10% chance to reattack', 10, 'QUICKSHOT');
INSERT INTO weapon_ability (weapon_id, ability_id) VALUES (16, 12);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Copper Armor T1', 20, 'assets/sprites/equipments/armor/body/copperarmor.png', 'BODY');
INSERT INTO armor (equipment_id, armor_value) VALUES (17, 10);
INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Copper Armor T2', 40, 'assets/sprites/equipments/armor/body/copperarmor.png', 'BODY');
INSERT INTO armor (equipment_id, armor_value) VALUES (18, 20);
INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Copper Armor T3', 55, 'assets/sprites/equipments/armor/body/copperarmor.png', 'BODY');
INSERT INTO armor (equipment_id, armor_value) VALUES (19, 25);
INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Copper Armor T4', 65, 'assets/sprites/equipments/armor/body/copperarmor.png', 'BODY');
INSERT INTO armor (equipment_id, armor_value) VALUES (20, 30);
INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Copper Armor T5', 75, 'assets/sprites/equipments/armor/body/copperarmor.png', 'BODY');
INSERT INTO armor (equipment_id, armor_value) VALUES (21, 35);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Wooden Shield', 100, 'assets/sprites/equipments/shield/woodenshield.png', 'RIGHTHAND');
INSERT INTO weapon (equipment_id, atk_value, is_melee) VALUES (22, 0, TRUE);
INSERT INTO equipment_ability (name, description, value, effect) VALUES ('Shieldwall', '15% to block the highest melee and ranged damage', 15, 'SHIELDWALL');
INSERT INTO weapon_ability (weapon_id, ability_id) VALUES (22, 13);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Copper Helmet T2', 100, 'assets/sprites/equipments/armor/head/copperhelm2.png', 'HEAD');
INSERT INTO armor (equipment_id, armor_value) VALUES (23, 50);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Knife', 30, 'assets/sprites/equipments/weapon/melee/onehanded/knife.png', 'LEFTHAND');
INSERT INTO weapon (equipment_id, atk_value, is_melee) VALUES (24, 18, TRUE);
INSERT INTO equipment_ability (name, description, value, effect) VALUES ('Puncture', '15% to ignore armor', 15, 'PUNCTURE');
INSERT INTO weapon_ability (weapon_id, ability_id) VALUES (24, 11);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Buckler', 45, 'assets/sprites/equipments/shield/buckler.png', 'RIGHTHAND');
INSERT INTO weapon (equipment_id, atk_value, is_melee) VALUES (25, 0, TRUE);
INSERT INTO equipment_ability (name, description, value, secondary_value, effect) VALUES ('Shieldwall', '10% to block the highest melee damage and 5% ranged', 10, 5, 'SHIELDWALL');
INSERT INTO weapon_ability (weapon_id, ability_id) VALUES (25, 14);

insert into consumable (name, description, value, sprite_path, healing_amount, healing_power) values ('Berries', 'Heal 1hp every 5mins for 50mins (10hp total)', 17, 'assets/sprites/foods/berries.png', 10, 1);
insert into consumable (name, description, value, sprite_path, healing_amount, healing_power) values ('Bread', 'Heal 2hp every 5mins for 50mins (20hp total)', 35, 'assets/sprites/foods/bread.png', 20, 2);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Headscarf', 30, 'assets/sprites/equipments/armor/head/headscarf.png', 'HEAD');
INSERT INTO armor (equipment_id, armor_value) VALUES (26, 20);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Training Cap', 60, 'assets/sprites/equipments/armor/head/woodhelm1.png', 'HEAD');
INSERT INTO armor (equipment_id, armor_value) VALUES (27, 40);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Copper Helmet T1', 80, 'assets/sprites/equipments/armor/head/copperhelm1.png', 'HEAD');
INSERT INTO armor (equipment_id, armor_value) VALUES (28, 45);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Copper Helmet T3', 140, 'assets/sprites/equipments/armor/head/copperhelm3.png', 'HEAD');
INSERT INTO armor (equipment_id, armor_value) VALUES (29, 60);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Copper Helmet T4', 150, 'assets/sprites/equipments/armor/head/copperhelm4.png', 'HEAD');
INSERT INTO armor (equipment_id, armor_value) VALUES (30, 70);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Copper Helmet T5', 200, 'assets/sprites/equipments/armor/head/copperhelm5.png', 'HEAD');
INSERT INTO armor (equipment_id, armor_value) VALUES (31, 80);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Copper Helmet T6', 250, 'assets/sprites/equipments/armor/head/copperhelm6.png', 'HEAD');
INSERT INTO armor (equipment_id, armor_value) VALUES (32, 90);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Iron Helmet T1', 250, 'assets/sprites/equipments/armor/head/ironhelm1.png', 'HEAD');
INSERT INTO armor (equipment_id, armor_value) VALUES (33, 95);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Iron Helmet T2', 300, 'assets/sprites/equipments/armor/head/ironhelm2.png', 'HEAD');
INSERT INTO armor (equipment_id, armor_value) VALUES (34, 100);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Iron Helmet T3', 350, 'assets/sprites/equipments/armor/head/ironhelm3.png', 'HEAD');
INSERT INTO armor (equipment_id, armor_value) VALUES (35, 105);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Iron Helmet T4', 350, 'assets/sprites/equipments/armor/head/ironhelm4.png', 'HEAD');
INSERT INTO armor (equipment_id, armor_value) VALUES (36, 110);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Iron Helmet T5', 450, 'assets/sprites/equipments/armor/head/ironhelm5.png', 'HEAD');
INSERT INTO armor (equipment_id, armor_value) VALUES (37, 115);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Iron Helmet T6', 500, 'assets/sprites/equipments/armor/head/ironhelm6.png', 'HEAD');
INSERT INTO armor (equipment_id, armor_value) VALUES (38, 125);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Training Armor', 10, 'assets/sprites/equipments/armor/body/woodarmor.png', 'BODY');
INSERT INTO armor (equipment_id, armor_value) VALUES (39, 5);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Iron Armor T1', 90, 'assets/sprites/equipments/armor/body/ironarmor.png', 'BODY');
INSERT INTO armor (equipment_id, armor_value) VALUES (40, 50);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Iron Armor T2', 100, 'assets/sprites/equipments/armor/body/ironarmor.png', 'BODY');
INSERT INTO armor (equipment_id, armor_value) VALUES (41, 55);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Iron Armor T3', 100, 'assets/sprites/equipments/armor/body/ironarmor.png', 'BODY');
INSERT INTO armor (equipment_id, armor_value) VALUES (42, 60);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Iron Armor T4', 150, 'assets/sprites/equipments/armor/body/ironarmor.png', 'BODY');
INSERT INTO armor (equipment_id, armor_value) VALUES (43, 65);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Iron Armor T5', 160, 'assets/sprites/equipments/armor/body/ironarmor.png', 'BODY');
INSERT INTO armor (equipment_id, armor_value) VALUES (44, 70);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Iron Armor T6', 200, 'assets/sprites/equipments/armor/body/ironarmor.png', 'BODY');
INSERT INTO armor (equipment_id, armor_value) VALUES (45, 80);

insert into app_user (id, email, login, password, coin, energy) values (1, 'overloadPatri@abc.com', 'batch_energy_forge', 'OoOA&é00xAeç', 0, 0);

--Brigand Thug, 25HEAD, 40BODY
INSERT INTO CHARACTER (name, level, hp, armor, max_hp, max_armor, atk, sprite_path, character_type, morale, max_morale, experience_point, user_id, recovering, healing_stack, healing_power, in_combat, awaiting)
VALUES ('Training Partner', 1, 50, 0, 50, 0, 8, 'assets/sprites/enemies/trainer1.png', 'TRAINING1', 4, 4, 15, 1, false, 0, 0, false, false);

INSERT INTO CHARACTER (name, level, hp, armor, max_hp, max_armor, atk, sprite_path, character_type, morale, max_morale, experience_point, user_id, recovering, healing_stack, healing_power, in_combat, awaiting)
VALUES ('Shieldbearer', 1, 50, 0, 50, 0, 8, 'assets/sprites/enemies/trainer1.png', 'TRAINING2', 4, 4, 15, 1, false, 0, 0, false, false);
insert into equipped_item (character_id, equipment_id, slot)
values (2, 22, 'RIGHTHAND');

INSERT INTO CHARACTER (name, level, hp, armor, max_hp, max_armor, atk, sprite_path, character_type, morale, max_morale, experience_point, user_id, recovering, healing_stack, healing_power, in_combat, awaiting)
VALUES ('Sword Newbie', 1, 50, 0, 50, 0, 35, 'assets/sprites/enemies/trainer1.png', 'TRAINING3', 4, 4, 15, 1, false, 0, 0, false, false);
insert into equipped_item (character_id, equipment_id, slot)
values (3, 24, 'LEFTHAND');

INSERT INTO CHARACTER (name, level, hp, armor, max_hp, max_armor, atk, sprite_path, character_type, morale, max_morale, experience_point, user_id, recovering, healing_stack, healing_power, in_combat, awaiting)
VALUES ('Armored Partner', 1, 50, 300, 50, 300, 8, 'assets/sprites/enemies/trainer2.png', 'TRAINING4', 4, 4, 15, 1, false, 0, 0, false, false);

INSERT INTO CHARACTER (name, level, hp, armor, max_hp, max_armor, atk, sprite_path, character_type, morale, max_morale, experience_point, user_id, recovering, healing_stack, healing_power, in_combat, awaiting)
VALUES ('Bandit Outlaw', 1, 50, 0, 50, 0, 8, 'assets/sprites/enemies/brigand.png', 'BRIGAND_THUG', 4, 4, 15, 1, false, 0, 0, false, false);
insert into equipped_item (character_id, equipment_id, slot) values (5, 4, 'LEFTHAND');
insert into equipped_item (character_id, equipment_id, slot) values (5, 7, 'LEFTHAND');
insert into equipped_item (character_id, equipment_id, slot) values (5, 8, 'LEFTHAND');
insert into equipped_item (character_id, equipment_id, slot) values (5, 9, 'LEFTHAND');
insert into equipped_item (character_id, equipment_id, slot) values (5, 10, 'LEFTHAND');
insert into equipped_item (character_id, equipment_id, slot) values (5, 11, 'LEFTHAND');
insert into equipped_item (character_id, equipment_id, slot) values (5, 12, 'LEFTHAND');
insert into equipped_item (character_id, equipment_id, slot) values (5, 13, 'LEFTHAND');
insert into equipped_item (character_id, equipment_id, slot) values (5, 14, 'LEFTHAND');
insert into equipped_item (character_id, equipment_id, slot) values (5, 15, 'LEFTHAND');
insert into equipped_item (character_id, equipment_id, slot) values (5, 22, 'RIGHTHAND');
insert into equipped_item (character_id, equipment_id, slot) values (5, 25, 'RIGHTHAND');
insert into equipped_item (character_id, equipment_id, slot) values (5, 5, 'ZWEIHAND');
insert into equipped_item (character_id, equipment_id, slot) values (5, 6, 'ZWEIHAND');

insert into equipped_item (character_id, equipment_id, slot) values (5, 1, 'HEAD');
insert into equipped_item (character_id, equipment_id, slot) values (5, 2, 'HEAD');
insert into equipped_item (character_id, equipment_id, slot) values (5, 26, 'HEAD');
insert into equipped_item (character_id, equipment_id, slot) values (5, 27, 'HEAD');
insert into equipped_item (character_id, equipment_id, slot) values (5, 28, 'HEAD');

insert into equipped_item (character_id, equipment_id, slot) values (5, 39, 'BODY');
insert into equipped_item (character_id, equipment_id, slot) values (5, 18, 'BODY');
insert into equipped_item (character_id, equipment_id, slot) values (5, 20, 'BODY');
insert into equipped_item (character_id, equipment_id, slot) values (5, 21, 'BODY');
insert into equipped_item (character_id, equipment_id, slot) values (5, 40, 'BODY');
insert into equipped_item (character_id, equipment_id, slot) values (5, 41, 'BODY');
insert into equipped_item (character_id, equipment_id, slot) values (5, 43, 'BODY');
insert into equipped_item (character_id, equipment_id, slot) values (5, 44, 'BODY');


INSERT INTO CHARACTER (name, level, hp, armor, max_hp, max_armor, atk, sprite_path, character_type, morale, max_morale, experience_point, user_id, recovering, healing_stack, healing_power, in_combat, awaiting)
VALUES ('Bandit Hunter', 2, 50, 0, 50, 0, 8, 'assets/sprites/enemies/brigand.png', 'BRIGAND_POACHER', 4, 4, 17, 1, false, 0, 0, false, false);
insert into equipped_item (character_id, equipment_id, slot) values (6, 16, 'ZWEIHAND');
insert into equipped_item (character_id, equipment_id, slot) values (6, 1, 'HEAD');
insert into equipped_item (character_id, equipment_id, slot) values (6, 26, 'HEAD');
insert into equipped_item (character_id, equipment_id, slot) values (6, 18, 'BODY');

INSERT INTO building (name, description, "type", sprite_path) VALUES ('Temple', 'Priestess apply fresh bandages and pray for your wounded heroes to speed up their healing time for injuries, for a price of course.', 'TEMPLE', 'assets/sprites/buildings/temple.png');
INSERT INTO building (name, description, "type", sprite_path) VALUES ('Tavern', 'Buy drinks to your soldiers to boost their morale.', 'TAVERN', 'assets/sprites/buildings/tavern.png');
INSERT INTO building (name, description, "type", sprite_path) VALUES ('Marketplace', 'The Market square. Buy all sorts of things including food, weapons and armor. The selection of market items usually refreshes everyday.', 'MARKET', 'assets/sprites/buildings/marketplace.png');