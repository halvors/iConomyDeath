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
				
				if (Config.Percentage) {
					amount = Config.Amount * account.getBalance() / 100;
				} else {
					amount = Config.Amount;
				}
				
				if (account.hasEnough(amount)) {
					account.subtract(amount);
					player.sendMessage(Config.MoneyTaken.replaceAll("<amount>", iConomy.getBank().format(amount)));
				} else {
					player.sendMessage(Config.NotEnoughMoney);
				}
			} else {
				player.sendMessage(Config.DontHaveAccount);
			}
		}
    }
}