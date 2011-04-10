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

package com.halvors.iConomyDeath;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.nijiko.coelho.iConomy.iConomy;
import com.nijiko.coelho.iConomy.system.Account;

public class iConomyDeathPlayerListener extends PlayerListener {
    private final iConomyDeath plugin;

    public iConomyDeathPlayerListener(iConomyDeath instance) {
        plugin = instance;
    }
    
    public void onPlayerRespawn(PlayerRespawnEvent event) {
    	Player player = event.getPlayer();
    	
    	if (iConomyDeath.hasPermissions(player, "iConomyDeath.use")) {
			if (iConomy.getBank().hasAccount(player.getName())) {	
				Account account = iConomy.getBank().getAccount(player.getName());
				double amount;
				
				if (plugin.getConfigManager().UsePercentage) {
					amount = plugin.getConfigManager().Value * account.getBalance() / 100;
				} else {
					amount = plugin.getConfigManager().Value;
				}
				
				if (account.hasEnough(amount)) {
					account.subtract(amount);
					player.sendMessage(ChatColor.GREEN + plugin.getConfigManager().Money_was_taken_from_your_account.replaceAll("<money>", iConomy.getBank().format(amount)));
				} else {
					player.sendMessage(ChatColor.RED + plugin.getConfigManager().You_does_not_have_enough_money);
				}
			} else {
				player.sendMessage(ChatColor.RED + plugin.getConfigManager().You_does_not_have_a_account);
			}
		}
    }
}