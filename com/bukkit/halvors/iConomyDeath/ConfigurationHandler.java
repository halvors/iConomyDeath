package com.bukkit.halvors.iConomyDeath;

import org.bukkit.util.config.Configuration;

/**
* Handles default configuration and loads data.
*
* @author halvors
*/

public class ConfigurationHandler extends DefaultConfiguration
{

    private Configuration config;

    public ConfigurationHandler(Configuration config)
	{
        this.config = config;
    }

    public void load()
	{
		Amount = config.getDouble("Amount", Amount);
    }
}