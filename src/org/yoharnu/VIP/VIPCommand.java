package org.yoharnu.VIP;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class VIPCommand implements CommandExecutor {
	private final VIP plugin;
	public VIPPermissions permissions;

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
		permissions = plugin.permissions;
		Player player = (Player) sender;
		/*if (!(permissions.canAddPlayer(player) || permissions.canRemovePlayer(player) || permissions.canEnable(player) || permissions.canDisable(player))){
			sender.sendMessage("You do not have permission to do that.");
			return true;
		}*/
		if (args.length==1){
			if(args[0].equalsIgnoreCase("enable") && !plugin.getConfig().getBoolean("enabled", true)){
				if(permissions.canEnable(player)){
					plugin.getConfig().setProperty("enabled", true);
					plugin.getConfig().save();
					System.out.println("VIP enabled");
					sender.sendMessage("VIP enabled");
				}
				else{
					sender.sendMessage("You do not have permission to do that.");
				}
				return true;
			}
			else if(args[0].equalsIgnoreCase("enable")){
				if(permissions.canEnable(player)){
					sender.sendMessage("VIP is already enabled.");
				}
				else{
					sender.sendMessage("You do not have permission to do that.");
				}
				return true;
			}
			else if(args[0].equalsIgnoreCase("disable") && plugin.getConfig().getBoolean("enabled", true)){
				if(permissions.canDisable(player)){
					plugin.getConfig().setProperty("enabled", false);
					plugin.getConfig().save();
					System.out.println("VIP disabled");
					sender.sendMessage("VIP disabled");
				}
				else{
					sender.sendMessage("You do not have permission to do that.");
				}
				return true;
			}
			else if(args[0].equalsIgnoreCase("disable")){
				if(permissions.canDisable(player)){
					sender.sendMessage("VIP is already disabled.");
				}
				else{
					sender.sendMessage("You do not have permission to do that.");
				}
				return true;
			}
			else if(args[0].equalsIgnoreCase("kick")){
				if(permissions.canToggleKick(player)){
					plugin.getConfig().setProperty("Kick last logged", !plugin.getConfig().getBoolean("Kick last logged", false));
					plugin.getConfig().save();
					sender.sendMessage("\"Kick last logged\" is now set to: " + plugin.getConfig().getString("Kick last logged"));
				}
				else{
					sender.sendMessage("You do not have permission to do that.");
				}
				return true;
			}
		}
		if(!plugin.getConfig().getBoolean("enabled", true)){
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
			if(permissions.canAddPlayer(player)){
				if(vip){
					sender.sendMessage("That player is already a VIP.");
					return true;
				}
				plugin.getConfig().setProperty("VIPs." + playerName, true);
				plugin.getConfig().save();
				sender.sendMessage(playerName + " is now a VIP.");
			}
			else{
				sender.sendMessage("You do not have permission to do that.");
			}
			return true;
		}
		else if(action.equalsIgnoreCase("remove")){
			if(permissions.canRemovePlayer(player)){
				if(!vip){
					sender.sendMessage("That player is not a VIP.");
					return true;
				}
				plugin.getConfig().removeProperty("VIPs." + playerName);
				plugin.getConfig().save();
				sender.sendMessage(playerName + " is no longer a VIP.");
			}
			else{
				sender.sendMessage("You do not have permission to do that.");
			}
		}
		return true;
	}

}
