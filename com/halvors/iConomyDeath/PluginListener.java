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

package com.bukkit.halvors.iConomyDeath;

import org.bukkit.event.server.PluginEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;

import com.nijiko.coelho.iConomy.iConomy;

public class PluginListener extends ServerListener {
    public PluginListener() {
	}

    @Override
    public void onPluginEnabled(PluginEvent event) {
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