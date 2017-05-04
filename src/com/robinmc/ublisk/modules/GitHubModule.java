package com.robinmc.ublisk.modules;

import java.io.IOException;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.utils.Logger;
import com.robinmc.ublisk.utils.Logger.LogLevel;

public class GitHubModule extends UModule {
	
	private static final String PREFIX = "[BOT]";
	
	private static GitHub github;
	
	private static GHRepository ubliskRepository;
	private static GHRepository texturesRepository;
	
	private boolean initialized = false;
	
	@Override
	public void onEnable(Main plugin){
		if (initialized)
			throw new UnsupportedOperationException("GitHub module is already initialized");
		
		Logger.log(LogLevel.DEBUG, "Initializing GitHub..");
		
		try {
			//github = GitHub.connect(Var.GITHUB_LOGIN, Var.GITHUB_ACCESS_TOKEN);
			github = GitHub.connectUsingOAuth(Var.GITHUB_ACCESS_TOKEN);
		} catch (IOException e) {
			Logger.log(LogLevel.SEVERE, "GitHub", "Failed to initialize GitHub");
			e.printStackTrace();
			return;
		}
		
		Logger.log(LogLevel.INFO, "Initialized GitHub.");
		
		Logger.log(LogLevel.DEBUG, "Initializing repository..");
		
		try {
			ubliskRepository = github.getRepository("Derkades/Ublisk");
		} catch (IOException e) {
			Logger.log(LogLevel.SEVERE, "GitHub", "Failed to initialize repository");
			e.printStackTrace();
		}
		
		Logger.log(LogLevel.INFO, "Initialized repository.");
		
		Logger.log(LogLevel.DEBUG, "Initializing textures repository..");
		
		try {
			texturesRepository = github.getRepository("Derkades/UbliskTextures");
		} catch (IOException e) {
			Logger.log(LogLevel.SEVERE, "GitHub", "Failed to initialize repository");
			e.printStackTrace();
		}
		
		Logger.log(LogLevel.INFO, "Initialized textures repository.");
		
		initialized = true;		
	}
	
	public static GHRepository getUbliskRepository(){
		return ubliskRepository;
	}
	
	public static GHRepository getUbliskTexturesRepository(){
		return texturesRepository;
	}
	
	public static GHIssue createIssue(String description) throws IOException {
		return ubliskRepository.createIssue(PREFIX + " " + description).body(description + "").label("bot").create();
	}

} 
