// Package
package org.yoharnu.VIP;

// Imports
import org.bukkit.event.Event;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.config.Configuration;
import org.yoharnu.VIP.VIPPlayerListener;


/**
 * VIP for Bukkit - 
 * 
 * @author yoharnu
 */

// Starts the class
public class VIP extends JavaPlugin {

	// Links the VIPPlayerListener
	private final VIPPlayerListener playerListener = new VIPPlayerListener(this);
	private Configuration configuration;
	public VIPPermissions permissions;

	@Override
	public void onDisable() {
		System.out.println("VIP Disabled");
	}

	@Override
	public void onEnable() {
		
		configuration = getConfiguration();
		permissions = new VIPPermissions(this);
		
		if(permissions!=null && configuration.getBoolean("Use permissions for VIP list", false)){
			configuration.setProperty("Use permissions for VIP list", true);
		}
		else{
			configuration.setProperty("Use permissions for VIP list", false);
		}

		// Create the pluginmanager
		PluginManager pm = getServer().getPluginManager();

		// Create listeners
		pm.registerEvent(Event.Type.PLAYER_LOGIN, playerListener,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener,
				Event.Priority.Normal, this);
		
		getCommand("vip").setExecutor(new VIPCommand(this));

		// Get the information from the yml file.
		PluginDescriptionFile pdfFile = this.getDescription();

		// Print that the plugin has been enabled!
		System.out.println(pdfFile.getName() + " version "
				+ pdfFile.getVersion() + " is enabled!");
		
		if(configuration.getBoolean("enabled", true)){
			configuration.setProperty("enabled", true);
		}
		if(configuration.getBoolean("Kick last logged", true)){
			configuration.setProperty("Kick last logged", true);
		}
		configuration.save();		
	}

	public Configuration getConfig() {
		return configuration;
	}
}
