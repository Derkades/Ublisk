package com.robinmc.ublisk.utils;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Recipe;

public class RecipeUtils {
	
	public static void removeVanillaRecipes(){
		Iterator<Recipe> iterator = Bukkit.getServer().recipeIterator();
		while (iterator.hasNext()){
			iterator.next();
			iterator.remove();
		}
	}

}
