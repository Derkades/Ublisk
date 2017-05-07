package com.robinmc.ublisk.utils.perm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum PermissionGroup {
	
	DEFAULT(null, "Default", "&7&lMember&8:"),
	
	MODERATOR(PermissionGroup.DEFAULT, "Moderator", "&e&lModerator&8:&6&l",
			Permission.COMMAND_MUTE,
			Permission.COMMAND_DEBUG),
	
	BUILDER(PermissionGroup.MODERATOR, "Builder", "&c&lBuilder&8:&6&l",
			Permission.BUILDER_MODE),
	
	HELPER(PermissionGroup.BUILDER, "Helper", "Helper",
			Permission.COMMAND_DEBUG),

	OWNER(PermissionGroup.MODERATOR, "Owner", "&2&lOwner&8:&6&l");
	
	private PermissionGroup parent;
	private String name;
	private String prefix;
	private List<Permission> permissions;
	
	PermissionGroup(PermissionGroup parent, String name, String prefix, Permission... permissions){
		this.parent = parent;
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
