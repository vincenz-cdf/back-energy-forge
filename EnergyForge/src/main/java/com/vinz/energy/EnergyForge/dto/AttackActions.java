package com.vinz.energy.EnergyForge.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class AttackActions implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Integer> atkAmount = new ArrayList<>();
	private Integer atkCount = 1;
	private Integer backlash = 0;
	
	public List<Integer> getAtkAmount() {
		return atkAmount;
	}
	public void setAtkAmount(List<Integer> atkAmount) {
		this.atkAmount = atkAmount;
	}
	public Integer getAtkCount() {
		return atkCount;
	}
	public void setAtkCount(Integer atkCount) {
		this.atkCount = atkCount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getBacklash() {
		return backlash;
	}
	public void setBacklash(Integer backlash) {
		this.backlash = backlash;
	}
	public AttackActions(List<Integer> atkAmount, Integer atkCount, Integer backlash) {
		super();
		this.atkAmount = atkAmount;
		this.atkCount = atkCount;
		this.backlash = backlash;
	}	
	public AttackActions(List<Integer> atkAmount, Integer atkCount) {
		super();
		this.atkAmount = atkAmount;
		this.atkCount = atkCount;
	}	
	public AttackActions() {
		super();
	}	
	
}
