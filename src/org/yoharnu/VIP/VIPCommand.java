package org.yoharnu.VIP;

import java.util.List;


import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class VIPCommand implements CommandExecutor {
	private final VIP plugin;

	VIPCommand(VIP plugin){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!sender.isOp()){
			sender.sendMessage("You do not have permission to do that.");
			return true;
		}
		if (args.length<2){
			return false;
		}
		else if(!(args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove"))){
			return false;
		}
		String playerName = args[1];
		List<Player> Matches = plugin.getServer().matchPlayer(playerName);
		if(Matches.size()==0){
			sender.sendMessage("That player is not online.");
			return true;
		}
		if(Matches.size()>1){
			sender.sendMessage("There is more than one player by that name.");
			return true;
		}
		Player player = Matches.get(0);
		String action = args[0];
		boolean vip = plugin.getConfig().getBoolean("VIPs." + player.getName(), false);
		if(action.equalsIgnoreCase("add")){
			if(vip){
				sender.sendMessage("That player is already a VIP.");
				return true;
			}
			plugin.getConfig().setProperty("VIPs." + player.getName(), true);
			plugin.getConfig().save();
			sender.sendMessage(player.getName() + " is now a VIP.");
			player.sendMessage(((Player)sender).getName() + " gave you VIP status!");
			return true;
		}
		else if(action.equalsIgnoreCase("remove")){
			if(!vip){
				sender.sendMessage("That player is not a VIP.");
				return true;
			}
			plugin.getConfig().removeProperty("VIPs." + player.getName());
			plugin.getConfig().save();
			sender.sendMessage(player.getName() + " is no longer a VIP.");
			player.sendMessage(((Player)sender).getName() + " revoked your VIP status!");
		}

		return true;
	}

}
