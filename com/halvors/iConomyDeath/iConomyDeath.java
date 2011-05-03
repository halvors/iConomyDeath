/*
 * Copyright (C) 2011 halvors <halvors@skymiastudios.com>.
 *
 * This file is part of iConomyDeath.
 *
 * iConomyDeath is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * iConomyDeath is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with iConomyDeath.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.halvors.iConomyDeath;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.halvors.iConomyDeath.util.ConfigManager;
import com.iConomy.iConomy;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class iConomyDeath extends JavaPlugin {
	public static String name;
	public static String version;
	
	private final Logger log = Logger.getLogger("Minecraft");
	private final PluginManager pm = this.getServer().getPluginManager();
	private final PluginDescriptionFile pdfFile = this.getDescription();
	
	public iConomy iConomy = null;

    public static PermissionHandler Permissions;
    
    private final ConfigManager configManager = new ConfigManager(this);
    
    private final iConomyDeathPlayerListener playerListener = new iConomyDeathPlayerListener(this);
    private final iConomyDeathServerListener serverListener = new iConomyDeathServerListener(this);
    
    private HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    
    public iConomyDeath() {
    	
    }
    
    public void onEnable() {
        // Load name and version from pdfFile
        name = pdfFile.getName();
        version = pdfFile.getVersion();
        
        // Load Configuration
        try {
            configManager.load();
        } catch (Exception e) {
            e.printStackTrace();
            log(Level.INFO, "Error encountered while loading data. Disabling " + name);
            pm.disablePlugin(this);
            
            return;
        }

        // Register our events
        pm.registerEvent(Event.Type.PLUGIN_ENABLE, serverListener, Event.Priority.Monitor, this);
        pm.registerEvent(Event.Type.PLUGIN_DISABLE, serverListener, Event.Priority.Monitor, this);
        
        pm.registerEvent(Event.Type.PLAYER_RESPAWN, playerListener, Event.Priority.Normal, this);
        
        log(Level.INFO, "version " + version + " is enabled!");
        
        setupPermissions();
    }
    
    public void onDisable() {
    	log(Level.INFO, "Plugin disabled!");
    }
    
    private void setupPermissions() {
    	Plugin permissions = this.getServer().getPluginManager().getPlugin("Permissions");

        if (Permissions == null) {
        	if (permissions != null) {
            	Permissions = ((Permissions)permissions).getHandler();
            } else {
            	log(Level.INFO, "Permission system not detected, defaulting to OP");
            }
        }
    }

    public static boolean hasPermissions(Player p, String s) {
        if (Permissions != null) {
            return Permissions.has(p, s);
        } else {
            return p.isOp();
        }
    }
    
    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }
    
    public void log(Level level, String msg) {
        this.log.log(level, "[" + name + "] " + msg);
    }
    
    public ConfigManager getConfigManager() {
    	return configManager;
    }
}