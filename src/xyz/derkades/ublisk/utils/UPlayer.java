package xyz.derkades.ublisk.utils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.WeatherType;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Player.Spigot;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import xyz.derkades.ublisk.DataFile;
import xyz.derkades.ublisk.HashMaps;
import xyz.derkades.ublisk.Main;
import xyz.derkades.ublisk.Message;
import xyz.derkades.ublisk.Town;
import xyz.derkades.ublisk.Var;
import xyz.derkades.ublisk.database.PlayerInfo;
import xyz.derkades.ublisk.modules.AFK;
import xyz.derkades.ublisk.modules.CustomHealth;
import xyz.derkades.ublisk.modules.CustomXP;
import xyz.derkades.ublisk.modules.FriendsBossBar;
import xyz.derkades.ublisk.modules.PlayerFreeze;
import xyz.derkades.ublisk.modules.VoteRestart;
import xyz.derkades.ublisk.money.Money;
import xyz.derkades.ublisk.permission.Permission;
import xyz.derkades.ublisk.permission.PermissionGroup;
import xyz.derkades.ublisk.quest.NPC;
import xyz.derkades.ublisk.quest.Quest;
import xyz.derkades.ublisk.quest.QuestParticipant;
import xyz.derkades.ublisk.utils.Logger.LogLevel;
import xyz.derkades.ublisk.utils.exception.LastSenderUnknownException;
import xyz.derkades.ublisk.utils.exception.PlayerNotFoundException;
import xyz.derkades.ublisk.utils.inventory.InvUtils;
import xyz.derkades.ublisk.utils.inventory.UInventory;
import xyz.derkades.ublisk.utils.settings.Setting;
import xyz.derkades.ublisk.weapons.abilities.Ability;

public class UPlayer {

	final private Player player;
	final private OfflinePlayer offline;

	public UPlayer(Player player) {
		if (player == null)
			throw new IllegalArgumentException("Player must not be null");
		
		this.player = player;
		this.offline = player;
	}

	public UPlayer(UUID uuid) {
		if (uuid == null)
			throw new IllegalArgumentException("UUID must not be null");
		this.player = Bukkit.getPlayer(uuid);
		this.offline = this.player;
	}

	public UPlayer(String name) throws PlayerNotFoundException {
		if (name == null)
			throw new IllegalArgumentException("Name must not be null");
		
		Player player = Bukkit.getPlayer(name);
		
		if (player == null)
			throw new PlayerNotFoundException();
		
		this.player = player;
		this.offline = this.player;
	}

	public UPlayer(CommandSender sender) {
		if (sender instanceof Player) {
			this.player = (Player) sender;
			this.offline = this.player;
		} else {
			throw new IllegalArgumentException("CommandSender is not a player");
		}
	}

	public UPlayer(PlayerEvent event) {
		if (event == null)
			throw new IllegalArgumentException("Event must not be null");
		this.player = event.getPlayer();
		this.offline = this.player;
	}

	public UPlayer(EntityEvent event) {
		if (event == null)
			throw new IllegalArgumentException("Event must not be null");
		
		Entity entity = event.getEntity();
		if (entity instanceof Player) {
			this.player = (Player) entity;
			this.offline = this.player;
		} else {
			throw new IllegalArgumentException("Entity is not a player");
		}
	}

	public UPlayer(Entity entity) {
		if (entity == null)
			throw new IllegalArgumentException("Entity must not be null");
		
		if (entity instanceof Player) {
			this.player = (Player) entity;
			this.offline = this.player;
		} else {
			throw new IllegalArgumentException("Entity is not a player");
		}
	}

	public UPlayer(HumanEntity human) {
		if (human == null)
			throw new IllegalArgumentException("HumanEntity must not be null");
		
		if (human instanceof Player) {
			this.player = (Player) human;
			this.offline = this.player;
		} else {
			throw new IllegalArgumentException("Human is not a player");
		}
	}

	/**
	 * Warning: exceptions will be thrown if you execute any methods where the player requires to be online
	 * @param offline
	 */
	public UPlayer(OfflinePlayer offline) {
		if (offline == null)
			throw new IllegalArgumentException("OfflinePlayer must not be null");
		
		if (offline.isOnline()) {
			this.player = offline.getPlayer();
			this.offline = this.player;
		} else {
			this.offline = offline;
			this.player = null;
		}
	}

	public Player getPlayer() {
		return player;
	}
	
	public OfflinePlayer getOfflinePlayer() {
		return offline;
	}

	public void setLifeCrystals(int amount) {
		DataFile.LIFE_CRYSTAL.getConfig().set("life." + getUniqueId(), amount);
	}

	public int getLifeCrystals() {
		if (DataFile.LIFE_CRYSTAL.getConfig().isSet("life." + getUniqueId())) {
			return DataFile.LIFE_CRYSTAL.getConfig().getInt("life." + getUniqueId());
		} else {
			return 5;
		}
	}

	public UInventory getInventory() {
		return new UInventory(player.getInventory());
	}

	public int getVotingPoints() {
		String path = "voting." + getUniqueId();
		if (DataFile.VOTING.getConfig().isSet(path)) {
			return DataFile.VOTING.getConfig().getInt(path);
		} else {
			setVotingPoints(0);
			return 0;
		}
	}

	public void setVotingPoints(int i) {
		String path = "voting." + getUniqueId();
		DataFile.VOTING.getConfig().set(path, i);
	}

	public boolean hasVotingPoints(int i) {
		return getVotingPoints() >= i;
	}

	public PermissionGroup getGroup() {
		PermissionGroup group = PermissionGroup.fromString(DataFile.PERMISSIONS.getConfig().getString("groups." + this.getUniqueId()));
		if (group == null){
			return PermissionGroup.DEFAULT;
		} else {
			return group;
		}
	}

	public void setGroup(PermissionGroup group) {
		DataFile.PERMISSIONS.getConfig().set("groups." + getUniqueId(), group.getName().toLowerCase());
	}

	public boolean hasPermission(Permission permission) {
		return this.getGroup().getPermissions().contains(permission);
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
		return offline.getUniqueId();
	}

	public void sendMessage(String msg) {
		player.sendMessage(msg);
	}

	public void sendMessage(TextComponent text) {
		player.spigot().sendMessage(text);
	}
	
	public void sendMessage(Message message) {
		player.sendMessage(message.toString());
	}

	public void sendMessage(BaseComponent[] text) {
		player.spigot().sendMessage(text);
	}
	
	public void sendMessage(BaseComponent[]... text){
		//this.sendMessage(ArrayUtils.addAll(text));
		//for (BaseComponent[] components : text){
		//	this.sendMessage(components);
		//}
		BaseComponent[] newArray = new TextComponent[]{};
		for (BaseComponent[] componentArray : text){
			newArray = ArrayUtils.addAll(newArray, componentArray);
		}
		
		this.sendMessage(newArray);
	}

	public void sendMessage(Object o) {
		player.sendMessage(o + "");
	}

	public void sendPrefixedMessage(String message){
		player.sendMessage(Ublisk.getPrefix() + message);
	}
	
	public void sendPrefixedMessage(String prefix, String message){
		player.sendMessage(Ublisk.getPrefix(prefix) + message);
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
		return CustomXP.getLevel(player);
	}

	public void setXP(int xp) {
		CustomXP.setXP(player, xp);
		CustomXP.updateXPBar(player);
	}

	public int getXP() {
		return CustomXP.getXP(player);
	}
	
	public void updateXPBar(){
		CustomXP.updateXPBar(player);
	}

	public void addXP(int xp) {
		setXP(getXP() + xp);
		CustomXP.updateXPBar(player);
	}

	public String getName() {
		return offline.getName();
	}

	public void addFriend(OfflinePlayer newFriend) {
		final List<String> list = DataFile.FRIENDS.getConfig().getStringList("friends." + this.getUniqueId());
		
		if (list.contains(newFriend.getUniqueId())){
			throw new UnsupportedOperationException("Friend is already in friends list");
		}
		
		list.add(newFriend.getUniqueId().toString());
		
		DataFile.FRIENDS.getConfig().set("friends." + this.getUniqueId(), list);
		
		FriendsBossBar.resetBars(this);
	}

	public void removeFriend(int index) {
		final List<String> friendsUUIDList = DataFile.FRIENDS.getConfig().getStringList("friends." + this.getUniqueId());
		
		if (index > friendsUUIDList.size()){
			throw new IllegalArgumentException("Index can't be more than list size");
		}
		
		friendsUUIDList.remove(index);
		
		DataFile.FRIENDS.getConfig().set("friends." + this.getUniqueId(), friendsUUIDList);
		
		FriendsBossBar.resetBars(this);
	}

	public void removeFriend(OfflinePlayer friendToRemove) {
		final List<String> list = DataFile.FRIENDS.getConfig().getStringList("friends." + this.getUniqueId());
		
		if (!list.contains(friendToRemove.getUniqueId().toString())){
			throw new IllegalArgumentException(friendToRemove.getName() + " is not " + this.getName() + "'s friend");
		}
		
		list.remove(friendToRemove.getUniqueId().toString());
		
		DataFile.FRIENDS.getConfig().set("friends." + this.getUniqueId(), list);
		
		FriendsBossBar.resetBars(this);
	}

	public List<OfflinePlayer> getFriends() {
		final List<String> uuidStrings = DataFile.FRIENDS.getConfig().getStringList("friends." + getUniqueId());
		final List<OfflinePlayer> players = new ArrayList<OfflinePlayer>();
		for (String uuidString : uuidStrings){
			UUID uuid = UUID.fromString(uuidString);
			players.add(Bukkit.getOfflinePlayer(uuid));
		}
		return players;
	}
	
	public List<UPlayer> getOnlineFriends(){
		List<UPlayer> list = new ArrayList<>();
		for (OfflinePlayer offlineFriend : this.getFriends()){
			if (offlineFriend.isOnline()){
				list.add(new UPlayer(offlineFriend));
			}
		}
		return list;
	}
	
	public boolean isFriend(OfflinePlayer offlinePlayer){
		for (OfflinePlayer friend : this.getFriends()){
			if (friend.getName().equals(offlinePlayer.getName())){
				return true;
			}
		}
		return false;
	}

	public boolean getSetting(Setting setting) {
		return setting.get(player);
	}

	public void setSetting(Setting setting, boolean bool) {
		setting.put(player, bool);
	}

	public GameMode getGameMode() {
		return player.getGameMode();
	}

	public void setGameMode(GameMode gamemode) {
		player.setGameMode(gamemode);
	}

	public void setLastSender(UPlayer player) {
		HashMaps.LAST_MESSAGE_SENDER.put(this.player, player.getPlayer());
	}

	public UPlayer getLastSender() throws LastSenderUnknownException {
		if (!HashMaps.LAST_MESSAGE_SENDER.containsKey(player)) {
			throw new LastSenderUnknownException();
		}

		return new UPlayer(HashMaps.LAST_MESSAGE_SENDER.get(player));
	}

	public void sendPrivateMessage(UPlayer sender, String msg) {
		player.sendMessage(Ublisk.getPrefix("Private Message") + sender.getName() + ChatColor.DARK_GRAY + ": "
				+ ChatColor.RESET + ChatColor.BOLD + msg);
		sender.sendMessage(Ublisk.getPrefix("Private Message") + ChatColor.AQUA + " -> " + player.getName()
				+ ChatColor.DARK_GRAY + ": " + ChatColor.RESET + ChatColor.BOLD + msg);
	}

	private static final String BUILDER_MODE_INV_PATH = Main.getInstance().getDataFolder() + "/inv";

	public boolean isInBuilderMode() {
		// Check if an inventory file exists, because the item is deleted when a player goes out of builder mode.
		return new File(BUILDER_MODE_INV_PATH, player.getName() + ".yml").exists();
	}

	public void setBuilderModeEnabled(boolean bool) {
		if (bool == this.isInBuilderMode()){
			return;
		}
		
		if (bool) { // Enable builder mode
			this.saveInventoryToFile(BUILDER_MODE_INV_PATH); // Save inventory to file
			this.clearInventory();
			this.setGameMode(GameMode.CREATIVE);
			this.sendMessage(Message.BUILDER_MODE_ACTIVATED);
		} else { // Disable builder mode
			this.fillInventoryFromFile(BUILDER_MODE_INV_PATH);
			this.setGameMode(GameMode.ADVENTURE);
			this.sendMessage(Message.BUILDER_MODE_DEACTIVATED);
		}
	}

	/**
	 * If player is not in builder mode. put player in builder mode and visa versa
	 */
	public void toggleBuilderMode() {
		setBuilderModeEnabled(!isInBuilderMode());
	}

	public void refreshLastSeenDate() {
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		Date dateobj = new Date();
		String date = df.format(dateobj);
		DataFile.LAST_PLAYED.getConfig().set("last-played." + this.getUniqueId(), date);
	}

	public String getLastSeenDate() {
		if (DataFile.LAST_PLAYED.getConfig().isSet("last-played." + this.getUniqueId())) {
			return DataFile.LAST_PLAYED.getConfig().getString("last-played." + this.getUniqueId());
		} else {
			return "Never";
		}
	}

	public void tracker(Map<UUID, Integer> map) {
		map.put(this.getUniqueId(), map.get(this.getUniqueId()) + 1);
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
	
	public void sendActionBarMessage(String message, long time) {
		new URunnable (){
			
			long ticksRan = 20;
			
			@Override
			public void run(){
				ticksRan++;
				
				Ublisk.NMS.sendActionBarMessage(player, message);

				if (ticksRan > time){
					this.cancel();
					return;
				}
			}
			
		}.runTimer(1);
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

	/**
	 * @return Mana, value between 0 and 20
	 */
	public int getMana() {
		return player.getFoodLevel();
	}

	/**
	 * @param mana
	 *        An integer, 0-20
	 */
	public void setMana(int mana) {
		player.setFoodLevel(mana);
	}

	public void setAfk(boolean setAfk) {
		AFK.setAfk(this, setAfk);
		if (setAfk) {
			//Ublisk.broadcastPrefixedMessage(this.getName() + " is now AFK.");
			Ublisk.broadcastMessage(
					Ublisk.getPrefixComponents(),
					this.getDisplayName(ChatColor.YELLOW, false),
					TextComponent.fromLegacyText(ChatColor.YELLOW + " is now AFK")
					);
		} else {
			//Ublisk.broadcastPrefixedMessage(this.getName() + " is no longer AFK.");
			Ublisk.broadcastMessage(
					Ublisk.getPrefixComponents(),
					this.getDisplayName(ChatColor.YELLOW, false),
					TextComponent.fromLegacyText(ChatColor.YELLOW + " is no longer AFK")
					);
		}
	}

	public boolean isAfk() {
		return AFK.isAfk(this);
	}

	/**
	 * @return Town the player is currently in, or null if the player is not in one.
	 */
	public Town getTown() {
		for (Town town : Town.values()) {
			Location loc = player.getLocation();
			if (loc.getX() < town.lessX() && loc.getX() > town.moreX() && loc.getZ() < town.lessZ()
					&& loc.getZ() > town.moreZ()) {
				return town;
			}
		}
		return null;
	}

	/**
	 * @return Current town, or last town if the player is no longer in a town.
	 */
	public Town getLastTown() {
		String s = DataFile.TOWN.getConfig().getString("last-town." + this.getUniqueId());

		if (s == null){
			return Town.INTRODUCTION;
		}

		Town town = Town.fromString(s);
		
		if (town == null){
			return Town.INTRODUCTION;
		}
		
		return town;
	}
	
	/**
	 * @return Town name if in one, otherwise "None"
	 */
	public String getTownName() {
		Town town = this.getTown();
		if (town == null){
			return "None";
		} else {
			return town.getName();
		}
	}

	/**
	 * You should not use this. It is called automatically every time the players enters a new town.
	 * @param town
	 */
	public void setLastTown(Town town) {
		DataFile.TOWN.getConfig().set("last-town." + this.getUniqueId(), town.getName());
	}

	public void sendTitle(String title, String subtitle) {
		Ublisk.NMS.sendTitle(player, title, subtitle);
	}

	public void sendTitle(String title) {
		this.sendTitle(title, "");
	}

	public void sendSubTitle(String subtitle) {
		this.sendTitle("", subtitle);
	}

	public boolean isDead() {
		return player.isDead();
	}

	public int getHealth() {
		return (int) player.getHealth();
	}
	
	public void setHealth(double health) {
		player.setHealth(health);
	}

	public int getMaxHealth() {
		return CustomHealth.getMaxHealth(this);
	}
	
	public void heal(){
		this.givePotionEffect(PotionEffectType.REGENERATION, 20, 255);
	}

	public Spigot spigot() {
		return player.spigot();
	}

	public void setResourcePack(String pack) {
		player.setResourcePack(pack);
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
		player.setCollidable(false);
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
	
	private static final Map<UUID, List<String>> ABILITIES_COOLDOWN = new HashMap<>();

	/**
	 * Checks if the player has enough mana and if their level is high enough (also for cooldown). If both or one of these conditions is not true, it will send message(s). Null value is permitted.
	 * 
	 * @param ability
	 */
	public void doAbility(Ability ability) {
		if (ability == null) {
			throw new IllegalArgumentException("Ability is cannot be null.");
		}
		
		if (!this.abilitiesEnabled()){
			return;
		}
		
		Logger.log(LogLevel.DEBUG, "class name: " + ability.getName());
		
		UUID uuid = this.getUniqueId();
		
		Logger.log(LogLevel.DEBUG, "Contains: " + ABILITIES_COOLDOWN.containsKey(uuid));
		
		if (ABILITIES_COOLDOWN.containsKey(uuid) && ABILITIES_COOLDOWN.get(uuid).contains(ability.getName())){
			this.sendPrefixedMessage(ChatColor.RED + "You can't do this ability yet. Please wait a few seconds.");
			return;
		}
		
		List<String> cooldownAbilities;
		if (ABILITIES_COOLDOWN.containsKey(uuid))
			 cooldownAbilities = ABILITIES_COOLDOWN.get(uuid);
		else cooldownAbilities = new ArrayList<>();
		
		cooldownAbilities.add(ability.getName());
		ABILITIES_COOLDOWN.put(uuid, cooldownAbilities);
		
		Logger.log(LogLevel.DEBUG, "Contains after: " + ABILITIES_COOLDOWN.containsKey(uuid));
		
		new URunnable(){
			public void run(){
				List<String> list = ABILITIES_COOLDOWN.get(uuid);
				list.remove(ability.getName());
				ABILITIES_COOLDOWN.put(uuid, list);
			}
		}.runLater(ability.getCooldown());

		if (ability.getMinimumLevel() > player.getLevel()) {
			this.sendMessage(Message.ABILITY_NOT_ENOUGH_LEVEL);
			return;
		}

		if (this.getMana() < ability.getMana()){
			this.sendMessage(Message.ABILITY_NOT_ENOUGH_MANA);
			return;
		}

		if (ability.run(this)){
			//If the ability casted successfully
			PlayerInfo.ABILITIES.put(this.getUniqueId(), PlayerInfo.ABILITIES.get(this.getUniqueId()) + 1);
			this.setMana(this.getMana() - ability.getMana());
		}
	}
	
	public static class Test extends URunnable {
		public void run(){
			for (UPlayer player : Ublisk.getOnlinePlayers()){
				if (!ABILITIES_COOLDOWN.containsKey(player.getUniqueId())){
					Logger.log(LogLevel.DEBUG, "does not contain");
				} else {
					Logger.log(LogLevel.DEBUG, String.join(", ", ABILITIES_COOLDOWN.get(player.getUniqueId())));
				}
			}
		}
	}
	
	public void setVelocity(Vector vector) {
		player.setVelocity(vector);
	}
	
	public Vector getVelocity(){
		return player.getVelocity();
	}
	
	/**
	 * Gives the player a potion effect without particles
	 * @param type The potion effect type
	 * @param duration How long the effect should last for, <b>in ticks</b>
	 * @param amplifier How strong the effect should be. Amplifier 1 -> Speed II
	 */
	public void givePotionEffect(PotionEffectType type, int duration, int amplifier){
		player.addPotionEffect(new PotionEffect(type, duration, amplifier, true, false));
	}
	
	/**
	 * Sets a player's guild. Warning: does not perform any checks regarding if the player is already in a guild, etc.
	 * @param guild
	 */
	public void setGuild(Guild guild){
		Guild.setGuild(guild, player);
	}
	
	/**
	 * @return The player's guild, or <kbd>null</kbd> if the player is not in a guild.
	 */
	public Guild getGuild(){
		return Guild.getGuild(player);
	}
	
	/**
	 * @return "None" if player is not in a guild, otherwise a guild name.
	 */
	public String getGuildName(){
		Guild guild = this.getGuild();
		if (guild == null){
			return "None";
		} else {
			return guild.getName();
		}
	}
	
	public void leaveGuild(){
		Guild.leaveGuild(player);
	}
	
	public boolean isOnGround() {
		return player.isOnGround();
	}
	
	public Block getTargetBlock(int range){
		return player.getTargetBlock((Set<Material>) null, range);
	}
	
	public Block getTargetBlock(){
		return this.getTargetBlock(100);
	}
	
	public void setFrozen(boolean frozen){
		PlayerFreeze.setFrozen(player, frozen);
	}
	
	public boolean isFrozen(){
		return PlayerFreeze.isFrozen(player);
	}
	
	public Entity getEntity(){
		return this.getPlayer();
	}
	
	public String getStatsURL(){
		return Var.WEBSITE_ROOT + "stats/player.php?player=" + this.getName();
	}
	
	private boolean abilitiesEnabled = true;
	
	public boolean abilitiesEnabled(){
		return abilitiesEnabled;
	}
	
	public void setAbilitiesEnabled(boolean enabled){
		abilitiesEnabled = enabled;
	}
	
	/**
	 * Executes a command by the player.
	 * @param command
	 * @return True if the command executed successfully, false otherwise.
	 */
	public boolean executeCommand(String command){
		return Bukkit.dispatchCommand(player, command);
	}
	
	public void setAllowFlight(boolean allow){
		player.setAllowFlight(allow);
	}
	
	public void setFlying(boolean flying){
		player.setFlying(flying);
	}
	
	public boolean getAllowFlight(){
		return player.getAllowFlight();
	}
	
	public boolean isFlying(){
		return player.isFlying();
	}
	
	public BaseComponent[] getDisplayName(ChatColor color, boolean bold){
		return new ComponentBuilder(this.getName())
				.color(color)
				.bold(bold)
				.event(new HoverEvent(
					HoverEvent.Action.SHOW_TEXT,
					new ComponentBuilder("XP: " + this.getXP() + " / " + CustomXP.getRequiredXP(this.getLevel() + 1))
					.color(ChatColor.AQUA)
					.append("\n")
					.append("Health: " + this.getHealth() + " / " + this.getMaxHealth())
					.append("\n")
					.append("Guild: " + this.getGuildName())
					.append("\n\n")
					.append("Click to open statistics").color(ChatColor.GRAY).italic(true)
					.create()))
				.event(new ClickEvent(
							ClickEvent.Action.OPEN_URL, this.getStatsURL()
						)).create();
	}
	
	public void setWeather(WeatherType weather){
		player.setPlayerWeather(weather);
	}

	@Override
	public String toString() {
		return this.getUniqueId().toString();
	}

}