package com.vinz.energy.EnergyForge.db.enums;

public enum CharacterType {
	HERO, 
	
	TRAINING1,TRAINING2,TRAINING3,TRAINING4,
	
	BRIGAND_THUG, BRIGAND_POACHER;
	
    public boolean isNpc() {
        switch(this) {
            case BRIGAND_THUG, BRIGAND_POACHER:
                return true;
            default:
                break;
        }
        return false;
    }
	
    public int shieldPercent() {
        switch(this) {
            case BRIGAND_THUG:
                return 33;
            default:
                break;
        }
        return 150;
    }
    
    public int headPercent() {
        switch(this) {
            case BRIGAND_THUG, BRIGAND_POACHER:
                return 50;
            default:
                break;
        }
        return 0;
    }
}
