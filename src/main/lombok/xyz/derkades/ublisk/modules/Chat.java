package xyz.derkades.ublisk.modules;

public class Chat extends UModule {

	/*// TODO Use arraylist instead
	public static final Map<UUID, Boolean> IS_MUTED = new HashMap<>();
	public static final Map<UUID, Boolean> IS_SOFT_MUTED = new HashMap<>();

	@Override
	public void onEnable(){
		for (final UPlayer player : Ublisk.getOnlinePlayers()){
			IS_MUTED.put(player.getUniqueId(), false);
			IS_SOFT_MUTED.put(player.getUniqueId(), false);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(final PlayerJoinEvent event){
		final UUID uuid = event.getPlayer().getUniqueId();

		if (!IS_MUTED.containsKey(uuid)){
			IS_MUTED.put(uuid, false);
		}

		if (!IS_SOFT_MUTED.containsKey(uuid)){
			IS_SOFT_MUTED.put(uuid, false);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(final AsyncPlayerChatEvent event){
		event.setCancelled(true); // Don't send chat message, we'll send a custom message

		final UPlayer player = new UPlayer(event);

		if (Chat.IS_MUTED.get(player.getUniqueId())){
			player.sendMessage(Message.CANT_CHAT_MUTED);
			event.setCancelled(true);
			return;
		}

		for (final Trigger trigger : Trigger.values()){
			if (event.getMessage().equals(trigger.getTrigger())){
				event.setMessage(trigger.getMessage());
			}
		}

		ChatColor chatColor = ChatColor.WHITE;
		if (IS_SOFT_MUTED.get(player.getUniqueId()))
			chatColor = ChatColor.GRAY;

		final int level = player.getLevel();

		final BaseComponent[] message =
				ListUtils.combineArrays(
						new ComponentBuilder("")
								.append("[").reset().color(ChatColor.DARK_GRAY)
								.append(level + "").reset().color(ChatColor.GRAY)
								.append("] ").reset().color(ChatColor.DARK_GRAY)
								.append(player.getGroup().getName()).color(player.getGroup().getPrefixColor()).bold(player.getGroup().nameBold())
								.append(":").reset().color(ChatColor.DARK_GRAY)
								.append(" ")
								.create(),
						player.getDisplayName(player.getGroup().getNameColor(), player.getGroup().nameBold()),
						new ComponentBuilder("")
								.append(": ").reset().color(ChatColor.DARK_GRAY).bold(true)
								.append(event.getMessage()).reset().color(chatColor)
								.create()
				);

		Ublisk.getServer().spigot().broadcast(message);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void log(final AsyncPlayerChatEvent event){
		final UPlayer player = new UPlayer(event);

		player.tracker(PlayerInfo.CHAT_MESSAGES);

		Logger.log(LogLevel.CHAT, player.getName(), event.getMessage());
	}*/

}
