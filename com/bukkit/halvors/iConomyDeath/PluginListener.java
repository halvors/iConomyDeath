package com.bukkit.halvors.iConomyDeath;

import org.bukkit.event.server.PluginEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;

import com.nijiko.coelho.iConomy.iConomy;

/**
 * Checks for plugins whenever one is enabled
 */

public class PluginListener extends ServerListener
{
    public PluginListener() 
	{
	}

    @Override
    public void onPluginEnabled(PluginEvent event)
	{
        if(iConomyDeath.getiConomy() == null) {
            Plugin iConomy = iConomyDeath.getBukkitServer().getPluginManager().getPlugin("iConomy");

            if (iConomy != null) {
                if(iConomy.isEnabled()) {
                	iConomyDeath.setiConomy((iConomy)iConomy);
                    System.out.println("[iConomyDeath] Successfully linked with iConomy.");
                }
            }
        }
    }
}