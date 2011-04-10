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
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

import com.halvors.iConomyDeath.iConomyDeath;

public class ConfigManager {
	private iConomyDeath plugin;
	
	private File configFile;
	
	// General
	public boolean UsePercentage; 
	public double Value;
	
	// Messages
	public String Money_was_taken_from_your_account;
	public String You_does_not_have_enough_money;
	public String You_does_not_have_a_account;
	
	public ConfigManager(iConomyDeath instance) {
		this.plugin = instance;
		
	    this.configFile = new File(plugin.getDataFolder(), "config.yml");
	}
	
	// Load configuration
	public void load() {
		checkConfig();
		
        Configuration config = new Configuration(configFile);
        config.load();
        
        loadGlobals(config);
    }

	// Save configuration
	public void save() {
		Configuration config = new Configuration(configFile);
		
		saveGlobals(config);
		
		config.save();
	}
	
	// Reload configuration
	public void reload() {
        load();
    }
	
	private void checkConfig() {
        if (!configFile.exists()) {
            try {
                configFile.getParentFile().mkdir();
                configFile.createNewFile();
                OutputStream output = new FileOutputStream(configFile, false);
                InputStream input = ConfigManager.class.getResourceAsStream("config.yml");
                byte[] buf = new byte[8192];
                while (true) {
                    int length = input.read(buf);
                    if (length < 0) {
                        break;
                    }
                    output.write(buf, 0, length);
                }
                input.close();
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	
	private void loadGlobals(Configuration config) {
		// General
		this.UsePercentage = config.getBoolean("UsePercentage", UsePercentage);
		this.Value = config.getDouble("Value", Value);

        // Messages
		ConfigurationNode messages = config.getNode("Messages");
		this.Money_was_taken_from_your_account = messages.getString("Money_was_taken_from_your_account", this.Money_was_taken_from_your_account);
		
		this.You_does_not_have_enough_money = messages.getString("You_does_not_have_enough_money", this.You_does_not_have_enough_money);
		
		this.You_does_not_have_a_account = messages.getString("You_does_not_have_a_account", this.You_does_not_have_a_account);
	}

    private void saveGlobals(Configuration config) {
    	// General
    	config.setProperty("UsePercentage", this.UsePercentage);
    	config.setProperty("Value", this.Value);

        // Messages
        config.setProperty("Messages.Money_was_taken_from_your_account", this.Money_was_taken_from_your_account);
        
        config.setProperty("Messages.You_does_not_have_enough_money", this.You_does_not_have_enough_money);
        
        config.setProperty("Messages.You_does_not_have_a_account", this.You_does_not_have_a_account);
        
        config.save();
    }
}