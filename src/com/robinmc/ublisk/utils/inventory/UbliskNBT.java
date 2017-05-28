package com.robinmc.ublisk.utils.inventory;

public enum UbliskNBT {
	
	ITEM_DROPPABLE("UbliskItemDroppable"),
	
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
