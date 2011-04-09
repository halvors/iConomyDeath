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

import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

public class Config {
	public static boolean Percentage; 
	public static double Amount;
	
	public static String MoneyTaken;
	public static String NotEnoughMoney;
	public static String DontHaveAccount;
    
    public static void getConfig(File dataFolder) {
        File configFile = new File(dataFolder + File.separator + "config.yml");
        
        // Setup defaults
        Percentage = true;
        Amount = 10.0;
        
        MoneyTaken = "<amount> was taken from your account because you died.";
        NotEnoughMoney = "Can't take money from your account because you don't have enough money.";
        DontHaveAccount = "Can't take money from your account because you don't have one.";
        
        // write default config file if it doesn't exist
        if (!configFile.exists()) {
            saveConfig(configFile);
        }
        
        loadConfig(configFile);
        saveConfig(configFile);
    }
        
    public static void loadConfig(File configFile) {
        Configuration config = new Configuration(configFile);
        config.load();
       
        Percentage = config.getBoolean("Percentage", Percentage);
        Amount = config.getDouble("Amount", Amount);
        
        ConfigurationNode message = config.getNode("Message");
        MoneyTaken = message.getString("MoneyTaken", MoneyTaken);
        NotEnoughMoney = message.getString("NotEnoughMoney", NotEnoughMoney);
        DontHaveAccount = message.getString("DontHaveAccount", DontHaveAccount);
    }
    
    public static void saveConfig(File configFile) {
        Configuration config = new Configuration(configFile);
        
        config.setProperty("Percentage", Percentage);
        config.setProperty("Amount", Amount);
        
        config.setProperty("Message.MoneyTaken", MoneyTaken);
        config.setProperty("Message.NotEnoughMoney", NotEnoughMoney);
        config.setProperty("Message.DontHaveAccount", DontHaveAccount);
        
        config.save();
    }
}