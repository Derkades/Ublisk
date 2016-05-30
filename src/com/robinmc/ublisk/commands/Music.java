package com.robinmc.ublisk.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.robinmc.ublisk.Messages;
				Player player = (Player) sender;
				return true;
			} else {
				sender.sendMessage(Messages.noPlayer());
				return true;
			}
		}
	}
}
