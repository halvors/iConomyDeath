package com.halvors.iConomyDeath;

import java.io.File;

import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

public class Config {
    
	public static double Amount;
	
	public static String MoneyTaken;
	public static String NotEnoughMoney;
	public static String DontHaveAccount;
    
    public static void getConfig(File dataFolder) {
        File configFile = new File(dataFolder + File.separator + "config.yml");
        
        // Setup defaults
        Amount = 64.0;
        
        MoneyTaken = "<amount> was taken from your account because you died.";
        NotEnoughMoney = "Can't take money from your account because you don't have enough money.";
        DontHaveAccount = "Can't take money from your account because you don't have one.";
        
        // write default config file if it doesn't exist
        if ( !configFile.exists() ) {
            saveConfig(configFile);
        }
        
        loadConfig(configFile);
        saveConfig(configFile);
    }
        
    public static void loadConfig(File configFile) {
        Configuration config = new Configuration(configFile);
        config.load();
       
        Amount = config.getDouble("Amount", Amount);
        
        ConfigurationNode message = config.getNode("Message");
        MoneyTaken = message.getString("MoneyTaken", MoneyTaken);
        NotEnoughMoney = message.getString("NotEnoughMoney", NotEnoughMoney);
        DontHaveAccount = message.getString("DontHaveAccount", DontHaveAccount);
    }
    
    public static void saveConfig(File configFile) {
        Configuration config = new Configuration(configFile);
        
        config.setProperty("Amount", Amount);
        
        config.setProperty("Message.MoneyTaken", MoneyTaken);
        config.setProperty("Message.NotEnoughMoney", NotEnoughMoney);
        config.setProperty("Message.DontHaveAccount", DontHaveAccount);
        
        config.save();
    }
}