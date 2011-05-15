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

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;

import com.iConomy.iConomy;

public class iConomyDeathServerListener extends ServerListener {
    private final iConomyDeath plugin;

    public iConomyDeathServerListener(iConomyDeath plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPluginDisable(PluginDisableEvent event) {
        if (plugin.iConomy != null) {
            if (event.getPlugin().getDescription().getName().equals("iConomy")) {
                plugin.iConomy = null;
                plugin.log(Level.INFO, "un-hooked from iConomy.");
            }
        }
    }

    @Override
    public void onPluginEnable(PluginEnableEvent event) {
        if (plugin.iConomy == null) {
            Plugin iConomy = plugin.getServer().getPluginManager().getPlugin("iConomy");

            if (iConomy != null) {
                if (iConomy.isEnabled()) {
                    plugin.iConomy = (iConomy)iConomy;
                    plugin.log(Level.INFO, "hooked into iConomy.");
                }
            }
        }
    }
}