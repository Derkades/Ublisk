package com.robinmc.ublisk;

public enum Helper {
	
	ROBINMC("RobinMC (Robin)", 
			"https://goo.gl/Aay9Ry", 
			"Plugin development, bug testing, landscaping, a bit of building, textures, quests, abilities, music"),
	
	JARICK("EpicC4Build (Jarick)", 
			"No link provided", 
			"Building (a lot of building)"),
	
	SANDERP("SenpaiSander (Sander)", 
			"https://www.youtube.com/channel/UC7gVEC704565MNzCB6OS3Ew", 
			"Building, quests, 3D models for weapons"),
	
	JERRIJN("Jerrijn (Jerrijn)", 
			"No link provided", 
			"3D models for weapons, abilities"),
	
	CHASPYR("Chaspyr (Casper)", 
			"https://twitter.com/Chaspyrr", 
			"A bit of building, bug testing, 3D models for weapons, complaining that he can't play yet"),
	
	/*GLITCHERDOTBE("GlitcherDOTbe", 
			"http://www.planetminecraft.com/texture_pack/glitchs-3d-addons-pack-19/", 
			"Textures"),*/
	
	ROTTENNUGGET("RottenNugget", 
			"https://www.reddit.com/r/Minecraft/comments/2922fb/3d_models_resource_pack/", 
			"3D models for some blocks and items"),
	
	/*PARKER("MrParkerl11", 
			"https://goo.gl/WFW9zm", 
			"Built one windmill. Yep, that's it."), */
	
	SYDAN("SirSydan (Sydan)", 
			"https://www.youtube.com/channel/UCFl5VN9R5uEszEoLrYgTOoA", 
			"Music"),
	
	XISUMA("xisumavoid", 
			"https://www.youtube.com/user/xisumavoid", 
			"I used some of his modular resourcepacks in the Ublisk pack."),
	
	SANDER_GAMING("Sander_Gaming (Sander)", 
			"https://www.youtube.com/channel/UCW-jqEMRdjgimFtarWZGGsg", 
			"???"),
	
	LARISSA("LarissaMC (Larissa)", 
			"No link provided", 
			"Building"),
	
	;
	
	private String ign;
	private String link;
	private String function;
	
	Helper(String ign, String link, String function){
		this.ign = ign;
		this.link = link;
		this.function = function;
	}
	
	public String getIgn(){
		if (ign == ""){
			return "N/A";
		} else {
			return ign;
		}
	}
	
	public String getLink(){
		if (link == ""){
			return "N/A";
		} else {
			return link;
		}		
	}
	
	public String getFunction(){
		return function;
	}
	
	public static Helper fromString(String text) {
		if (text != null) {
			for (Helper helper: Helper.values()) {
				if (text.equalsIgnoreCase(helper.ign)) {
					return helper;
				}
			}
		}
		return null;
	}
	
}
