package com.vinz.energy.EnergyForge.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.ToString;

@ToString
public class CumulatedTeamStats {

	private Integer totalHp = 0;
	private Integer totalArmor = 0;
	private List<CharacterDto> yourDto = new ArrayList<CharacterDto>();
	
	private Integer enemyHp = 0;
	private Integer enemyArmor = 0;
	private List<CharacterDto> enemyDto = new ArrayList<CharacterDto>();
	
	public Integer getTotalHp() {
		return totalHp;
	}
	public void setTotalHp(Integer totalHp) {
		this.totalHp = totalHp;
	}
	public Integer getTotalArmor() {
		return totalArmor;
	}
	public void setTotalArmor(Integer totalArmor) {
		this.totalArmor = totalArmor;
	}
	public Integer getEnemyHp() {
		return enemyHp;
	}
	public void setEnemyHp(Integer enemyHp) {
		this.enemyHp = enemyHp;
	}
	public Integer getEnemyArmor() {
		return enemyArmor;
	}
	public void setEnemyArmor(Integer enemyArmor) {
		this.enemyArmor = enemyArmor;
	}
	public List<CharacterDto> getYourDto() {
		return yourDto;
	}
	public void setYourDto(List<CharacterDto> yourDto) {
		this.yourDto = yourDto;
	}
	public List<CharacterDto> getEnemyDto() {
		return enemyDto;
	}
	public void setEnemyDto(List<CharacterDto> enemyDto) {
		this.enemyDto = enemyDto;
	}
	
	
}
