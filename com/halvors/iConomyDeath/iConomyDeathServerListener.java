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

import java.util.logging.Level;

import org.bukkit.event.server.ServerListener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import com.nijiko.coelho.iConomy.iConomy;

public class iConomyDeathServerListener extends ServerListener {
	private iConomyDeath plugin;
	
    public iConomyDeathServerListener(iConomyDeath instance) {
    	plugin = instance;
	}

    @Override
    public void onPluginEnable(PluginEnableEvent event) {
        if (iConomyDeath.getiConomy() == null) {
            Plugin iConomy = iConomyDeath.getBukkitServer().getPluginManager().getPlugin("iConomy");

            if (iConomy != null) {
                if (iConomy.isEnabled()) {
                	iConomyDeath.setiConomy((iConomy)iConomy);
                	plugin.log(Level.INFO, "Successfully linked with iConomy.");
                }
            }
        }
    }
}