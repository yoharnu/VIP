package org.yoharnu.VIP;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nijiko.permissions.PermissionHandler;

public class VIPPermissions {
	private static PermissionHandler permissions;
	
	public VIPPermissions(VIP plugin) {
		Plugin theYetiPermissions = plugin.getServer().getPluginManager().getPlugin("Permissions");
		if (theYetiPermissions != null) {
			permissions = ((com.nijikokun.bukkit.Permissions.Permissions) theYetiPermissions).getHandler();
		}
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
}
