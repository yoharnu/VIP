package org.yoharnu.VIP;

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
		if (!(sender instanceof Player)){
			sender.sendMessage("You cannot do that from the console.");
			return true;
		}
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
		String action = args[0];
		boolean vip = plugin.getConfig().getBoolean("VIPs." + playerName, false);
		if(action.equalsIgnoreCase("add")){
			if(vip){
				sender.sendMessage("That player is already a VIP.");
				return true;
			}
			plugin.getConfig().setProperty("VIPs." + playerName, true);
			plugin.getConfig().save();
			sender.sendMessage(playerName + " is now a VIP.");
			return true;
		}
		else if(action.equalsIgnoreCase("remove")){
			if(!vip){
				sender.sendMessage("That player is not a VIP.");
				return true;
			}
			plugin.getConfig().removeProperty("VIPs." + playerName);
			plugin.getConfig().save();
			sender.sendMessage(playerName + " is no longer a VIP.");
		}
		return true;
	}

}
