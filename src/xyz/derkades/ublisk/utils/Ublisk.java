package xyz.derkades.ublisk.utils;

import static org.bukkit.ChatColor.DARK_GRAY;
import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.GRAY;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;
import static org.bukkit.ChatColor.YELLOW;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import xyz.derkades.ublisk.Main;
import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.Var;
import xyz.derkades.ublisk.database.ServerInfo;
import xyz.derkades.ublisk.utils.Logger.LogLevel;
import xyz.derkades.ublisk.utils.inventory.Item;
import xyz.derkades.ublisk.utils.version_helper.NMS;

public class Ublisk {
	
	/**
	 * Should be set to true if an error occured that will be fixed by restarting. <b>This should never be set to false!</b>
	 */
	public static boolean RESTART_ERROR = false;
	
	public static NMS NMS;
	
	private static Connection DATABASE_CONNECTION;
	
	public static List<UPlayer> getOnlinePlayers(){
		List<UPlayer> list = new ArrayList<UPlayer>();
		Bukkit.getOnlinePlayers().forEach(player -> list.add(new UPlayer(player)));
		return list;
	}
	
	public static Collection<UPlayer> getOfflinePlayers(){
		Collection<UPlayer> players = new HashSet<>();
		for (OfflinePlayer player : Bukkit.getOfflinePlayers()){
			players.add(new UPlayer(player));
		}
		return players;
	}
	
	/**
	 * Execute a command as the console (without the /)
	 * @param cmd The command to be executed
	 */
	public static void sendConsoleCommand(String cmd){
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
	}
	
	public static void openDatabaseConnection() {
		String ip = Var.DATABASE_HOST;
		int port = Var.DATABASE_PORT;
		String user = Var.DATABASE_USER;
		String pass = Var.DATABASE_PASSWORD;
		String db = Var.DATABASE_DB_NAME;
		
		Properties properties = new Properties();
		properties.setProperty("user", user);
		properties.setProperty("password", pass);
		properties.setProperty("useSSL", "false");
		
		try {
			DATABASE_CONNECTION = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + db, properties);
		} catch (SQLException e) {
			Ublisk.exception(e, Ublisk.class);
		}
	}
	
	public static PreparedStatement prepareStatement(String reason, String sql) throws SQLException {
		Connection connection = Ublisk.getDatabaseConnection(reason);
		return connection.prepareStatement(sql);
	}
	
	public static PreparedStatement prepareStatement(String reason, String sql, Object... parameters) throws SQLException {
		PreparedStatement statement = Ublisk.prepareStatement(reason, sql);
		
		int i = 1;
		for (Object parameter : parameters) {
			if (parameter instanceof String) {
				statement.setString(i, (String) parameter);
			} else if (parameter instanceof Integer) {
				statement.setInt(i, (int) parameter);
			} else if (parameter instanceof Double) {
				statement.setDouble(i, (double) parameter);
			} else {
				throw new IllegalArgumentException("Data type not supported");
			}
			
			i++;
		}
		
		return statement;
	}
	
	public static void closeDatabaseConnection() {
		if (DATABASE_CONNECTION != null) {
			try {
				DATABASE_CONNECTION.close();
			} catch (SQLException e) {
				Ublisk.exception(e, Ublisk.class);
			}
		} else {
			Logger.log(LogLevel.WARNING, "Database connection is null?");
		}
	}
	
	public static Connection getDatabaseConnection(String reason) {
		ServerInfo.DATABASE_REQUESTS++;

		Logger.log(LogLevel.DEBUG, "Database", "New connection: " + reason);
		return DATABASE_CONNECTION;
	}
	
	public static void exception(Exception exception, Class<?> source, String message){
		Logger.log(LogLevel.SEVERE, source.getSimpleName(), message);
		exception.printStackTrace();
	}
	
	public static void exception(Exception exception, Class<?> source){
		Logger.log(LogLevel.SEVERE, source.getSimpleName(), exception.getMessage());
		exception.printStackTrace();
	}
	
	public static String getProgressString(float f){
		String string;
		
		if (f > 0.9f){
			string = GREEN + "IIIIIIIII";
		} else if (f > 0.8f){
			string = GREEN + "IIIIIIII" + RED + "I";
		} else if (f > 0.7f){
			string = GREEN + "IIIIIII" + RED + "II";
		} else if (f > 0.6f){
			string = GREEN + "IIIIII" + RED + "III";
		} else if (f > 0.5f){
			string = GREEN + "IIIII" + RED + "IIII";
		} else if (f > 0.4f){
			string = GREEN + "IIII" + RED + "IIIII";
		} else if (f > 0.3f){
			string = GREEN + "III" + RED + "IIIIII";
		} else if (f > 0.2f){
			string = GREEN + "II" + RED + "IIIIIII";
		} else if (f > 0.1f){
			string = GREEN + "I" + RED + "IIIIIIII";
		} else {
			string = RED + "IIIIIIIII";
		}
		
		return DARK_GRAY + " [" + string + DARK_GRAY + "]";
	}
	
	/**
	 * @param name
	 * @return An OfflinePlayer object or null if the player could not be found
	 */
	public static OfflinePlayer getOfflinePlayer(String name){
		for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()){
			if (offlinePlayer.getName().equals(name)){
				return offlinePlayer;
			}
		}
		return null;
	}
	
	/**
	 * Broadcasts a message. This message will not appear in the console.
	 * @param message Message to be sent.
	 */
	public static void broadcastMessage(Message message){
		for (Player player : Bukkit.getOnlinePlayers()){
			player.sendMessage(message.toString());
		}
	}
	
	/**
	 * Broadcasts a message. This message will not appear in the console.
	 * @param message Message to be sent.
	 */
	public static void broadcastMessage(String message){
		for (Player player : Bukkit.getOnlinePlayers()){
			player.sendMessage(message);
		}
	}
	
	public static void broadcastPrefixedMessage(String message){
		broadcastMessage(Ublisk.getPrefix() + message);
	}
	
	public static void broadcastPrefixedMessage(String prefix, String message){
		broadcastMessage(Ublisk.getPrefix(prefix) + message);
	}
	
	public static void broadcastMessage(BaseComponent[]... message){
		for (UPlayer player : Ublisk.getOnlinePlayers()){
			player.sendMessage(message);
		}
	}
	
	public static Server getServer(){
		return Bukkit.getServer();
	}
	
	public static void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, double speed){
		Var.WORLD.spawnParticle(particle, location, count, offsetX, offsetY, offsetZ, speed);
	}
	
	/**
	 * @param particle Must be reddust, mobSpell or mobSpellAmbient
	 * @param location Particle location
	 * @param red 0-255
	 * @param green 0-255
	 * @param blue 0-255
	 * 
	 * @throws UnsupportedOperationException if particle is not one of the three listed above.
	 */
	public static void spawnParticle(Particle particle, Location location, int red, int green, int blue, int count){
		if (particle != Particle.BLOCK_DUST && particle != Particle.SPELL_MOB && particle != Particle.SPELL_MOB_AMBIENT){
			throw new UnsupportedOperationException("Particle must be reddust, mobSpell or mobSpellAmbient");
		}
		
		for (int i = 0; i <= count; i++){
			spawnParticle(particle, location, 0, red / 255.0, green / 255.0, blue / 255.0, 0);
		}
	}
	
	public static void playSound(Location location, Sound sound){
		Var.WORLD.playSound(location, sound, 1.0f, 1.0f);
	}
	
	public static void playSound(Location location, Sound sound, float pitch){
		Var.WORLD.playSound(location, sound, 1.0f, pitch);
	}
	
	/**
	 * Creates a fake explosion with sounds and particles.
	 * @param location The location to spawn the explosion at.
	 * @param damage Damage to be dealt. This is divided by the number of blocks the entity is away from the explosion. E.g. with damage = 10, a zombie standing 3 blocks away will get 10/3 = 3.33 damage.
	 * @param damageRadius See damage.
	 * @param sound If an explosion sound should be played.
	 * @param explosions Particles to summon. See Explosion enum.
	 */
	public static void createFakeExplosion(Location location, UPlayer source, int damage, double damageRadius, boolean sound, Explosion... explosions){
		
		//Spawn particles
		for (Explosion explosion : explosions){
			if (explosion == Explosion.BLAST_SMALL){
				Ublisk.spawnParticle(Particle.EXPLOSION_LARGE, location, 1, 0, 0, 0, 0);
			} else if (explosion == Explosion.BLAST_LARGE){
				Ublisk.spawnParticle(Particle.EXPLOSION_HUGE, location, 1, 0, 0, 0, 0);
			} else if (explosion == Explosion.FIRE){
				Ublisk.spawnParticle(Particle.LAVA, location, 100, 1, 0, 1, 0.5);
			} else if (explosion == Explosion.FLAMES){
				Ublisk.spawnParticle(Particle.FLAME, location, 48, 1, 1, 1, 0.1);
			} else if (explosion == Explosion.SMOKE){
				Ublisk.spawnParticle(Particle.SMOKE_LARGE, location, 15, 0, 0, 0, 0.1);
			} 
		}
		
		//Play sounds
		if (sound){
			Ublisk.playSound(location, Sound.ENTITY_GENERIC_EXPLODE);
		}
		
		//Do damage
		List<Entity> near = location.getWorld().getEntities();
		for (Entity entity : near) {
			if (entity instanceof LivingEntity){
				LivingEntity living = (LivingEntity) entity;
				double distance = entity.getLocation().distance(location);
				if (distance <= damageRadius)
					living.damage(damage / distance, source.getEntity());
			}
		}
	}
	
	public static enum Explosion {
		
		/**
		 * A small explosion. Summons Particle.EXPLOSION_LARGE
		 */
		BLAST_SMALL,
		
		/**
		 * A big explosion. Summons Particle.EXPLOSION_HUGE
		 */
		BLAST_LARGE,
		
		/**
		 * Spawns lava particles (not drip particles, those particles that jump out of lava and leave a smoke trail)
		 */
		FIRE,
		
		/**
		 * Spawns flame particles, those particles torches spawn.
		 */
		FLAMES,
		
		/**
		 * Summons SMOKE_LARGE particles.
		 */
		SMOKE;
		
	}
	
	public static String getPrefix(){
		return getPrefix("Ublisk");
	}
	
	public static String getPrefix(String string){
		return GOLD + string + GRAY + " >> " + YELLOW;
	}
	
	public static BaseComponent[] getPrefixComponents(){
		return TextComponent.fromLegacyText(getPrefix());
	}
	
	public static BaseComponent[] getPrefixComponents(String prefix){
		return TextComponent.fromLegacyText(getPrefix(prefix));
	}
	
	public static boolean isEntityNearby(Location location, boolean ignorePlayers){
		for (Entity entity : Var.WORLD.getNearbyEntities(location, 1, 1, 1)){
			if (entity instanceof Player && ignorePlayers){
				continue;
			}
				
			return true;
		}
		return false;
	}
	
	public static void setGameRule(String rule, String value){
		boolean success = Var.WORLD.setGameRuleValue(rule, value);
		if (!success){
			throw new IllegalArgumentException("Either the gamerule or value specified is wrong.");
		}
	}
	
	/**
	 * Drops item at location. Will fail silently if item or item.getItemStack() is null
	 * @param location
	 * @param item
	 */
	public static void dropItem(Location location, Item item){
		if (item == null || item.getItemStack() == null){
			return;
		}
		
		Var.WORLD.dropItemNaturally(location, item.getItemStack());
	}
	
	/**
	 * Creates a new BossBar object. Does not show the bossbar to players.
	 * 
	 * @param text
	 * @param color
	 * @param style
	 * @return
	 * 
	 * @see #showBossBar(BossBar, long, List)
	 */
	public static BossBar createBossBar(String text, BarColor color, BarStyle style){
		return Bukkit.createBossBar(text, color, style);
	}
	
	/**
	 * Shows a bossbar to all players in the list for a specified amount of time
	 * 
	 * @param bar BossBar
	 * @param timeout Number of ticks after which the bar should disappear
	 * @param players Players to show the bar to
	 * 
	 * @see #createBossBar(String, BarColor, BarStyle)
	 */
	public static void showBossBar(BossBar bar, long timeout, List<UPlayer> players) {
		for (UPlayer player : players) {
			bar.addPlayer(player.bukkit());
		}
		
		new URunnable() {
			public void run() {
				bar.removeAll();
			}
		}.runLater(timeout);
	}
	
	public static void runAsync(Runnable runnable) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), runnable);
	}
	
	public static void runSync(Runnable runnable) {
		Bukkit.getScheduler().runTask(Main.getInstance(), runnable);
	}

}
