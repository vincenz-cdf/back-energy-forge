package com.vinz.energy.EnergyForge.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "app_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;
    
    @Builder.Default
    @Column(nullable = false)
    private Integer energy = 0;
    
    @Builder.Default
    @Column(nullable = false)
    private Integer coin = 0;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserItem> userItems;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<CombatInstance> combatInstances;

}
