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

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Militia Spear', 180, 'assets/sprites/equipments/weapon/melee/onehanded/militiaspear.png', 'LEFTHAND');
INSERT INTO weapon (equipment_id, atk_value, is_melee) VALUES (8, 28, TRUE);
INSERT INTO equipment_ability (name, description, value, effect) VALUES ('Spearwall', 'Foreach enemy has 20% to cancel their next attack but the spear deal 50% less damage, melee member of your party can''t attack cancelled enemies', 20, 'SPEARWALL');
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
INSERT INTO equipment_ability (name, description, value, effect) VALUES ('Shieldwall', '15% to block the highest damage', 15, 'SHIELDWALL');
INSERT INTO weapon_ability (weapon_id, ability_id) VALUES (22, 13);

INSERT INTO equipment (name, value, sprite_path, slot) VALUES ('Copper Helmet T2', 100, 'assets/sprites/equipments/armor/head/copperhelm2.png', 'HEAD');
INSERT INTO armor (equipment_id, armor_value) VALUES (23, 50);
INSERT INTO weapon_ability (weapon_id, ability_id) VALUES (5, 5);