package com.robinmc.ublisk.utils.inventory;

public enum UbliskNBT {
	
	IDENTIFIER("UbliskItemIdentifier"),
	ITEM_DROPPABLE("UbliskItemDroppable"),
	HEALTH_BONUS("UbliskHealthBonus"),
	
	;
	
	private String nbtKey;
	
	UbliskNBT(String nbtKey){
		this.nbtKey = nbtKey;
	}
	
	@Override
	public String toString(){
		return nbtKey;
	}

}
