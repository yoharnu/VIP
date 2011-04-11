package org.yoharnu.VIP;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nijiko.permissions.PermissionHandler;

public class VIPPermissions {
	private final VIP plugin;
	private static PermissionHandler permissions;

	public VIPPermissions(VIP plugin) {
		Plugin theYetiPermissions = plugin.getServer().getPluginManager().getPlugin("Permissions");
		if (theYetiPermissions != null) {
			permissions = ((com.nijikokun.bukkit.Permissions.Permissions) theYetiPermissions).getHandler();
		}
		this.plugin=plugin;
	}

	public boolean canAddPlayer(Player player){
		if (permissions != null){
			return permissions.has(player, "VIP.add");
		}
		else{
			return player.isOp();
		}
	}
	public boolean canRemovePlayer(Player player){
		if (permissions != null){
			return permissions.has(player, "VIP.remove");
		}
		else{
			return player.isOp();
		}
	}
	public boolean canEnable(Player player){
		if (permissions != null){
			return permissions.has(player, "VIP.enable");
		}
		else{
			return player.isOp();
		}
	}
	public boolean canDisable(Player player){
		if (permissions != null){
			return permissions.has(player, "VIP.disable");
		}
		else{
			return player.isOp();
		}
	}
	public boolean canToggleKick(Player player){
		if (permissions != null){
			return permissions.has(player, "VIP.toggleKick");
		}
		else{
			return player.isOp();
		}
	}
	public int isVIP(String playerName){
		if (permissions != null){
			List<Player> Matches = plugin.getServer().matchPlayer(playerName);
			Player player = null;
			if(Matches.size()>0){
				player = Matches.get(0);
				if(permissions.has(player, "VIP.VIP")){
					return 1;
				}
			}
			return 0;
		}
		else{
			if(plugin.getConfig().getString("VIPs." + playerName)=="true"){
				plugin.getConfig().setProperty("VIPs." + playerName, 1);
				plugin.getConfig().save();
			}
			else if(plugin.getConfig().getString("VIPs." + playerName)=="false"){
				plugin.getConfig().setProperty("VIPs." + playerName, 0);
				plugin.getConfig().save();
			}
			return plugin.getConfig().getInt("VIPs." + playerName, 0);
		}
	}
	public PermissionHandler getP(){
		return permissions;
	}
}