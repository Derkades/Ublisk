package com.robinmc.ublisk.utils.perm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

public enum PermissionGroup {
	
	DEFAULT(null, "Member", ChatColor.GRAY),
	
	MODERATOR(PermissionGroup.DEFAULT, "Moderator", ChatColor.YELLOW,
			Permission.COMMAND_MUTE,
			Permission.COMMAND_DEBUG),
	
	BUILDER(PermissionGroup.MODERATOR, "Builder", ChatColor.RED,
			Permission.BUILDER_MODE),
	
	HELPER(PermissionGroup.BUILDER, "Helper", ChatColor.DARK_AQUA,
			Permission.COMMAND_DEBUG),

	OWNER(PermissionGroup.MODERATOR, "Owner", ChatColor.DARK_GREEN);
	
	private PermissionGroup parent;
	private String name;
	private ChatColor prefixColor;
	private List<Permission> permissions;
	
	PermissionGroup(PermissionGroup parent, String name, ChatColor prefixColor, Permission... permissions){
		this.parent = parent;
		this.name = name;
		this.prefixColor = prefixColor;
		this.permissions = Arrays.asList(permissions);
	}
	
	public String getName(){
		return name;
	}
	
	public ChatColor getPrefixColor(){
		return prefixColor;
	}
	
	public ChatColor getNameColor(){
		if (this.equals(PermissionGroup.DEFAULT)){
			return ChatColor.WHITE;
		} else {
			return ChatColor.GOLD;
		}
	}
	
	public boolean nameBold(){
		return !this.equals(PermissionGroup.DEFAULT);
	}
	
	public List<Permission> getPermissions(){
		List<Permission> permissions = new ArrayList<Permission>();
		PermissionGroup current = this;
		while (current != null){
			for (Permission permission : current.permissions){
				if (!permissions.contains(permission)){
					permissions.add(permission);
				}
			}
			current = current.parent;
		}
		return permissions;
	}
	
	@Deprecated
	public boolean hasPermission(Permission perm){
		return permissions.contains(perm);
	}
	
	public static PermissionGroup fromString(String string) {
		for (PermissionGroup group : PermissionGroup.values()){
			if (group.getName().equalsIgnoreCase(string)){
				return group;
			}
		}
		return null;
	}

}
