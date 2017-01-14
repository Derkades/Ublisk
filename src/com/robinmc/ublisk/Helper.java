package com.robinmc.ublisk;

public enum Helper {
	
	ROBINMC("RobinMC (Robin)", 
			"https://goo.gl/Aay9Ry", 
			"Plugin development, testing, building, textures, weapons, quests, music"),
	
	CHASPYR("Chaspyr (Casper)", 
			"https://twitter.com/ItsChaspyr", 
			"Building, bug testing, textures"),
	
	GLITCHERDOTBE("GlitcherDOTbe", 
			"http://www.planetminecraft.com/texture_pack/glitchs-3d-addons-pack-19/", 
			"Textures"),
	
	ROTTENNUGGET("RottenNugget", 
			"https://www.reddit.com/r/Minecraft/comments/2922fb/3d_models_resource_pack/", 
			
			"3D models"),
	
	PARKER("MrParkerl11", 
			"https://goo.gl/WFW9zm", 
			"Building"),
	
	SANDERP("SenpaiSander (Sander)", 
			"https://www.youtube.com/channel/UC7gVEC704565MNzCB6OS3Ew", 
			"Has done a lot of work for Ublisk! Building, quests, weapons, textures"),
	
	SYDAN("SirSydan (Sydan)", 
			"https://www.youtube.com/channel/UCFl5VN9R5uEszEoLrYgTOoA", 
			"Music"),
	
	XISUMA("xisumavoid", 
			"https://www.youtube.com/user/xisumavoid", 
			"Textures (stained glass, end rods, dark oak wood)"),
	
	BREECKO("Breecko (Brecht)", 
			"https://www.youtube.com/channel/UC4Slc9O6dLsia_fWrgN8Q9Q", 
			"Building, being awesome and motivating :D"),
	
	SANDER_GAMING("Sander_Gaming (Sander)", 
			"https://www.youtube.com/channel/UCW-jqEMRdjgimFtarWZGGsg", 
			"Building, textures, being the great person he is :)"),
	
	LARISSA("LarissaMC (Larissa)", 
			"No link provided", 
			"Building"),
	
	JERRIJN("Jerrijn (Jerrijn)", 
			"No link provided", 
			"Ideas and inspiration"),
	
	JARICK("EpicC4Build (Jarick)", 
			"No link provided", 
			"Building");
	
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
