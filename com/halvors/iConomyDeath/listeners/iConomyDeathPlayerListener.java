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
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.halvors.iConomyDeath.iConomyDeath;
import com.halvors.iConomyDeath.util.ConfigManager;
import com.nijikokun.register.payment.Method;
import com.nijikokun.register.payment.Method.MethodAccount;

public class iConomyDeathPlayerListener extends PlayerListener {
    private final iConomyDeath plugin;
    private final ConfigManager configManager;
    private final Method economy;
    
    public iConomyDeathPlayerListener(final iConomyDeath plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.economy = plugin.getEconomy();
    }
    
    @Override
    public void onPlayerRespawn(PlayerRespawnEvent event) {
    	Player player = event.getPlayer();
    	
    	if (plugin.hasPermissions(player, "iConomyDeath.use")) {
    		String name = player.getName();
    		
    		if ((economy != null) && (economy.hasAccount(name))) {
    			MethodAccount account = economy.getAccount(name);
				double amount = 0;
				
				if (configManager.UsePercentage) {
					amount = configManager.Value * account.balance() / 100;
				} else {
					amount = configManager.Value;
				}
				
				if (account.hasEnough(amount)) {
					account.subtract(amount);
					
					player.sendMessage(ChatColor.GREEN + configManager.Money_was_taken_from_your_account.replaceAll("<money>", economy.format(amount)));
				} else {
					player.sendMessage(ChatColor.RED + configManager.You_does_not_have_enough_money);
				}
			} else {
				player.sendMessage(ChatColor.RED + configManager.You_does_not_have_an_account);
			}
		}
    }
}