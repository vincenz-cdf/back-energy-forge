package com.vinz.energy.EnergyForge.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.vinz.energy.EnergyForge.db.enums.CombatStatus;
import com.vinz.energy.EnergyForge.db.enums.Scenario;
import com.vinz.energy.EnergyForge.dto.CombatIndexes;
import com.vinz.energy.EnergyForge.dto.CumulatedTeamStats;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "combat_instance")
public class CombatInstance implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Column(nullable = false)
	private String label;
	
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
	private CombatStatus status;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
	private User user;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false)
	private List<CombatIndexes> indexes = new ArrayList<CombatIndexes>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
	private Scenario scenario;
    
    @ManyToMany(cascade = {
    	    CascadeType.PERSIST,
    	    CascadeType.MERGE
    	})
    	@JoinTable(name = "combat_instance_characters",
    	    joinColumns = @JoinColumn(name = "combat_instance_id"),
    	    inverseJoinColumns = @JoinColumn(name = "character_id")
    	)
    	private List<Character> characters = new ArrayList<>();
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = true)
    private CumulatedTeamStats afterMathInfo;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CombatStatus getStatus() {
		return status;
	}

	public void setStatus(CombatStatus status) {
		this.status = status;
	}

	public List<CombatIndexes> getIndexes() {
		return indexes;
	}

	public void setIndexes(List<CombatIndexes> indexes) {
		this.indexes = indexes;
	}

	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<Character> getCharacters() {
		return characters;
	}

	public void setCharacters(List<Character> characters) {
		this.characters = characters;
	}

	public CumulatedTeamStats getAfterMathInfo() {
		return afterMathInfo;
	}

	public void setAfterMathInfo(CumulatedTeamStats afterMathInfo) {
		this.afterMathInfo = afterMathInfo;
	}
}
