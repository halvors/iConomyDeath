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

import com.halvors.iConomyDeath.iConomyDeath;

public class ConfigManager {
	private final iConomyDeath plugin;
	
	private File configFile;
	
	// General
	public boolean UsePercentage; 
	public double Value;
	
	// Messages
	public String Money_was_taken_from_your_account;
	public String You_does_not_have_enough_money;
	public String You_does_not_have_an_account;
	
	public ConfigManager(final iConomyDeath plugin) {
		this.plugin = plugin;
		
	    configFile = new File(plugin.getDataFolder(), "config.yml");
	}
	
	// Load configuration
	public void load() {
		checkConfig(configFile);
		
        Configuration config = new Configuration(configFile);
        config.load();
        
        // General
		UsePercentage = config.getBoolean("UsePercentage", UsePercentage);
		Value = config.getDouble("Value", Value);
		
        // Messages
		Money_was_taken_from_your_account = config.getString("Messages.Money_was_taken_from_your_account", Money_was_taken_from_your_account);
		You_does_not_have_enough_money = config.getString("Messages.You_does_not_have_enough_money", You_does_not_have_enough_money);
		You_does_not_have_an_account = config.getString("Messages.You_does_not_have_an_account", You_does_not_have_an_account);
    }
	
	// Save configuration
	public void save() {
		Configuration config = new Configuration(configFile);
		
		// General
    	config.setProperty("UsePercentage", UsePercentage);
    	config.setProperty("Value", Value);
    	
        // Messages
        config.setProperty("Messages.Money_was_taken_from_your_account", Money_was_taken_from_your_account);
        config.setProperty("Messages.You_does_not_have_enough_money", You_does_not_have_enough_money);
        config.setProperty("Messages.You_does_not_have_an_account", You_does_not_have_an_account);
		
		config.save();
	}
	
	// Reload configuration
	public void reload() {
        load();
    }
	
	private void checkConfig(File config) {
        if (!config.exists()) {
            try {
                config.getParentFile().mkdir();
                config.createNewFile();
                OutputStream output = new FileOutputStream(config, false);
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
}