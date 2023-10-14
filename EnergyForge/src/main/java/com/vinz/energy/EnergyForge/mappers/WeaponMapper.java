package com.vinz.energy.EnergyForge.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vinz.energy.EnergyForge.db.equipments.Weapon;
import com.vinz.energy.EnergyForge.dto.equipments.WeaponDto;

@Mapper(componentModel = "spring", uses = {EquippedItemMapper.class, WeaponAbilityMapper.class, UserItemMapper.class})
public interface WeaponMapper {
	@Mapping(source = "weaponAbilities", target = "weaponAbilities")
	WeaponDto toDto(Weapon weapon);
	
	@Mapping(source = "weaponAbilities", target = "weaponAbilities")
	Weapon toEntity(WeaponDto weaponDto);

	List<WeaponDto> toDtoList(List<Weapon> weapons);

	List<Weapon> toEntityList(List<WeaponDto> weaponDtos);
}


