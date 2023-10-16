package com.vinz.energy.EnergyForge.db.enums;

public enum Scenario {
	CONTRACT,
	PVP,
	
	
	//Campaign
	T_L1;
	
	
	
    public String traductEnumToLabel() {
        switch(this) {
            case T_L1:
                return "Tutorial 1";
            default:
                break;
        }
        return "Unknown";
    }
    
    public boolean isCampaign() {
        switch(this) {
            case T_L1:
                return true;
            default:
                break;
        }
        return false;
    }
	
}
