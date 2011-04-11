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
		permissions=plugin.permissions;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!(sender instanceof Player)){
			sender.sendMessage("You cannot do that from the console.");
			return true;
		}
		Player player = (Player) sender;
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
			else if(args[0].equalsIgnoreCase("permissions")){
				if(player.isOp()){
					plugin.getConfig().setProperty("Use permissions for VIP list", !plugin.getConfig().getBoolean("Use permissions for VIP list", false));
					plugin.getConfig().save();
					sender.sendMessage("\"Use permissions for VIP list\" is now set to: " + plugin.getConfig().getString("Use permissions for VIP list"));
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
		String addRemove = args[1];
		String action = args[0];
		if(action.equalsIgnoreCase("add")){
			if(!permissions.canAddPlayer(player)){
				sender.sendMessage("You do not have permission to do that.");
			}
			if(!plugin.getConfig().getBoolean("Use permissions for VIP list", false)){
				int priority = 1;
				if(args.length==3){
					try{
						priority = Integer.parseInt(args[2].trim());
					}
					catch (NumberFormatException e){
						return false;
					}
				}
				if(priority<1){
					sender.sendMessage("Priority must be positive");
					return true;
				}
				plugin.getConfig().setProperty("VIPs." + addRemove, priority);
				plugin.getConfig().save();
				sender.sendMessage(addRemove + " is now a VIP with priority " + priority);
			}
			else{
				sender.sendMessage("VIP is using permissions. Add players manually.");
			}
			return true;
		}
		else if(action.equalsIgnoreCase("remove")){
			if(!permissions.canRemovePlayer(player)){
				sender.sendMessage("You do not have permission to do that.");
			}
			else if(!plugin.getConfig().getBoolean("Use permissions for VIP list", false)){
				plugin.getConfig().removeProperty("VIPs." + addRemove);
				if(plugin.getConfig().getString("VIPs")=="{}"){
					plugin.getConfig().removeProperty("VIPs");
				}
				plugin.getConfig().save();
			}
			else{
				sender.sendMessage("VIP is using permissions. Remove players manually.");
				return true;
			}
			sender.sendMessage(addRemove + " is no longer a VIP.");
		}
		return true;
	}

}
