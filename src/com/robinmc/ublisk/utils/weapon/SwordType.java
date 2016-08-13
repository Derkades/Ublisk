package com.robinmc.ublisk.utils.weapon;

import org.bukkit.Material;

import com.robinmc.ublisk.utils.enums.Classes;

public enum SwordType implements WeaponType {

	WOOD {

		@Override
		public Material getMaterial() {
			return Material.WOOD_SWORD;
		}
		
	},
	
	STONE {

		@Override
		public Material getMaterial() {
			return Material.STONE_SWORD;
		}
		
	},
	
	IRON {

		@Override
		public Material getMaterial() {
			return Material.IRON_SWORD;
		}
		
	},
	
	GOLD {

		@Override
		public Material getMaterial() {
			return Material.GOLD_SWORD;
		}
		
	},
	
	DIAMOND {

		@Override
		public Material getMaterial() {
			return Material.DIAMOND_SWORD;
		}
		
	};

	@Override
	public Classes getClass(Classes clazz) {
		return Classes.SWORDSMAN;
	}

}
