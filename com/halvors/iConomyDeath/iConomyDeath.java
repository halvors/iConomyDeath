/*
 * Copyright (C) 2011 Halvor Lyche Strandvoll.
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

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.config.Configuration;

import com.nijiko.coelho.iConomy.iConomy;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class iConomyDeath extends JavaPlugin {
	private static PluginListener PluginListener = null;
    private static iConomy iConomy = null;
    private static Server Server = null;

    public static PermissionHandler Permissions;
    
    private Configuration config;
    private PluginManager pm;
    
    private iConomyDeathPlayerListener playerListener;
    
    private HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
 
    // Config variables
    public double Amount = 64;
    
    public void onEnable() {
    	pm = getServer().getPluginManager();
    	config = getConfiguration();
    	Server = getServer();
        PluginListener = new PluginListener();
        
        playerListener = new iConomyDeathPlayerListener(this);
    	
     	// Create default config if it doesn't exist.
        if (!(new File(getDataFolder(), "config.yml")).exists()) {
        	defaultConfig();
        }
        
        // Load Configuration Settings
        loadConfig();

        // Register our events
        pm.registerEvent(Event.Type.PLUGIN_ENABLE, PluginListener, Event.Priority.Monitor, this);
        
        pm.registerEvent(Event.Type.PLAYER_RESPAWN, playerListener, Event.Priority.Normal, this);

        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
        setupPermissions();
    }
    
    public void onDisable() {   	
    	PluginDescriptionFile pdfFile = this.getDescription();
    	System.out.println(pdfFile.getName() + " Plugin disabled!");
    }
    
    private void loadConfig() {
    	config.load();
    	Amount = config.getDouble("Amount", Amount);
    }

    private void defaultConfig() {
    	config.setProperty("Amount", Amount);
    	config.save();
    }
    	
    private void setupPermissions() {
    	Plugin permissions = this.getServer().getPluginManager().getPlugin("Permissions");

        if (Permissions == null) {
        	if (permissions != null) {
            	Permissions = ((Permissions)permissions).getHandler();
            } else {
            	System.out.println("Permission system not detected, defaulting to OP");
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
    
    public static Server getBukkitServer() {
        return Server;
    }

    public static iConomy getiConomy() {
        return iConomy;
    }
    
    public static boolean setiConomy(iConomy plugin) {
        if (iConomy == null) {
            iConomy = plugin;
        } else {
            return false;
        }
        return true;
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
}