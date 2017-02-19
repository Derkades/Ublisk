package com.robinmc.ublisk;

public enum Helper {
	
	ROBINMC("RobinMC (Robin)", 
			"https://goo.gl/Aay9Ry", 
			"Plugin development, bug testing, landscaping, a bit of building, textures, quests, music"),
	
	JARICK("EpicC4Build (Jarick)", 
			"No link provided", 
			"Main builder for Rhocus and Dawn Point"),
	
	SANDERP("SenpaiSander (Sander)", 
			"https://www.youtube.com/channel/UC7gVEC704565MNzCB6OS3Ew", 
			"Building, quests, weapons, textures"),
	
	JERRIJN("Jerrijn (Jerrijn)", 
			"No link provided", 
			"Custom sword textures, ideas and inspiration"),
	
	CHASPYR("Chaspyr (Casper)", 
			"https://twitter.com/ItsChaspyr", 
			"Building, bug testing, textures, complaining that he can't play yet"),
	
	/*GLITCHERDOTBE("GlitcherDOTbe", 
			"http://www.planetminecraft.com/texture_pack/glitchs-3d-addons-pack-19/", 
			"Textures"),*/
	
	ROTTENNUGGET("RottenNugget", 
			"https://www.reddit.com/r/Minecraft/comments/2922fb/3d_models_resource_pack/", 
			"3D models for some blocks and items"),
	
	PARKER("MrParkerl11", 
			"https://goo.gl/WFW9zm", 
			"Built one windmill. Yep, that's it."),
	
	SYDAN("SirSydan (Sydan)", 
			"https://www.youtube.com/channel/UCFl5VN9R5uEszEoLrYgTOoA", 
			"Music"),
	
	XISUMA("xisumavoid", 
			"https://www.youtube.com/user/xisumavoid", 
			"I used some of his modular resourcepacks in the Ublisk pack."),
	
	BREECKO("Breecko (Brecht)", 
			"https://www.youtube.com/channel/UC4Slc9O6dLsia_fWrgN8Q9Q", 
			"A but of building, but mainly being awesome and motivating :D"),
	
	SANDER_GAMING("Sander_Gaming (Sander)", 
			"https://www.youtube.com/channel/UCW-jqEMRdjgimFtarWZGGsg", 
			"Providing me with his great humor while programming"),
	
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
