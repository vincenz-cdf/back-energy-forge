alter table app_user
add diamond int default 0;

update "character" 
set experience_point = 75
where character_type in ('TRAINING1', 'TRAINING2', 'TRAINING3', 'TRAINING4', 'BRIGAND_THUG');

update "character" 
set experience_point = 88
where character_type in ('BRIGAND_POACHER');