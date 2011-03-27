package com.bukkit.halvors.iConomyDeath;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.nijiko.coelho.iConomy.iConomy;
import com.nijiko.coelho.iConomy.system.Bank;
import com.nijiko.coelho.iConomy.system.Account;

/**
* iConomyDeath plugin for Bukkit
*
* @author halvors
*/

public class iConomyDeathPlayerListener extends PlayerListener
{
    private final iConomyDeath plugin;

    public iConomyDeathPlayerListener(iConomyDeath instance)
	{
        plugin = instance;
    }
    
    public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		Player player = event.getPlayer();
    
		if (iConomy.getBank().hasAccount(player.getName())) {
			if (plugin.hasPermissions(player, "iConomyDeath.use")) {
				Account account = iConomy.getBank().getAccount(player.getName());
				double amount = plugin.config.Amount;
		
				account.subtract(amount);
				account.save();
    
				player.sendMessage("[iConomyDeath] " + iConomy.getBank().format(amount) + " was removed from your iConomy account because you was killed!");
			}
		}
    }
}