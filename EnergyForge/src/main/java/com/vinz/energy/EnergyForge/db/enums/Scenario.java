package com.vinz.energy.EnergyForge.db.enums;

public enum Scenario {
	CONTRACT,
	PVP,
	
	
	//Campaign
	T_L1,
	T_L2,
	T_L3,
	T_L4;
	
	
	
    public String traductEnumToLabel() {
        switch(this) {
            case T_L1:
                return "Tutorial 1";
            case T_L2:
                return "Tutorial 2";
            case T_L3:
                return "Tutorial 3";
            case T_L4:
                return "Tutorial 3";
            default:
                break;
        }
        return "Unknown";
    }
    
    public boolean isCampaign() {
        switch(this) {
            case T_L1, T_L2, T_L3, T_L4:
                return true;
            default:
                break;
        }
        return false;
    }
	
}
