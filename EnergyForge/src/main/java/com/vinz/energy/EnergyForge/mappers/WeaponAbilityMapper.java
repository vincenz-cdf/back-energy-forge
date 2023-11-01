package com.vinz.energy.EnergyForge.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vinz.energy.EnergyForge.db.equipments.abilities.WeaponAbility;
import com.vinz.energy.EnergyForge.dto.equipments.abilities.WeaponAbilityDto;

@Mapper(componentModel = "spring", uses = {WeaponMapper.class, EquipmentAbilityMapper.class, EquippedItemMapper.class,})
public interface WeaponAbilityMapper {
	
    @Mapping(source = "weapon.id", target = "weaponId")
    @Mapping(source = "ability.id", target = "equipmentAbilityId")
    @Mapping(source = "ability.name", target = "equipmentAbilityName")
    @Mapping(source = "ability.description", target = "equipmentAbilityDescription")
    @Mapping(source = "ability.value", target = "equipmentAbilityValue")
    @Mapping(source = "ability.secondaryValue", target = "equipmentAbilitySecondaryValue")
    @Mapping(source = "ability.effect", target = "equipmentAbilityEffect")
    WeaponAbilityDto toDto(WeaponAbility weaponAbility);

    @Mapping(source = "weaponId", target = "weapon.id")
    @Mapping(target = "ability.id", source = "equipmentAbilityId")
    @Mapping(target = "ability.name", source = "equipmentAbilityName")
    @Mapping(target = "ability.description", source = "equipmentAbilityDescription")
    @Mapping(target = "ability.value", source = "equipmentAbilityValue")
    @Mapping(target = "ability.secondaryValue", source = "equipmentAbilitySecondaryValue")
    @Mapping(target = "ability.effect", source = "equipmentAbilityEffect")
    WeaponAbility toEntity(WeaponAbilityDto weaponAbilityDto);

    List<WeaponAbilityDto> toDtoList(List<WeaponAbility> weaponAbilities);

    List<WeaponAbility> toEntityList(List<WeaponAbilityDto> weaponAbilityDtos);

}
