package xyz.derkades.ublisk.modules;

import java.io.IOException;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import xyz.derkades.ublisk.Var;
import xyz.derkades.ublisk.utils.Logger;
import xyz.derkades.ublisk.utils.Logger.LogLevel;

public class GitHubModule extends UModule {
	
	private static GitHub github;
	
	private static GHRepository ubliskRepository;
	private static GHRepository texturesRepository;
	
	private boolean initialized = false;
	
	@Override
	public void onEnable(){
		if (initialized)
			throw new UnsupportedOperationException("GitHub module is already initialized");
		
		Logger.log(LogLevel.DEBUG, "Initializing GitHub..");
		
		try {
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
	
	public static GHIssue createIssue(String author, String description) throws IOException {
		return ubliskRepository.createIssue("[" + author + "] " + description).body(description + "").label("bot").create();
	}

} 
