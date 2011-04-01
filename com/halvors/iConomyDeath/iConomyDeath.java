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
import org.bukkit.event.Event;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.Plugin;

import com.nijiko.coelho.iConomy.iConomy;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class iConomyDeath extends JavaPlugin {
	public static String name;
	public static String codename;
	public static String version;
	
	private PluginManager pm;
	private PluginDescriptionFile pdfFile;
	private static PluginListener PluginListener = null;
    private static iConomy iConomy = null;
    private static Server Server = null;

    public static PermissionHandler Permissions;
    
    private iConomyDeathPlayerListener playerListener;
    
    private HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
 
    public iConomyDeath() {
    }
    
    public void onEnable() {
    	pm = getServer().getPluginManager();
    	pdfFile = this.getDescription();
    	Server = getServer();
        PluginListener = new PluginListener();
        
        playerListener = new iConomyDeathPlayerListener(this);
    	
        // Load name and version from pdfFile
        name = pdfFile.getName();
        version = pdfFile.getVersion();
        
        // Load Configuration Settings
        Config.getConfig(getDataFolder());

        // Register our events
        pm.registerEvent(Event.Type.PLUGIN_ENABLE, PluginListener, Event.Priority.Monitor, this);
        
        pm.registerEvent(Event.Type.PLAYER_RESPAWN, playerListener, Event.Priority.Normal, this);
        
        System.out.println(name + " version " + version + " is enabled!");
        
        setupPermissions();
    }
    
    public void onDisable() {   	
    	System.out.println(name + " Plugin disabled!");
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