package com.robinmc.ublisk.utils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Player.Spigot;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.robinmc.ublisk.Clazz;
import com.robinmc.ublisk.HashMaps;
import com.robinmc.ublisk.Main;
import com.robinmc.ublisk.Message;
import com.robinmc.ublisk.Town;
import com.robinmc.ublisk.Var;
import com.robinmc.ublisk.VoteRestart;
import com.robinmc.ublisk.abilities.Ability;
import com.robinmc.ublisk.money.Money;
import com.robinmc.ublisk.quest.NPC;
import com.robinmc.ublisk.quest.Quest;
import com.robinmc.ublisk.quest.QuestParticipant;
import com.robinmc.ublisk.quest.npcmenu.NPCMenu;
import com.robinmc.ublisk.task.AfkTimer;
import com.robinmc.ublisk.utils.IconMenu.OptionClickEvent;
import com.robinmc.ublisk.utils.Logger.LogLevel;
import com.robinmc.ublisk.utils.exception.GroupNotFoundException;
import com.robinmc.ublisk.utils.exception.LastSenderUnknownException;
import com.robinmc.ublisk.utils.exception.MobNotFoundException;
import com.robinmc.ublisk.utils.exception.NotEnoughManaException;
import com.robinmc.ublisk.utils.exception.NotInATownException;
import com.robinmc.ublisk.utils.exception.NotInGuildException;
import com.robinmc.ublisk.utils.exception.PlayerNotFoundException;
import com.robinmc.ublisk.utils.guilds.Guild;
import com.robinmc.ublisk.utils.guilds.Guilds;
import com.robinmc.ublisk.utils.inventory.InvUtils;
import com.robinmc.ublisk.utils.perm.Permission;
import com.robinmc.ublisk.utils.perm.PermissionGroup;
import com.robinmc.ublisk.utils.settings.Setting;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_11_R1.ChatComponentText;
import net.minecraft.server.v1_11_R1.Packet;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;

public class UPlayer {

	private Player player;

	public UPlayer(Player player) {
		this.player = player;
	}

	public UPlayer(UUID uuid) {
		this.player = Bukkit.getPlayer(uuid);
	}

	public UPlayer(String name) throws PlayerNotFoundException {
		this.player = UUIDUtils.getPlayerFromName(name);
	}

	public UPlayer(CommandSender sender) {
		if (sender instanceof Player) {
			this.player = (Player) sender;
		} else {
			throw new IllegalArgumentException("CommandSender is not a player");
		}
	}

	public UPlayer(PlayerEvent event) {
		this.player = event.getPlayer();
	}

	public UPlayer(EntityEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Player) {
			this.player = (Player) entity;
		} else {
			throw new IllegalArgumentException("Entity is not a player");
		}
	}
	
	public UPlayer(Entity entity){
		if (entity instanceof Player) {
			this.player = (Player) entity;
		} else {
			throw new IllegalArgumentException("Entity is not a player");
		}
	}
	
	public UPlayer(HumanEntity human){
		if (human instanceof Player){
			this.player = (Player) human;
		} else {
			throw new IllegalArgumentException("Human is not a player");
		}
	}
	
	public UPlayer(OfflinePlayer offline){
		if (offline.isOnline()){
			this.player = offline.getPlayer();
		} else {
			throw new IllegalArgumentException("Player " + player.getName() + " is not online.");
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setLifeCrystals(int amount) {
		DataFile.LIFE_CRYSTAL.set("life." + getUniqueId(), amount);
	}

	public int getLifeCrystals() {
		if (DataFile.LIFE_CRYSTAL.isSet("life." + getUniqueId())) {
			return DataFile.LIFE_CRYSTAL.getInteger("life." + getUniqueId());
		} else {
			return 5;
		}
	}

	public PlayerInventory getInventory() {
		return player.getInventory();
	}

	public int getVotingPoints() {
		String path = "voting." + getUniqueId();
		if (DataFile.VOTING.isSet(path)) {
			return DataFile.VOTING.getInteger(path);
		} else {
			setVotingPoints(0);
			return 0;
		}
	}

	public void setVotingPoints(int i) {
		String path = "voting." + getUniqueId();
		DataFile.VOTING.set(path, i);
	}

	public boolean hasVotingPoints(int i) {
		return getVotingPoints() >= i;
	}

	public PermissionGroup getGroup() {
		try {
			return PermissionGroup.fromString(DataFile.PERMISSIONS.getString("groups." + this.getUniqueId()));
		} catch (GroupNotFoundException e) {
			Logger.log(LogLevel.WARNING, "Permissions", "Could not get group of " + player.getName());
			return PermissionGroup.DEFAULT;
		}
	}

	public void setGroup(PermissionGroup group) {
		DataFile.PERMISSIONS.set("groups." + getUniqueId(), group.getName().toLowerCase());
	}

	public boolean hasPermission(Permission perm) {
		return getGroup().hasPermission(perm);
	}

	public Location getLocation() {
		return player.getLocation();
	}

	public void teleport(Location loc) {
		player.teleport(loc);
	}

	public void teleport(double x, double y, double z) {
		teleport(new Location(Var.WORLD, x, y, z));
	}

	public void teleport(double x, double y, double z, int pitch, int yaw) {
		teleport(new Location(Var.WORLD, x, y, z, pitch, yaw));
	}

	public QuestParticipant getQuestParticipant(Quest quest, NPC npc) {
		return new QuestParticipant(player, quest, npc);
	}

	public UUID getUniqueId() {
		return player.getUniqueId();
	}

	public void sendMessage(String msg) {
		player.sendMessage(msg);
	}

	public void sendMessage(TextComponent text) {
		player.spigot().sendMessage(text);
	}

	public void sendMessage(BaseComponent[] text) {
		player.spigot().sendMessage(text);
	}

	public void sendMessage(Object o) {
		player.sendMessage(o + "");
	}

	public void sendSpacer() {
		player.sendMessage(" ");
	}

	public void sendSpacers(int spacers) {
		for (int i = 0; i <= spacers; i++) {
			sendSpacer();
		}
	}

	public boolean isSneaking() {
		return player.isSneaking();
	}

	public int getLevel() {
		return Exp.getLevel(player);
	}

	private void setXP(int xp) {
		Exp.set(player, xp);
	}

	public int getXP() {
		return Exp.get(player);
	}

	public void refreshXP() {
		Exp.refresh(player);
	}

	public void addXP(int xp) {
		setXP(getXP() + xp);
	}

	public void giveMobXP(Entity entity) throws MobNotFoundException {
		Exp.giveMobExp(this, entity);
	}

	public String getName() {
		return player.getName();
	}

	public boolean addFriend(UPlayer newFriend) {
		final List<String> list = DataFile.FRIENDS.getStringList("friends." + getUniqueId());
		if (list.contains(newFriend.toString())) {
			return false;
		} else {
			list.add(newFriend.toString());
			DataFile.FRIENDS.set("friends." + getUniqueId(), list);
			return true;
		}
	}

	public boolean removeFriend(int index) {
		final List<String> list = DataFile.FRIENDS.getStringList("friends." + getUniqueId());
		if (list.size() >= index) {
			list.remove(index);
			DataFile.FRIENDS.set("friends." + getUniqueId(), list);
			return true;
		} else {
			return false;
		}
	}

	public boolean removeFriend(OfflinePlayer friendToRemove) {
		final List<String> list = DataFile.FRIENDS.getStringList("friends." + getUniqueId());
		if (list.contains(friendToRemove.toString())) {
			list.remove(friendToRemove.toString());
			DataFile.FRIENDS.set("friends." + getUniqueId(), list);
			return true;
		} else {
			return false;
		}
	}

	public List<OfflinePlayer> getFriends() {
		final List<String> strings = DataFile.FRIENDS.getStringList("friends." + getUniqueId());
		final List<OfflinePlayer> players = new ArrayList<OfflinePlayer>();
		for (String string : strings)
			players.add(Ublisk.getPlayerFromString(string));
		return players;
	}

	/**
	 * Bans a player for a given amount of time
	 * 
	 * @param player
	 * @param time
	 *            Time in seconds
	 */
	@Deprecated
	public void tempBan(final int time) {
		player.setBanned(true);
		Logger.log(LogLevel.WARNING, "Banning", player.getName() + " has been banned for " + time + " seconds.");
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			public void run() {
				player.setBanned(false);
				Logger.log(LogLevel.INFO, "Banning",
						player.getName() + " has been unbanned after " + time + " seconds.");
			}
		}, time * 20);
	}

	public boolean getSetting(Setting setting) {
		return setting.get(player);
	}

	public void setSetting(Setting setting, boolean bool) {
		setting.put(player, bool);
	}

	public double getHealth() {
		return player.getHealth();
	}

	public GameMode getGameMode() {
		return player.getGameMode();
	}

	public void setGameMode(GameMode gamemode) {
		player.setGameMode(gamemode);
	}

	public void joinGuild(Guild guild) {
		guild.addPlayer(this);
	}

	public void leaveGuild(Guild guild) throws NotInGuildException {
		Logger.log(LogLevel.DEBUG, "Leave guild");
		guild.removePlayer(this);
	}

	public boolean isInGuild() {
		for (Guild guild : Guilds.getGuilds()) {
			if (guild.hasPlayer(this)) {
				return true;
			}
		}
		return false;
	}

	public Guild getGuild() throws NotInGuildException {
		for (Guild guild : Guilds.getGuilds()) {
			if (guild.hasPlayer(this)) {
				return guild;
			}
		}
		throw new NotInGuildException();
	}

	public Guild getInvitedGuild() {
		return Guilds.INVITED_GUILD.get(this);
	}

	public void inviteToGuild(Guild guild, UPlayer target) {
		Logger.log(LogLevel.DEBUG, "Invite to guild: " + guild.getName() + " player " + target.getName());
		Guilds.INVITED_GUILD.put(target, guild);
		target.sendMessage(Message.Complicated.Guilds.inviteToGuild(guild, this));
	}

	public void setLastSender(UPlayer player) {
		HashMaps.LAST_MESSAGE_SENDER.put(this.player, player.getPlayer());
	}

	public UPlayer getLastSender() throws LastSenderUnknownException {
		if (!HashMaps.LAST_MESSAGE_SENDER.containsKey(player)) {
			throw new LastSenderUnknownException();
		}

		return UPlayer.get(HashMaps.LAST_MESSAGE_SENDER.get(player));
	}

	public void sendPrivateMessage(UPlayer sender, String msg) {
		player.sendMessage(Message.prefix("Private Message") + sender.getName() + ChatColor.DARK_GRAY + ": "
				+ ChatColor.RESET + ChatColor.BOLD + msg);
		sender.sendMessage(Message.prefix("Private Message") + ChatColor.AQUA + " -> " + player.getName()
				+ ChatColor.DARK_GRAY + ": " + ChatColor.RESET + ChatColor.BOLD + msg);
	}

	public void sendMessage(Message message) {
		player.sendMessage(message.toString());
	}

	public boolean isInBuilderMode() {
		// Check if an inventory file exists, because the item is deleted when a
		// player goes out of builder mode.
		return new File(InvUtils.path, player.getName() + ".yml").exists();
	}

	public void setBuilderModeEnabled(boolean bool) {
		if (bool) { // Enable builder mode
			this.saveInventoryToFile(Main.getInstance().getDataFolder() + "\\inv"); // Save
																					// inventory
																					// to
																					// file
			this.clearInventory();
			this.setGameMode(GameMode.CREATIVE);
			this.sendMessage(Message.BUILDER_MODE_ACTIVATED);
		} else { // Disable builder mode
			this.fillInventoryFromFile(Main.getInstance().getDataFolder() + "\\inv");
			this.setGameMode(GameMode.ADVENTURE);
			this.sendMessage(Message.BUILDER_MODE_DEACTIVATED);
		}
	}

	public void toggleBuilderMode() {
		setBuilderModeEnabled(!isInBuilderMode()); // If player is not in
													// builder mode put player
													// in builder mode and visa
													// versa
	}

	public void refreshLastSeenDate() {
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		Date dateobj = new Date();
		String date = df.format(dateobj);
		DataFile.LAST_PLAYED.set("last-played." + player.getUniqueId(), date);
	}

	public String getLastSeenDate() {
		if (DataFile.LAST_PLAYED.isSet("last-played." + player.getUniqueId())) {
			return DataFile.LAST_PLAYED.getString("last-played." + player.getUniqueId());
		} else {
			return "Never";
		}
	}

	public void tracker(Map<UUID, Integer> map) {
		map.put(this.getUniqueId(), map.get(this.getUniqueId()) + 1);
	}

	public Clazz getClazz() {
		return Clazz.getClass(player);
	}

	public void setMoney(int amount) {
		Money.set(player, amount);
	}

	public int getMoney() {
		return Money.get(player);
	}

	public boolean hasMoney(int amount) {
		return Money.get(player) >= amount;
	}

	public void sendPacket(@SuppressWarnings("rawtypes") Packet packet) {
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	public void playNotMovingParticle(Particle particle, double x, double y, double z) {
		player.spawnParticle(particle, x, y, z, 0, 0, 0, 0, 1);
	}

	public boolean hasVotedForRestart() {
		return VoteRestart.hasVotedForRestart(this);
	}

	public void voteRestart() {
		VoteRestart.voteForRestart(this);
	}

	public void openNpcMenu(NPCMenu menu) {
		menu.open(this);
	}

	/**
	 * @return Mana, value between 0 and 20
	 */
	public int getMana() {
		return player.getFoodLevel();
	}

	/**
	 * @param mana
	 *            An integer, 0-20
	 */
	public void setMana(int mana) {
		player.setFoodLevel(mana);
	}

	public void removeMana(int mana) throws NotEnoughManaException {
		if (getMana() - mana < 0)
			throw new NotEnoughManaException();

		setMana(getMana() - mana);
	}

	public void setAfk(boolean isAfk) {
		HashMaps.AFK.put(this.getUniqueId(), isAfk);
		if (isAfk) {
			Bukkit.broadcastMessage(Message.Complicated.Commands.nowAfk(getName()));
		} else {
			Bukkit.broadcastMessage(Message.Complicated.Commands.noLongerAfk(getName()));
		}
	}

	public boolean isAfk() {
		return HashMaps.AFK.get(this.getUniqueId());
	}

	public void resetAfkTimer() {
		AfkTimer.TIMER.put(this.getUniqueId(), 0);
	}

	/**
	 * Please avoid using this, unless you are sure that you need this instead
	 * of UPlayer#getTown()
	 * 
	 * @throws NotInATownException
	 *             If the player is not in a town
	 */
	public Town getCurrentTown() throws NotInATownException {
		for (Town town : Town.values()) {
			Location loc = player.getLocation();
			if (loc.getX() < town.lessX() && loc.getX() > town.moreX() && loc.getZ() < town.lessZ()
					&& loc.getZ() > town.moreZ()) {
				return town;
			}
		}
		throw new NotInATownException();
	}

	/**
	 * getLastTown()
	 */
	public Town getTown() {
		String s = DataFile.TOWN.getString("last-town." + player.getUniqueId());

		if (s == null)
			return Town.INTRODUCTION;

		return Town.fromString(s);
	}

	public void setLastTown(Town town) {
		DataFile.TOWN.set("last-town." + player.getUniqueId(), town.getName());
	}

	public void sendTitle(String title, String subtitle) {
		((CraftPlayer) player).sendTitle(title, subtitle);
	}

	public void sendTitle(String title) {
		((CraftPlayer) player).sendTitle(title, "");
	}

	public void sendSubTitle(String subtitle) {
		((CraftPlayer) player).sendTitle("", subtitle);
	}

	public boolean isDead() {
		return player.isDead();
	}

	public void setHealth(double health) {
		player.setHealth(health);
	}

	public void setMaxHealth(double maxHealth) {
		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
	}

	public double getMaxHealth() {
		return player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
	}

	public int getCorrectMaxHealth() {
		if (!Var.LEVEL_HEALTH.containsKey(this.getLevel())) {
			return 1;
		} else {
			return Var.LEVEL_HEALTH.get(this.getLevel());
		}
	}

	public Spigot spigot() {
		return player.spigot();
	}

	public void setResourcePack(String pack) {
		player.setResourcePack(pack);
	}

	public void sendActionBarMessage(String message) {
		PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(message), (byte) 2);
		this.sendPacket(packet);
	}

	public void setAttribute(Attribute attribute, double d) {
		player.getAttribute(attribute).setBaseValue(d);
	}

	public double getAttribute(Attribute attribute) {
		return player.getAttribute(attribute).getBaseValue();
	}

	public void playSound(Sound sound, float volume, float pitch) {
		player.playSound(player.getLocation(), sound, volume, pitch);
	}

	public void playSound(Sound sound, float pitch) {
		this.playSound(sound, 1.0f, pitch);
	}

	public void playSound(Sound sound) {
		this.playSound(sound, 1.0f);
	}

	public void setCollidable(boolean bool) {
		((CraftPlayer) player).setCollidable(bool);
	}

	public void saveInventoryToFile(String path) {
		InvUtils.saveIntentory(path, player);
	}

	public void fillInventoryFromFile(String path) {
		this.clearInventory();
		InvUtils.restoreInventory(path, player);
	}

	public void openEnderchest() {
		player.openInventory(player.getEnderChest());
	}

	/**
	 * Clears inventory and armor slots.
	 */
	public void clearInventory() {
		PlayerInventory inv = player.getInventory();
		for (ItemStack item : inv.getContents())
			inv.remove(item);
		for (ItemStack item : inv.getArmorContents())
			inv.remove(item);
	}

	public boolean inventoryContains(ItemStack... items) {
		boolean hasItems = true;
		for (ItemStack item : items) {
			if (!player.getInventory().containsAtLeast(item, item.getAmount())) {
				hasItems = false;
			}
		}
		return hasItems;
	}
	
	/**
	 * Checks if the player has enough mana and if their level is high enough. If both or one of these conditions is not true, it will send message(s).
	 * @param ability
	 */
	public void doAbility(Ability ability){
		if (ability.getMinimumLevel() > player.getLevel()){
			this.sendMessage(Message.ABILITY_NOT_ENOUGH_LEVEL);
			return;
		}
		
		try {
			this.removeMana(ability.getMana());
		} catch (NotEnoughManaException e) {
			this.sendMessage(Message.ABILITY_NOT_ENOUGH_MANA);
			return;
		}
		
		ability.run(this);
	}

	@Override
	public String toString() {
		return player.getUniqueId().toString();
	}

	@Deprecated
	public static UPlayer fromUUID(UUID uuid) {
		return new UPlayer(Bukkit.getPlayer(uuid));
	}

	@Deprecated
	public static UPlayer get(Player player) {
		return new UPlayer(player);
	}

	@Deprecated
	public static UPlayer get(CommandSender sender) {
		return get((Player) sender);
	}

	@Deprecated
	public static UPlayer get(String name) throws PlayerNotFoundException {
		return get(UUIDUtils.getPlayerFromName(name));
	}

	@Deprecated
	public static UPlayer get(OptionClickEvent event) {
		return get(event.getPlayer());
	}

	@Deprecated
	public static UPlayer get(InventoryClickEvent event) {
		return get(event.getWhoClicked());
	}

	@Deprecated
	public static UPlayer get(PlayerEvent event) {
		return get(event.getPlayer());
	}

	@Deprecated
	public static UPlayer get(EntityEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Player) {
			return UPlayer.get(event.getEntity());
		} else {
			return null;
		}
	}

}