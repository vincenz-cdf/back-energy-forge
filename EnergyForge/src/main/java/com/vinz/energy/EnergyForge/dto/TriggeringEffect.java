package com.vinz.energy.EnergyForge.dto;

import java.io.Serializable;

import com.vinz.energy.EnergyForge.db.enums.Effect;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TriggeringEffect implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Effect effect;
	private Long userId;
	private Long characterId;
	private Long weaponId;
	
	private int meleeShieldValue;
	private int rangedShieldValue;
	
	public Effect getEffect() {
		return effect;
	}
	public void setEffect(Effect effect) {
		this.effect = effect;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getCharacterId() {
		return characterId;
	}
	public void setCharacterId(Long characterId) {
		this.characterId = characterId;
	}
	public Long getWeaponId() {
		return weaponId;
	}
	public void setWeaponId(Long weaponId) {
		this.weaponId = weaponId;
	}
	public int getMeleeShieldValue() {
		return meleeShieldValue;
	}
	public void setMeleeShieldValue(int meleeShieldValue) {
		this.meleeShieldValue = meleeShieldValue;
	}
	public int getRangedShieldValue() {
		return rangedShieldValue;
	}
	public void setRangedShieldValue(int rangedShieldValue) {
		this.rangedShieldValue = rangedShieldValue;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
