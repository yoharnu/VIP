// Package
package org.yoharnu.VIP;

// Imports

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class VIPPlayerListener extends PlayerListener {

	public static VIP plugin;
	public String callingPlayerName;

	public VIPPlayerListener(VIP instance) {
		plugin = instance;
	}

	@SuppressWarnings("null")
	@Override
	public void onPlayerLogin(PlayerLoginEvent event){
		if(plugin.getServer().getOnlinePlayers().length<plugin.getServer().getMaxPlayers()){
			return;
		}
		Player player = event.getPlayer();
		if(!isVIP(player)){
			return;
		}
		Player online[] = plugin.getServer().getOnlinePlayers();
		Player options[] = null;
		int it=0;
		for(int i=0; i<online.length; i++){
			if(!isVIP(online[i])){
				options[it] = online[i];
				it++;
			}
		}
		if(it==0){
			player.kickPlayer("Server is full (all VIPs)");
			return;
		}
		if(it==1){
			options[it-1].kickPlayer("Server is full. A VIP signed in.");
			return;
		}
		int kick=0;
		for(int i=1; i<options.length; i++){
			int iLoginTime =  plugin.getConfig().getInt("DO NOT EDIT -- Login times." + options[i].getName(), 0);
			int kickLoginTime = plugin.getConfig().getInt("DO NOT EDIT -- Login times." + options[kick].getName(), 0);
			if(iLoginTime>kickLoginTime){
				kick=i;
			}
		}
		options[kick].kickPlayer("Server is full. A VIP signed in.");
		return;
	}

	@Override
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		plugin.getConfig().setProperty("DO NOT EDIT -- Login times." + player.getName(), player.getWorld().getFullTime());
		plugin.getConfig().save();
		return;
	}

	@Override
	public void onPlayerQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		plugin.getConfig().removeProperty("DO NOT EDIT -- Login times." + player.getName());
		plugin.getConfig().save();
		return;
	}

	private boolean isVIP(Player player) {
		return plugin.getConfig().getBoolean("VIPs." + player.getName(), false);
	}
}