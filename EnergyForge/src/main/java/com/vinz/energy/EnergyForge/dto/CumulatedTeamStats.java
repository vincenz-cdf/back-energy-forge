package com.vinz.energy.EnergyForge.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.ToString;

@ToString
public class CumulatedTeamStats implements Serializable {
	
	public static class TeamStats implements Serializable {
	    private static final long serialVersionUID = 1L;
		private Integer hp = 0;
	    private Integer armor = 0;
	    private List<CharacterDto> dto = new ArrayList<CharacterDto>();
	    private List<Integer> deathThreshold = new ArrayList<Integer>();
		public Integer getHp() {
			return hp;
		}
		public void setHp(Integer hp) {
			this.hp = hp;
		}
		public Integer getArmor() {
			return armor;
		}
		public void setArmor(Integer armor) {
			this.armor = armor;
		}
		public List<CharacterDto> getDto() {
			return dto;
		}
		public void setDto(List<CharacterDto> dto) {
			this.dto = dto;
		}
		public List<Integer> getDeathThreshold() {
			return deathThreshold;
		}
		public void setDeathThreshold(List<Integer> deathThreshold) {
			this.deathThreshold = deathThreshold;
		}

	    
	}

    private static final long serialVersionUID = 1L;
	private TeamStats yourStats = new TeamStats();
    private TeamStats enemyStats = new TeamStats();

	public TeamStats getYourStats() {
		return yourStats;
	}
	public void setYourStats(TeamStats yourStats) {
		this.yourStats = yourStats;
	}
	public TeamStats getEnemyStats() {
		return enemyStats;
	}
	public void setEnemyStats(TeamStats enemyStats) {
		this.enemyStats = enemyStats;
	}
	
}
