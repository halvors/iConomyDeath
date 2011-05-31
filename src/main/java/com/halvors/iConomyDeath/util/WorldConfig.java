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

package com.halvors.iConomyDeath.util;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.util.config.Configuration;

import com.halvors.iConomyDeath.iConomyDeath;

/**
 * Holds the configuration for individual worlds
 *
 * @author halvors
 */
public class WorldConfig {
	private final iConomyDeath plugin;
	
	private ConfigManager configManager;
	
    private String worldName;
    private File configFile;

    /* Configuration data start */
    public boolean percentage;
    public double value;
    /* Configuration data end */

    /**
     * Construct the object.
     *
     * @param plugin
     * @param worldName
     */
    public WorldConfig(final iConomyDeath plugin, String worldName) {
    	this.plugin = plugin;
    	this.configManager = plugin.getConfigManager();
    	this.worldName = worldName;
    	
    	File baseFolder = new File(plugin.getDataFolder(), "worlds/");
        configFile = new File(baseFolder, worldName + ".yml");

        configManager.checkConfig(configFile, "config_world.yml");
        
        load();

        plugin.log(Level.INFO, "Loaded configuration for world '" + worldName + "'");
    }

    /**
     * Load the configuration.
     */
    public void load() {	
        Configuration config = new Configuration(configFile);
        config.load();
        
        percentage = config.getBoolean("percentage", percentage);
        value = config.getDouble("value", value);
    }

    /**
     * Get world name.
     * 
     * @return worldName
     */
    public String getWorldName() {
        return this.worldName;
    }
}