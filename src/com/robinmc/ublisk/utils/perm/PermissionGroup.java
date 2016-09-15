package com.robinmc.ublisk.utils.perm;

import java.util.Arrays;
import java.util.List;

import com.robinmc.ublisk.utils.exception.GroupNotFoundException;

public enum PermissionGroup {
	
	DEFAULT("Default", "&7&lMember&8:"),
	BUILDER("Builder", "&c&lBuilder&8:&6&l"),
	MODERATOR("Moderator", "&e&lModerator&8:&6&l",
			Permission.COMMAND_MUTE,
			Permission.COMMAND_DEBUG),
	OWNER("Owner", "&2&lOwner&8:&6&l", 
			Permission.COMMAND_DEBUG, 
			Permission.COMMAND_MUTE, 
			Permission.COMMANDLOG
			);
	
	private String name;
	private String prefix;
	private List<Permission> permissions;
	
	PermissionGroup(String name, String prefix, Permission... permissions){
		this.permissions = Arrays.asList(permissions);
		this.name = name;
		this.prefix = prefix;
	}
	
	public String getName(){
		return name;
	}
	
	public String getPrefix(){
		return prefix.replace("&", "§");
	}
	
	public List<Permission> getPermissions(){
		return permissions;
	}
	
	public boolean hasPermission(Permission perm){
		return permissions.contains(perm);
	}
	
	public static PermissionGroup fromString(String string) throws GroupNotFoundException{
		for (PermissionGroup group : PermissionGroup.values()){
			if (group.getName().equalsIgnoreCase(string)){
				return group;
			}
		}
		
		throw new GroupNotFoundException();
	}

}
