package com.vinz.energy.EnergyForge.db.equipments;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "equipment")
@Inheritance(strategy = InheritanceType.JOINED)
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer value;
    @Column(nullable = false)
    private String spritePath;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "slot", nullable = false)
    private EquipmentSlot slot;
    
    @OneToMany(mappedBy = "equipment")
    private List<EquippedItem> equippedItems = new ArrayList<EquippedItem>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getSpritePath() {
		return spritePath;
	}

	public void setSpritePath(String spritePath) {
		this.spritePath = spritePath;
	}

	public EquipmentSlot getSlot() {
		return slot;
	}

	public void setSlot(EquipmentSlot slot) {
		this.slot = slot;
	}

	public List<EquippedItem> getEquippedItems() {
		return equippedItems;
	}

	public void setEquippedItems(List<EquippedItem> equippedItems) {
		this.equippedItems = equippedItems;
	}

    
}
