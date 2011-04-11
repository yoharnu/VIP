// Package
package org.yoharnu.VIP;

// Imports

import java.util.LinkedList;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class VIPPlayerListener extends PlayerListener {

	public static VIP plugin;
	public String callingPlayerName;

	public VIPPlayerListener(VIP instance) {
		plugin = instance;
	}

	@Override
	public void onPlayerLogin(PlayerLoginEvent event){
		if(!plugin.getConfig().getBoolean("enabled", true)){
			return;
		}
		if(plugin.getServer().getOnlinePlayers().length<plugin.getServer().getMaxPlayers()){
			//event.setResult(Result.ALLOWED);
			return;
		}
		Player player = event.getPlayer();
		if(plugin.permissions.isVIP(player.getName())==0){
			event.setResult(Result.KICK_FULL);
			return;
		}
		Player online[] = plugin.getServer().getOnlinePlayers();
		LinkedList<Player> options = new LinkedList<Player>();
		int it=0;
		int p = 0;
		while(it==0 && p<plugin.permissions.isVIP(player.getName())){
			for(int i=0; i<online.length; i++){
				if(plugin.permissions.isVIP(online[i].getName())==p){
					options.add(online[i]);
					it++;
				}
			}
			p++;
		}
		if(it==0){
			event.setResult(Result.KICK_FULL);
			return;
		}
		if(it==1){
			options.get(0).kickPlayer("Server is full. A VIP signed in.");
			event.setResult(Result.ALLOWED);
			return;
		}
		int kick=0;
		for(int i=1; i<options.size(); i++){
			int iLoginTime =  plugin.getConfig().getInt("DO NOT EDIT -- Login times." + options.get(i).getName(), 0);
			int kickLoginTime = plugin.getConfig().getInt("DO NOT EDIT -- Login times." + options.get(kick).getName(), 0);
			if(iLoginTime>kickLoginTime && plugin.getConfig().getBoolean("Kick last logged", true)){
				kick=i;
			}
			else if(iLoginTime<kickLoginTime && !plugin.getConfig().getBoolean("Kick last logged", true)){
				kick=i;
			}
		}
		options.get(kick).kickPlayer("Server is full. A VIP signed in.");
		event.setResult(Result.ALLOWED);
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
		if(plugin.getConfig().getString("DO NOT EDIT -- Login times")=="{}"){
			plugin.getConfig().removeProperty("DO NOT EDIT -- Login times");
		}
		plugin.getConfig().save();
		return;
	}
}