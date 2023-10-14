package com.vinz.energy.EnergyForge.dto;

import java.io.Serializable;

public class AttackActions implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer atkAmount;
	private Integer atkCount = 1;
	public Integer getAtkAmount() {
		return atkAmount;
	}
	public void setAtkAmount(Integer atkAmount) {
		this.atkAmount = atkAmount;
	}
	public Integer getAtkCount() {
		return atkCount;
	}
	public void setAtkCount(Integer atkCount) {
		this.atkCount = atkCount;
	}
	
	
}
