package com.robinmc.ublisk.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.enums.Clazz;
import com.robinmc.ublisk.enums.Helper;
import com.robinmc.ublisk.enums.Town;
import com.robinmc.ublisk.enums.Tracker;
import com.robinmc.ublisk.quest.Quest;
import com.robinmc.ublisk.quest.QuestCharacter;
import com.robinmc.ublisk.quest.QuestParticipant;
import com.robinmc.ublisk.utils.exception.GroupNotFoundException;
import com.robinmc.ublisk.utils.exception.LastSenderUnknownException;
import com.robinmc.ublisk.utils.exception.MobNotFoundException;
import com.robinmc.ublisk.utils.exception.NotInGuildException;
import com.robinmc.ublisk.utils.exception.NotSetException;
import com.robinmc.ublisk.utils.exception.PlayerNotFoundException;
import com.robinmc.ublisk.utils.guilds.Guild;
import com.robinmc.ublisk.utils.guilds.Guilds;
import com.robinmc.ublisk.utils.inventory.BetterInventory;
import com.robinmc.ublisk.utils.logging.LogLevel;
import com.robinmc.ublisk.utils.logging.Logger;
import com.robinmc.ublisk.utils.perm.Permission;
import com.robinmc.ublisk.utils.perm.PermissionGroup;
import com.robinmc.ublisk.utils.settings.Setting;
import com.robinmc.ublisk.utils.settings.StaffSetting;
import com.robinmc.ublisk.utils.third_party.IconMenu.OptionClickEvent;
import com.robinmc.ublisk.utils.variable.Message;
import com.robinmc.ublisk.utils.variable.Var;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class UPlayer {
	
	private Player player;
	
	public UPlayer(Player player){
		this.player = player;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void setLifeCrystals(int amount){
		Config.set("life." + getUniqueId(), amount);
	}
	
	public void addLifeCrystals(int amount){
		int old = getLifeCrystals();
		setLifeCrystals(old + amount);
	}
	
	public void removeLifeCrystals(int amount){
		int old = getLifeCrystals();
		setLifeCrystals(old - amount);
	}
	
	public int getLifeCrystals(){
		if (Config.getConfig().isSet("life." + getUniqueId())){
			return Config.getInteger("life." + getUniqueId());
		} else {
			return 5;
		}
	}
	
	public BetterInventory getInventory(){
		return BetterInventory.getInventory(player);
	}
	
	public int getVotingPoints(){
		String path = "voting." + getUniqueId();
		if (Config.getConfig().isSet(path)){
			return Config.getInteger(path);
		} else {
			setVotingPoints(0);
			return 0;
		}
	}
	
	public void setVotingPoints(int i){
		String path = "voting." + getUniqueId();
		Config.set(path, i);
	}
	
	public void addVotingPoints(int i){
		setVotingPoints(getVotingPoints() + i);
	}
	
	public void removeVotingPoints(int i){
		setVotingPoints(getVotingPoints() - i);
	}
	
	public boolean hasVotingPoints(int i){
		return getVotingPoints() >= i;
	}
	
	public PermissionGroup getGroup(){
		try {
			return PermissionGroup.fromString(Config.getString("groups." + getUniqueId()));
		} catch (GroupNotFoundException e) {
			Logger.log(LogLevel.WARNING, "Permissions", "Could not get group of " + player.getName());
			return PermissionGroup.DEFAULT;
		}
	}
	
	public void setGroup(PermissionGroup group){
		Config.set("groups." + getUniqueId(), group.getName().toLowerCase());
	}
	
	public boolean hasPermission(Permission perm){
		return getGroup().hasPermission(perm);
	}
	
	public Location getLocation(){
		return player.getLocation();
	}
	
	public void teleport(Location loc){
		player.teleport(loc);
	}
	
	public void teleport(double x, double y, double z){
		teleport(new Location(Var.WORLD, x, y, z));
	}
	
	public void teleport(double x, double y, double z, int pitch, int yaw){
		teleport(new Location(Var.WORLD, x, y, z, pitch, yaw));
	}
	
	public QuestParticipant getQuestParticipant(Quest quest, QuestCharacter npc){
		return new QuestParticipant(player, quest, npc);
	}
	
	public UUID getUniqueId(){
		return player.getUniqueId();
	}
	
	public void sendMessage(String msg){
		player.sendMessage(msg);
	}
	
	public void sendMessage(TextComponent text){
		player.spigot().sendMessage(text);
	}
	
	public void sendMessage(BaseComponent[] text){
		player.spigot().sendMessage(text);
	}
	
	public void sendMessage(Object o){
		player.sendMessage(o + "");
	}
	
	public void sendSpacer(){
		player.sendMessage(" ");
	}
	
	public void sendSpacers(int spacers){
		for (int i = 0; i <= spacers; i++){
			sendSpacer();
		}
	}
	
	public boolean isSneaking(){
		return player.isSneaking();
	}
	
	public int getLevel(){
		return Exp.getLevel(player);
	}
	
	public int getXP(){
		return Exp.get(player);
	}
	
	public void refreshXP(){
		Exp.refresh(player);
	}
	
	public void addXP(int xp){
		Exp.add(player, xp);
	}
	
	public void setXP(int xp){
		Exp.set(player, xp);
	}
	
	public void giveMobXP(Entity entity) throws MobNotFoundException {
		Exp.giveMobExp(player, entity);
	}
	
	public String getName(){
		return player.getName();
	}
	
	public Town getLastTown(){
		return Town.getLastTown(player);
	}
	
	public void syncTracker(Tracker tracker){
		Tracker.syncWithDatabase(player, tracker);
	}
	
	public boolean addFriend(Player newFriend){
		final List<String> list = Main.getInstance().getConfig().getStringList("friends." + getUniqueId());
		if (list.contains(newFriend.getUniqueId().toString())){
			return false;
		} else {
			list.add(newFriend.getUniqueId().toString());
			Main.getInstance().getConfig().set("friends." + getUniqueId(), list);
			Config.save();
			return true;
		}
	}
	
	public boolean removeFriend(int index){
		final List<String> list = Main.getInstance().getConfig().getStringList("friends." + getUniqueId());
		if (list.size() >= index){
			list.remove(index);
			Main.getInstance().getConfig().set("friends." + getUniqueId(), list);
			Config.save();
			return true;
		} else {
			return false;
		}
	}
	
	public boolean removeFriend(OfflinePlayer friendToRemove){
		final List<String> list = Main.getInstance().getConfig().getStringList("friends." + getUniqueId());
		if (list.contains(friendToRemove.getUniqueId().toString())){			
			list.remove(friendToRemove.getUniqueId().toString());
			Main.getInstance().getConfig().set("friends." + getUniqueId(), list);
			Config.save();
			return true;
		} else {
			return false;
		}
	}
	
	public List<String> getFriends(){
		final List<String> list = Main.getInstance().getConfig().getStringList("friends." + getUniqueId());
		return list;
	}
	
	/**
	 * Bans a player for a given amount of time
	 * @param player
	 * @param time Time in seconds
	 */
	@SuppressWarnings("deprecation")
	public void tempBan(final int time){
		player.setBanned(true);
		Logger.log(LogLevel.WARNING, "Banning", player.getName() + " has been banned for " + time + " seconds.");
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable(){
			public void run(){
				player.setBanned(false);
				Logger.log(LogLevel.INFO, "Banning", player.getName() + " has been unbanned after " + time + " seconds.");
			}
		}, time * 20);
	}
	
	public boolean getSetting(Setting setting) throws NotSetException{
		return setting.get(player);
	}
	
	public void setSetting(Setting setting, boolean bool){
		setting.put(player, bool);
	}
	
	public boolean getStaffSetting(StaffSetting setting) throws NotSetException{
		return setting.get(player);
	}
	
	public void setStaffSetting(StaffSetting setting, boolean bool){
		setting.put(player, bool);
	}
	
	public double getHealth(){
		return player.getHealth();
	}
	
	public GameMode getGameMode(){
		return player.getGameMode();
	}
	
	public void joinGuild(Guild guild){
		guild.addPlayer(this);
	}
	
	public void leaveGuild(Guild guild) throws NotInGuildException {
		Logger.log(LogLevel.DEBUG, "Leave guild");
		guild.removePlayer(this);
	}
	
	public boolean isInGuild(){
		Logger.log(LogLevel.DEBUG, "Is in guild?");
		for (Guild guild : Guilds.getGuilds()){
			Logger.log(LogLevel.DEBUG, "Guilds for loop: " + guild.getName() + ". Contains player? " + guild.hasPlayer(this));
			if (guild.hasPlayer(this)){
				return true;
			}
		}
		return false;
	}
	
	public Guild getGuild() throws NotInGuildException {
		for (Guild guild : Guilds.getGuilds()){
			Logger.log(LogLevel.DEBUG, "Guilds for loop: " + guild.getName() + ". Contains player? " + guild.hasPlayer(this));
			if (guild.hasPlayer(this)){
				return guild;
			}
		}
		throw new NotInGuildException();
	}
	
	public Guild getInvitedGuild(){
		return Guilds.INVITED_GUILD.get(this);
	}
	
	public void inviteToGuild(Guild guild, UPlayer target){
		Logger.log(LogLevel.DEBUG, "Invite to guild: " + guild.getName() + " player " + target.getName());
		Guilds.INVITED_GUILD.put(target, guild);
		target.sendMessage(Message.Complicated.Guilds.inviteToGuild(guild, this));
	}
	
	public void setLastSender(UPlayer player){
		HashMaps.lastMessageSender.put(this.player, player.getPlayer());
	}
	
	public UPlayer getLastSender() throws LastSenderUnknownException{
		if (!HashMaps.lastMessageSender.containsKey(player)){
			throw new LastSenderUnknownException();
		}
		
		return UPlayer.get(HashMaps.lastMessageSender.get(player));
	}
	
	public void sendPrivateMessage(UPlayer sender, String msg){
		player.sendMessage(Message.prefix("Private Message") + sender.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.RESET + ChatColor.BOLD + msg);
		sender.sendMessage(Message.prefix("Private Message") + ChatColor.AQUA + " -> " + player.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.RESET + ChatColor.BOLD + msg);
	}
	
	public void sendMessage(Message message){
		player.sendMessage(message.get());
	}
	
	public boolean isInBuilderMode(){
		return Helper.builderModeEnabled(player);
	}
	
	public void setBuilderModeEnabled(boolean bool){
		if (bool){
			Helper.enableBuilderMode(player);
		} else {
			Helper.disableBuilderMode(player);
		}
	}
	
	public void toggleBuilderMode(){
		setBuilderModeEnabled(!isInBuilderMode()); //If player is not in builder mode put player in builder mode and visa versa 
	}
	
	public void refreshLastSeenDate(){
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		Date dateobj = new Date();
		String date = df.format(dateobj);
		Config.set("last-played." + player.getUniqueId(), date);
	}
	
	public String getLastSeenDate(){
		if (Config.getConfig().isSet("last-played." + player.getUniqueId())){
			return Config.getString("last-played." + player.getUniqueId());
		} else {
			return "Never";
		}
	}
	
	public void tracker(Tracker tracker){
		tracker.add(player);
	}
	
	public Clazz getClazz(){
		return Clazz.getClass(player);
	}
	
	public static UPlayer[] getOnlinePlayers(){
		List<UPlayer> list = new ArrayList<UPlayer>();
		for (Player player : Bukkit.getOnlinePlayers()){
			list.add(new UPlayer(player));
		}
		return list.toArray(new UPlayer[0]);
	}
	
	public static UPlayer get(Player player){ return new UPlayer(player); }
	
	public static UPlayer get(QuestParticipant qp){ return get(qp.getBukkitPlayer()); }
	
	public static UPlayer get(CommandSender sender){ return get((Player) sender); }
	
	public static UPlayer get(String name) throws PlayerNotFoundException { return get(UUIDUtils.getPlayerFromName(name)); }
	
	public static UPlayer get(OptionClickEvent event){ return get(event.getPlayer()); }
	
	public static UPlayer get(PlayerCommandPreprocessEvent event){ return get(event.getPlayer()); }
	
	public static UPlayer get(AsyncPlayerChatEvent event){ return get(event.getPlayer()); }
	
	public static UPlayer get(PlayerInteractEntityEvent event){ return get(event.getPlayer()); }
	
	public static UPlayer get(PlayerQuitEvent event){ return get(event.getPlayer()); }
	
	public static UPlayer get(PlayerJoinEvent event){ return get(event.getPlayer()); }

}