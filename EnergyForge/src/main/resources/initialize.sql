INSERT INTO equipment (name, value, sprite_path) VALUES ('Hood', 40, 'assets/sprites/equipments/armor/head/hood.png');
INSERT INTO armor (equipment_id, armor_value, slot) VALUES (1, 30, 'HEAD');

INSERT INTO equipment (name, value, sprite_path) VALUES ('Mouthpiece', 15, 'assets/sprites/equipments/armor/head/mouthpiece.png');
INSERT INTO armor (equipment_id, armor_value, slot) VALUES (2, 10, 'HEAD');

select * from equipped_item
select * from "character" c 

SELECT c FROM "character" c LEFT JOIN FETCH c.equipped_items WHERE c.user.id = 1 ORDER BY c.level ASC