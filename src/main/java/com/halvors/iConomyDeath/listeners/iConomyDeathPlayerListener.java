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

package com.halvors.iConomyDeath.listeners;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.halvors.iConomyDeath.iConomyDeath;
import com.halvors.iConomyDeath.util.ConfigManager;
import com.halvors.iConomyDeath.util.WorldConfig;
import com.nijikokun.register.payment.Method;
import com.nijikokun.register.payment.Method.MethodAccount;

public class iConomyDeathPlayerListener extends PlayerListener {
    private final iConomyDeath plugin;
    
    private final ConfigManager configManager;
    
    public iConomyDeathPlayerListener(final iConomyDeath plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
    }
    
    @Override
    public void onPlayerRespawn(PlayerRespawnEvent event) {
    	Player player = event.getPlayer();
    	World world = player.getWorld();
    	WorldConfig worldConfig = configManager.getWorldConfig(world);
    	
    	Method economy = plugin.getEconomy();
    	
    	if (plugin.hasPermissions(player, "iConomyDeath.use")) {
    		String name = player.getName();
    		
	    	if (economy.hasAccount(name)) {
	    		MethodAccount account = economy.getAccount(name);
				double amount = 0;
				
				if (worldConfig.percentage) {
					amount = worldConfig.value * account.balance() / 100;
				} else {
					amount = worldConfig.value;
				}
				
				if (account.hasEnough(amount)) {
					account.subtract(amount);
					
					player.sendMessage(ChatColor.GREEN + economy.format(amount) + " was taken from your account because you died.");
				} else {
					player.sendMessage(ChatColor.RED + "Can't take money from you because you don't have enough money.");
				}
			} else {
				player.sendMessage(ChatColor.RED + "Can't take money from you because you don't have an account.");
			}
		}
    }
}