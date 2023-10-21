package com.vinz.energy.EnergyForge.dto;

import java.io.Serializable;

import com.vinz.energy.EnergyForge.db.enums.Effect;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class TriggeringEffect implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Effect effect;
	private Long userId;
	private Long characterId;
	private Integer weaponId;
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
	public Integer getWeaponId() {
		return weaponId;
	}
	public void setWeaponId(Integer weaponId) {
		this.weaponId = weaponId;
	}
	
	

}
