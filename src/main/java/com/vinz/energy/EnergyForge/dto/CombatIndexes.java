package com.vinz.energy.EnergyForge.dto;



import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CombatIndexes {

	private Integer characterIndex;
	private Long characterId;
	private WeaponLightDto weapon = null;
	private WeaponLightDto shield = null;
	private ArmorLightDto bodyArmor = null;
	private ArmorLightDto headArmor = null;
	public Long getCharacterId() {
		return characterId;
	}
	public void setCharacterId(Long characterId) {
		this.characterId = characterId;
	}
	public WeaponLightDto getWeapon() {
		return weapon;
	}
	public void setWeapon(WeaponLightDto weapon) {
		this.weapon = weapon;
	}
	public WeaponLightDto getShield() {
		return shield;
	}
	public void setShield(WeaponLightDto shield) {
		this.shield = shield;
	}
	public ArmorLightDto getBodyArmor() {
		return bodyArmor;
	}
	public void setBodyArmor(ArmorLightDto bodyArmor) {
		this.bodyArmor = bodyArmor;
	}
	public ArmorLightDto getHeadArmor() {
		return headArmor;
	}
	public void setHeadArmor(ArmorLightDto headArmor) {
		this.headArmor = headArmor;
	}
	public Integer getCharacterIndex() {
		return characterIndex;
	}
	public void setCharacterIndex(Integer characterIndex) {
		this.characterIndex = characterIndex;
	}
	
	
	
}
