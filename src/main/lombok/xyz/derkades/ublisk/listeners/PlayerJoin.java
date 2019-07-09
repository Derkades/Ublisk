package xyz.derkades.ublisk.listeners;

import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.BOLD;
import static org.bukkit.ChatColor.DARK_AQUA;
import static org.bukkit.ChatColor.RESET;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;

import xyz.derkades.ublisk.DataFile;
import xyz.derkades.ublisk.Town;
import xyz.derkades.ublisk.database.PlayerInfo;
import xyz.derkades.ublisk.ext.com.bobacadodl.imgmessage.ImageChar;
import xyz.derkades.ublisk.utils.UPlayer;
import xyz.derkades.ublisk.utils.URunnable;
import xyz.derkades.ublisk.utils.settings.Setting;

public class PlayerJoin implements Listener {

	@EventHandler
	public void onJoin(final PlayerJoinEvent event){
		final UPlayer player = new UPlayer(event);
		final String pn = player.getName();
		final UUID uuid = player.getUniqueId();

		PlayerInfo.resetHashMaps(player);

		player.setCollidable(false);

		event.setJoinMessage(DARK_AQUA + "" + BOLD + pn + RESET + AQUA + " has joined");

		player.givePotionEffect(PotionEffectType.BLINDNESS, 1*20, 0);
		player.givePotionEffect(PotionEffectType.NIGHT_VISION, 1*20, 0);

		player.sendTitle(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Ublisk", ChatColor.YELLOW + "Welcome back, " + pn + "!");

		if (player.getSetting(Setting.PLAY_MUSIC)){
			final Town town = player.getTown();
			if (town != null)
				town.playThemeToPlayer(player);
		}

		player.tracker(PlayerInfo.JOIN_COUNT);

		//String ip = player.getPlayer().getAddress().toString();
		//ip = ip.replace("/", "");
		final String ip = player.getIP();
		DataFile.IP.getConfig().set("ip." + uuid, ip);

		player.updateXPBar();

		// Disable builder mode if the player no longer has permission
		if (!player.hasPermission("ublisk.builder")){
			player.setBuilderModeEnabled(false);
		}

		if (player.isInBuilderMode()){
			player.setGameMode(GameMode.CREATIVE);
		} else {
			player.setGameMode(GameMode.ADVENTURE);
		}

		player.setFlying(false);

		player.setAttribute(Attribute.GENERIC_ATTACK_SPEED, 0.7);

		new URunnable() {

			@Override
			public void run() {
				player.sendSpacers(10);

				final String[] fancyStrings = new String[] {
						"                       ",
						" # # ##  #   # ### # # ",
						" # # # # #   # #   # # ",
						" # # ##  #   # ### ##  ",
						" # # # # #   #   # ##  ",
						" ### ##  ### # ### # # ",
						"                       "
				};
				for (final String string : fancyStrings) {
					player.sendMessage(string
							.replace("#", ChatColor.AQUA + "" + ChatColor.BOLD + ImageChar.DARK_SHADE.getChar())
							.replace(" ", ChatColor.DARK_AQUA + "" + ChatColor.BOLD + ImageChar.DARK_SHADE.getChar()));
				}
				player.sendMessage(ChatColor.GRAY
						+ "Welcome to Ublisk! If you find any bugs, please report them using /bug [description].");
			}
		}.runLater(4);

		/*Ublisk.runAsync(() -> {
			Connection connection = null;

			PreparedStatement check = null;
			ResultSet checkResult = null;

			PreparedStatement insert = null;
			try {
				connection = Ublisk.getDatabaseConnection("Player join insert");

				check = connection.prepareStatement("SELECT EXISTS(SELECT * FROM player_info_2 WHERE uuid=?) AS `exists`");
				check.setString(1, uuid.toString());
				checkResult = check.executeQuery();
				checkResult.next();

				boolean exists = checkResult.getBoolean("exists");

				Logger.log(LogLevel.DEBUG, "Exists: " + exists);

				if (!exists) {
					insert = connection.prepareStatement("INSERT INTO `player_info_2) (uuid, name, xp) VALUES (?, ?, ?)");
					insert.setString(1, uuid.toString());
					insert.setString(2, pn);
					insert.setInt(3, 0);
					insert.execute();
				}
			} catch (SQLException e) {
				Ublisk.exception(e, PlayerJoin.class);
			} finally {
				try {
					if (check != null) check.close();
					if (checkResult != null) checkResult.close();
					if (insert != null) insert.close();
				} catch (SQLException e) {
					Ublisk.exception(e, PlayerJoin.class);
				}
			}
		});*/
	}

}
