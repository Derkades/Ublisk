package xyz.derkades.ublisk.utils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import xyz.derkades.derkutils.Cooldown;
import xyz.derkades.ublisk.DataFile;
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
import xyz.derkades.ublisk.utils.exception.PlayerNotFoundException;
import xyz.derkades.ublisk.utils.inventory.InvUtils;
import xyz.derkades.ublisk.utils.inventory.UInventory;
import xyz.derkades.ublisk.utils.settings.Setting;
import xyz.derkades.ublisk.weapons.abilities.Ability;

public class UPlayer {

	public static Map<UUID, Long> LAST_WALK_TIME = new HashMap<>();

	final private Player player;
	final private OfflinePlayer offline;

	public UPlayer(final Player player) {
		if (player == null)
			throw new IllegalArgumentException("Player must not be null");

		this.player = player;
		this.offline = player;
	}

	public UPlayer(final UUID uuid) {
		if (uuid == null)
			throw new IllegalArgumentException("UUID must not be null");
		//this.player = Bukkit.getPlayer(uuid);
		//this.offline = this.player;
		final OfflinePlayer offline = Bukkit.getOfflinePlayer(uuid);
		this.offline = offline;
		if (offline.isOnline()){
			this.player = (Player) offline;
		} else {
			this.player = null;
		}
	}

	public UPlayer(final String name) throws PlayerNotFoundException {
		if (name == null)
			throw new IllegalArgumentException("Name must not be null");

		final OfflinePlayer offline = Ublisk.getOfflinePlayer(name);
		this.offline = offline;
		if (offline.isOnline()){
			this.player = (Player) offline;
		} else {
			this.player = null;
		}
	}

	public UPlayer(final CommandSender sender) {
		if (sender instanceof Player) {
			this.player = (Player) sender;
			this.offline = this.player;
		} else {
			throw new IllegalArgumentException("CommandSender is not a player");
		}
	}

	public UPlayer(final PlayerEvent event) {
		if (event == null)
			throw new IllegalArgumentException("Event must not be null");
		this.player = event.getPlayer();
		this.offline = this.player;
	}

	public UPlayer(final EntityEvent event) {
		if (event == null)
			throw new IllegalArgumentException("Event must not be null");

		final Entity entity = event.getEntity();
		if (entity instanceof Player) {
			this.player = (Player) entity;
			this.offline = this.player;
		} else {
			throw new IllegalArgumentException("Entity is not a player");
		}
	}

	public UPlayer(final Entity entity) {
		if (entity == null)
			throw new IllegalArgumentException("Entity must not be null");

		if (entity instanceof Player) {
			this.player = (Player) entity;
			this.offline = this.player;
		} else {
			throw new IllegalArgumentException("Entity is not a player");
		}
	}

	public UPlayer(final HumanEntity human) {
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
	public UPlayer(final OfflinePlayer offline) {
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

	/**
	 * This will throw an UnsupportedOperationException if the player is offline.
	 * The use of this method is discouraged and should be avoided whenever possible
	 * @return Bukkit player
	 * @deprecated Use {@link #bukkit()}
	 */
	@Deprecated
	public Player getPlayer() {
		if (this.player == null) {
			throw new UnsupportedOperationException("The player is not online");
		}

		return this.player;
	}

	/**
	 * @deprecated Use UPlayer methods
	 * @return Bukkit OfflinePlayer object.
	 */
	@Deprecated
	public OfflinePlayer getOfflinePlayer() {
		return this.offline;
	}

	public void setLifeCrystals(final int amount) {
		DataFile.LIFE_CRYSTAL.getConfig().set("life." + this.getUniqueId(), amount);
	}

	public int getLifeCrystals() {
		if (DataFile.LIFE_CRYSTAL.getConfig().isSet("life." + this.getUniqueId())) {
			return DataFile.LIFE_CRYSTAL.getConfig().getInt("life." + this.getUniqueId());
		} else {
			return 5;
		}
	}

	public UInventory getInventory() {
		return new UInventory(this.player.getInventory());
	}

	public int getVotingPoints() {
		final String path = "voting." + this.getUniqueId();
		if (DataFile.VOTING.getConfig().isSet(path)) {
			return DataFile.VOTING.getConfig().getInt(path);
		} else {
			this.setVotingPoints(0);
			return 0;
		}
	}

	public void setVotingPoints(final int i) {
		final String path = "voting." + this.getUniqueId();
		DataFile.VOTING.getConfig().set(path, i);
	}

	public boolean hasVotingPoints(final int i) {
		return this.getVotingPoints() >= i;
	}

	public boolean hasPermission(final String permission) {
		return this.player.hasPermission(permission);
	}

	public Location getLocation() {
		return this.player.getLocation();
	}

	public void teleport(final Location loc) {
		this.player.teleport(loc);
	}

	public void teleport(final double x, final double y, final double z) {
		this.teleport(new Location(Var.WORLD, x, y, z));
	}

	public void teleport(final double x, final double y, final double z, final int pitch, final int yaw) {
		this.teleport(new Location(Var.WORLD, x, y, z, pitch, yaw));
	}

	public UUID getUniqueId() {
		return this.offline.getUniqueId();
	}

	public void sendMessage(final String msg) {
		this.player.sendMessage(msg);
	}

	public void sendMessage(final TextComponent text) {
		this.player.spigot().sendMessage(text);
	}

	public void sendMessage(final Message message) {
		this.player.sendMessage(message.toString());
	}

	public void sendMessage(final BaseComponent[] text) {
		this.player.spigot().sendMessage(text);
	}

	@Deprecated
	public void sendMessage(final BaseComponent[]... text){
//		BaseComponent[] newArray = new TextComponent[]{};
//		for (BaseComponent[] componentArray : text){
//			newArray = ArrayUtils.addAll(newArray, componentArray);
//		}

		final List<BaseComponent> message = new ArrayList<>();
		for (final BaseComponent[] componentArray : text) {
			message.addAll(Arrays.asList(componentArray));
		}
		this.sendMessage(message);
	}

	public void sendMessage(final Object o) {
		this.player.sendMessage(o + "");
	}

	public void sendPrefixedMessage(final String message){
		this.player.sendMessage(Ublisk.getPrefix() + message);
	}

	public void sendPrefixedMessage(final String prefix, final String message){
		this.player.sendMessage(Ublisk.getPrefix(prefix) + message);
	}

	public void sendSpacer() {
		this.player.sendMessage(" ");
	}

	public void sendSpacers(final int spacers) {
		for (int i = 0; i <= spacers; i++) {
			this.sendSpacer();
		}
	}

	public boolean isSneaking() {
		return this.player.isSneaking();
	}

	public int getLevel() {
		return CustomXP.getLevel(this.offline);
	}

	public void setXP(final int xp) {
		CustomXP.setXP(this.offline, xp);

		if (this.player != null) CustomXP.updateXPBar(this.player);
	}

	public int getXP() {
		return CustomXP.getXP(this.offline);
	}

	public void updateXPBar(){
		CustomXP.updateXPBar(this.player);
	}

	public void addXP(final int xp) {
		this.setXP(this.getXP() + xp);
		CustomXP.updateXPBar(this.player);
	}

	public String getName() {
		return this.offline.getName();
	}

	public void addFriend(final OfflinePlayer newFriend) {
		final List<String> list = DataFile.FRIENDS.getConfig().getStringList("friends." + this.getUniqueId());

		if (list.contains(newFriend.getUniqueId().toString())){
			throw new UnsupportedOperationException("Friend is already in friends list");
		}

		list.add(newFriend.getUniqueId().toString());

		DataFile.FRIENDS.getConfig().set("friends." + this.getUniqueId(), list);

		FriendsBossBar.resetBars(this);
	}

	public void removeFriend(final int index) {
		final List<String> friendsUUIDList = DataFile.FRIENDS.getConfig().getStringList("friends." + this.getUniqueId());

		if (index > friendsUUIDList.size()){
			throw new IllegalArgumentException("Index can't be more than list size");
		}

		friendsUUIDList.remove(index);

		DataFile.FRIENDS.getConfig().set("friends." + this.getUniqueId(), friendsUUIDList);

		FriendsBossBar.resetBars(this);
	}

	public void removeFriend(final OfflinePlayer friendToRemove) {
		final List<String> list = DataFile.FRIENDS.getConfig().getStringList("friends." + this.getUniqueId());

		if (!list.contains(friendToRemove.getUniqueId().toString())){
			throw new IllegalArgumentException(friendToRemove.getName() + " is not " + this.getName() + "'s friend");
		}

		list.remove(friendToRemove.getUniqueId().toString());

		DataFile.FRIENDS.getConfig().set("friends." + this.getUniqueId(), list);

		FriendsBossBar.resetBars(this);
	}

	public List<OfflinePlayer> getFriends() {
		final List<String> uuidStrings = DataFile.FRIENDS.getConfig().getStringList("friends." + this.getUniqueId());
		final List<OfflinePlayer> players = new ArrayList<>();
		for (final String uuidString : uuidStrings){
			final UUID uuid = UUID.fromString(uuidString);
			players.add(Bukkit.getOfflinePlayer(uuid));
		}
		return players;
	}

	public List<UPlayer> getOnlineFriends(){
		final List<UPlayer> list = new ArrayList<>();
		for (final OfflinePlayer offlineFriend : this.getFriends()){
			if (offlineFriend.isOnline()){
				list.add(new UPlayer(offlineFriend));
			}
		}
		return list;
	}

	public boolean isFriend(final OfflinePlayer offlinePlayer){
		for (final OfflinePlayer friend : this.getFriends()){
			if (friend.getName().equals(offlinePlayer.getName())){
				return true;
			}
		}
		return false;
	}

	public boolean getSetting(final Setting setting) {
		return setting.get(this.player);
	}

	public void setSetting(final Setting setting, final boolean bool) {
		setting.put(this.player, bool);
	}

	public GameMode getGameMode() {
		return this.player.getGameMode();
	}

	public void setGameMode(final GameMode gamemode) {
		this.player.setGameMode(gamemode);
	}

	public static final Map<UUID, UUID> LAST_MESSAGE_SENDER = new HashMap<>();

	public void setLastSender(final UPlayer player) {
		LAST_MESSAGE_SENDER.put(this.getUniqueId(), player.getUniqueId());
	}

	/**
	 * Get the last player who sent this player a message. <b>This player may be offline</b>
	 * @return A player or null if no player has sent a message to this player since server restart.
	 */
	public UPlayer getLastSender() {
		if (!LAST_MESSAGE_SENDER.containsKey(this.player.getUniqueId())) {
			return null;
		}

		return new UPlayer(LAST_MESSAGE_SENDER.get(this.player.getUniqueId()));
	}

	public void sendPrivateMessage(final UPlayer sender, final String msg) {
		this.player.sendMessage(Ublisk.getPrefix("Private Message") + sender.getName() + ChatColor.DARK_GRAY + ": "
				+ ChatColor.RESET + ChatColor.BOLD + msg);
		sender.sendMessage(Ublisk.getPrefix("Private Message") + ChatColor.AQUA + " -> " + this.player.getName()
				+ ChatColor.DARK_GRAY + ": " + ChatColor.RESET + ChatColor.BOLD + msg);
	}

	private static final String BUILDER_MODE_INV_PATH = Main.getInstance().getDataFolder() + "/inv";

	/**
	 * @return If the player is in builder mode
	 */
	public boolean isInBuilderMode() {
		// Check if an inventory file exists, because the item is deleted when a player goes out of builder mode.
		return new File(BUILDER_MODE_INV_PATH, this.player.getName() + ".yml").exists();
	}

	/**
	 * Enables or disables builder mode for the player. If the specified boolean is equal to the current status, the method will return silently.
	 * @param bool If builder mode should be enabled or disabled for the player
	 */
	public void setBuilderModeEnabled(final boolean bool) {
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
		this.setBuilderModeEnabled(!this.isInBuilderMode());
	}

	public void refreshLastSeenDate() {
		final DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		final Date dateobj = new Date();
		final String date = df.format(dateobj);
		DataFile.LAST_PLAYED.getConfig().set("last-played." + this.getUniqueId(), date);
	}

	public String getLastSeenDate() {
		if (DataFile.LAST_PLAYED.getConfig().isSet("last-played." + this.getUniqueId())) {
			return DataFile.LAST_PLAYED.getConfig().getString("last-played." + this.getUniqueId());
		} else {
			return "Never";
		}
	}

	public void tracker(final Map<UUID, Integer> map) {
		map.put(this.getUniqueId(), map.get(this.getUniqueId()) + 1);
	}

	public void setMoney(final int amount) {
		Money.set(this.player, amount);
	}

	public int getMoney() {
		return Money.get(this.player);
	}

	public boolean hasMoney(final int amount) {
		return Money.get(this.player) >= amount;
	}

	@Deprecated
	public void sendActionBarMessage(final String message, final long time) {
		new URunnable (){

			long ticksRan = 20;

			@Override
			public void run(){
				this.ticksRan++;

				Ublisk.NMS.sendActionBarMessage(UPlayer.this.player, message);

				if (this.ticksRan > time){
					this.cancel();
					return;
				}
			}

		}.runTimer(1);
	}

	public void sendActionBarMessage(final BaseComponent[] components) {
		this.player.spigot().sendMessage(ChatMessageType.ACTION_BAR, components);
	}

	public void playNotMovingParticle(final Particle particle, final double x, final double y, final double z) {
		this.player.spawnParticle(particle, x, y, z, 0, 0, 0, 0, 1);
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
		return this.player.getFoodLevel();
	}

	/**
	 * @param mana
	 *        An integer, 0-20
	 */
	public void setMana(final int mana) {
		this.player.setFoodLevel(mana);
	}

	public void setAfk(final boolean setAfk) {
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
		for (final Town town : Town.values()) {
			final Location loc = this.player.getLocation();
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
		final String s = DataFile.TOWN.getConfig().getString("last-town." + this.getUniqueId());

		if (s == null){
			return Town.INTRODUCTION;
		}

		final Town town = Town.fromString(s);

		if (town == null){
			return Town.INTRODUCTION;
		}

		return town;
	}

	/**
	 * @return Town name if in one, otherwise "None"
	 */
	public String getTownName() {
		final Town town = this.getTown();
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
	public void setLastTown(final Town town) {
		DataFile.TOWN.getConfig().set("last-town." + this.getUniqueId(), town.getName());
	}

	@Deprecated
	public void sendTitle(final String title, final String subtitle) {
		Ublisk.NMS.sendTitle(this.player, title, subtitle);
	}

	public void sendTitle(final BaseComponent[] components) {
		this.player.spigot().sendMessage(ChatMessageType.SYSTEM, components);
	}

	public void sendTitle(final String title) {
		this.sendTitle(title, "");
	}

	public void sendSubTitle(final String subtitle) {
		this.sendTitle("", subtitle);
	}

	public boolean isDead() {
		return this.player.isDead();
	}

	public int getHealth() {
		return (int) this.player.getHealth();
	}

	public void setHealth(final double health) {
		this.player.setHealth(health);
	}

	public int getMaxHealth() {
		return CustomHealth.getMaxHealth(this);
	}

	public void setHealthScale(final double scale) {
		this.player.setHealthScale(scale);
	}

	public void heal(){
		this.givePotionEffect(PotionEffectType.REGENERATION, 20, 255);
	}

	public Spigot spigot() {
		return this.player.spigot();
	}

	/**
	 * This will throw an UnsupportedOperationException if the player is offline.
	 * @return Bukkit player
	 */
	public Player bukkit() {
		if (this.player == null) {
			throw new UnsupportedOperationException("The player is not online");
		}

		return this.player;
	}

	public void setResourcePack(final String pack) {
		this.player.setResourcePack(pack);
	}

	public void setAttribute(final Attribute attribute, final double d) {
		this.player.getAttribute(attribute).setBaseValue(d);
	}

	public double getAttribute(final Attribute attribute) {
		return this.player.getAttribute(attribute).getBaseValue();
	}

	public void playSound(final Sound sound, final float volume, final float pitch) {
		this.player.playSound(this.player.getLocation(), sound, volume, pitch);
	}

	public void playSound(final Sound sound, final float pitch) {
		this.playSound(sound, 1.0f, pitch);
	}

	public void playSound(final Sound sound) {
		this.playSound(sound, 1.0f);
	}

	public void setCollidable(final boolean bool) {
		this.player.setCollidable(false);
	}

	public void saveInventoryToFile(final String path) {
		InvUtils.saveIntentory(path, this.player);
	}

	public void fillInventoryFromFile(final String path) {
		this.clearInventory();
		InvUtils.restoreInventory(path, this.player);
	}

	public void openEnderchest() {
		this.player.openInventory(this.player.getEnderChest());
	}

	/**
	 * Clears inventory and armor slots.
	 */
	public void clearInventory() {
		final PlayerInventory inv = this.player.getInventory();
		for (final ItemStack item : inv.getContents())
			inv.remove(item);
		for (final ItemStack item : inv.getArmorContents())
			inv.remove(item);
	}

	public boolean inventoryContains(final ItemStack... items) {
		boolean hasItems = true;
		for (final ItemStack item : items) {
			if (!this.player.getInventory().containsAtLeast(item, item.getAmount())) {
				hasItems = false;
			}
		}
		return hasItems;
	}

	/**
	 * Checks if the player has enough mana and if their level is high enough (also for cooldown). If both or one of these conditions is not true, it will send message(s). Null value is permitted.
	 *
	 * @param ability
	 */
	public void doAbility(final Ability ability) {
		if (ability == null) {
			throw new IllegalArgumentException("Ability cannot be null.");
		}

		if (!this.abilitiesEnabled()){
			return;
		}

		final long timeLeft = Cooldown.getCooldown(ability.getName() + this.getUniqueId());

		if (timeLeft != 0){
			this.sendPrefixedMessage(String.format(ChatColor.RED + "You can't do this ability yet. Please wait %s seconds.", timeLeft / 1000));
			return;
		}

		if (ability.getMinimumLevel() > this.player.getLevel()) {
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
			Cooldown.addCooldown(ability.getName() + this.getUniqueId(), ability.getCooldown());
			this.setMana(this.getMana() - ability.getMana());
		}
	}

	public void setVelocity(final Vector vector) {
		this.player.setVelocity(vector);
	}

	public Vector getVelocity(){
		return this.player.getVelocity();
	}

	/**
	 * Gives the player a potion effect without particles
	 * @param type The potion effect type
	 * @param duration How long the effect should last for, <b>in ticks</b>
	 * @param amplifier How strong the effect should be. Amplifier 1 -> Speed II
	 */
	public void givePotionEffect(final PotionEffectType type, final int duration, final int amplifier){
		this.player.addPotionEffect(new PotionEffect(type, duration, amplifier, true, false));
	}

	/**
	 * Removes potion effects with the type specified from the player
	 * @see #removePotionEffect(PotionEffectType...)
	 * @param type
	 */
	public void removePotionEffect(final PotionEffectType type){
		this.player.removePotionEffect(type);
	}

	/**
	 * @see #removePotionEffect(PotionEffectType)
	 * @param effectTypes
	 */
	public void removePotionEffect(final PotionEffectType... effectTypes){
		for (final PotionEffectType type : effectTypes)
			this.removePotionEffect(type);
	}

	/**
	 * Sets a player's guild. Warning: does not perform any checks regarding if the player is already in a guild, etc.
	 * @param guild
	 */
	public void setGuild(final Guild guild){
		Guild.setGuild(guild, this.player);
	}

	/**
	 * @return The player's guild, or <kbd>null</kbd> if the player is not in a guild.
	 */
	public Guild getGuild(){
		return Guild.getGuild(this);
	}

	/**
	 * @return "None" if player is not in a guild, otherwise a guild name.
	 */
	public String getGuildName(){
		final Guild guild = this.getGuild();
		if (guild == null){
			return "None";
		} else {
			return guild.getName();
		}
	}

	public void leaveGuild(){
		Guild.leaveGuild(this);
	}

	public boolean isOnGround() {
		return this.player.isOnGround();
	}

	public Block getTargetBlock(final int range){
		return this.player.getTargetBlock((Set<Material>) null, range);
	}

	public Block getTargetBlock(){
		return this.getTargetBlock(100);
	}

	public void setFrozen(final boolean frozen){
		PlayerFreeze.setFrozen(this.player, frozen);
	}

	public boolean isFrozen(){
		return PlayerFreeze.isFrozen(this.player);
	}

	public Entity getEntity(){
		return this.getPlayer();
	}

	public String getStatsURL(){
		return Var.WEBSITE_ROOT + "stats/player.php?player=" + this.getName();
	}

	private boolean abilitiesEnabled = true;

	public boolean abilitiesEnabled(){
		return this.abilitiesEnabled;
	}

	public void setAbilitiesEnabled(final boolean enabled){
		this.abilitiesEnabled = enabled;
	}

	/**
	 * Executes a command by the player.
	 * @param command
	 * @return True if the command executed successfully, false otherwise.
	 */
	public boolean executeCommand(final String command){
		return Bukkit.dispatchCommand(this.player, command);
	}

	public void setAllowFlight(final boolean allow){
		this.player.setAllowFlight(allow);
	}

	public void setFlying(final boolean flying){
		this.player.setFlying(flying);
	}

	public boolean getAllowFlight(){
		return this.player.getAllowFlight();
	}

	public boolean isFlying(){
		return this.player.isFlying();
	}

	public BaseComponent[] getDisplayName(final ChatColor color, final boolean bold){
		if (this.player != null) {
			//Return display name for online player
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
		} else {
			//Return display name for offline player
			return new ComponentBuilder(this.getName())
					.color(color)
					.bold(bold)
					.event(new HoverEvent(
						HoverEvent.Action.SHOW_TEXT,
						new ComponentBuilder("")
						.color(ChatColor.AQUA)
						.append("XP: " + this.getXP() + " / " + CustomXP.getRequiredXP(this.getLevel() + 1))
						.append("\n")
						.append("Click to open statistics").color(ChatColor.GRAY).italic(true)
						.create()))
					.event(new ClickEvent(
								ClickEvent.Action.OPEN_URL, this.getStatsURL()
							)).create();
		}
	}

	public void setWeather(final WeatherType weather){
		this.player.setPlayerWeather(weather);
	}

	public boolean isOnline() {
		return this.offline.isOnline();
	}

	public boolean isOp(){
		return this.offline.isOp();
	}

	public void setOp(final boolean isOp){
		this.offline.setOp(isOp);
	}

	public boolean isWhitelisted(){
		return this.offline.isWhitelisted();
	}

	public void setWhitelisted(final boolean whitelisted){
		this.offline.setWhitelisted(whitelisted);
	}

	public boolean isBanned(){
		return this.offline.isBanned();
	}

	public double getDistance(final UPlayer player) {
		return this.getDistance(player.getLocation());
	}

	public double getDistanceSquared(final UPlayer player) {
		return this.getDistanceSquared(player.getLocation());
	}

	public double getDistance(final Location location) {
		return this.getLocation().distance(location);
	}

	public double getDistanceSquared(final Location location) {
		return this.getLocation().distanceSquared(location);
	}

	public String getIP() {
		return this.player.getAddress().toString().split(":")[0];
	}

	public boolean isMoving() {
		if (LAST_WALK_TIME.containsKey(this.getUniqueId())) {
			final long time = System.currentTimeMillis() - LAST_WALK_TIME.get(this.getUniqueId());
			if (time < 100) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return this.getUniqueId().toString();
	}

}