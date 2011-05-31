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

import java.util.logging.Level;

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;

import com.halvors.iConomyDeath.iConomyDeath;
import com.nijikokun.register.payment.Method;
import com.nijikokun.register.payment.Methods;

public class iConomyDeathServerListener extends ServerListener {
    private final iConomyDeath plugin;
    
    private Methods methods = null;
    
    public iConomyDeathServerListener(final iConomyDeath plugin) {
        this.plugin = plugin;
        this.methods = new Methods();
    }

    @Override
    public void onPluginDisable(PluginDisableEvent event) {
        if (methods != null && methods.hasMethod()) {
            Boolean check = methods.checkDisabled(event.getPlugin());

            if (check) {
                plugin.economy = null;
                plugin.log(Level.INFO, "Payment method was disabled. No longer accepting payments.");
            }
        }
    }

    @Override
    public void onPluginEnable(PluginEnableEvent event) {
        if (!methods.hasMethod()) {
            if (methods.setMethod(event.getPlugin())) {
            	plugin.economy = methods.getMethod();
                plugin.log(Level.INFO, "Payment method found (" + plugin.economy.getName() + " version: " + plugin.economy.getVersion() + ")");
            }
        }
    }
}