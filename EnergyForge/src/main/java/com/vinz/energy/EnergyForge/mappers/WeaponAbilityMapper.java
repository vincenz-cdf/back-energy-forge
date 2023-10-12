package com.vinz.energy.EnergyForge.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vinz.energy.EnergyForge.db.equipments.abilities.WeaponAbility;
import com.vinz.energy.EnergyForge.dto.WeaponAbilityDto;

@Mapper(componentModel = "spring", uses = {WeaponMapper.class, EquipmentAbilityMapper.class, EquippedItemMapper.class,})
public interface WeaponAbilityMapper {
	
    @Mapping(source = "weapon", target = "weapon")
    @Mapping(source = "ability", target = "ability")
    WeaponAbilityDto toDto(WeaponAbility weaponAbility);

    @Mapping(source = "weapon", target = "weapon")
    @Mapping(source = "ability", target = "ability")
    WeaponAbility toEntity(WeaponAbilityDto weaponAbilityDto);

    List<WeaponAbilityDto> toDtoList(List<WeaponAbility> weaponAbilities);

    List<WeaponAbility> toEntityList(List<WeaponAbilityDto> weaponAbilityDtos);

}
