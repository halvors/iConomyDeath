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

package com.halvors.iConomyDeath.listeners;

import java.util.logging.Level;

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;

import com.halvors.iConomyDeath.iConomyDeath;
import com.nijikokun.register.payment.Methods;

public class iConomyDeathServerListener extends ServerListener {
    // Change "MyPlugin" to the name of your MAIN class file.
    // Let's say my plugins MAIN class is: Register.java
    // I would change "MyPlugin" to "Register"
    private final iConomyDeath plugin;
    private Methods methods = null;

    public iConomyDeathServerListener(final iConomyDeath plugin) {
        this.plugin = plugin;
        this.methods = new Methods();
    }

    @Override
    public void onPluginDisable(PluginDisableEvent event) {
        // Check to see if the plugin thats being disabled is the one we are using
        if (methods != null && methods.hasMethod()) {
            Boolean check = methods.checkDisabled(event.getPlugin());

            if (check) {
                plugin.method = null;
                plugin.log(Level.INFO, "Payment method was disabled. No longer accepting payments.");
            }
        }
    }

    @Override
    public void onPluginEnable(PluginEnableEvent event) {
        // Check to see if we need a payment method
        if (!methods.hasMethod()) {
            if (methods.setMethod(event.getPlugin())) {
                // You might want to make this a public variable inside your MAIN class public Method Method = null;
                // then reference it through this.plugin.Method so that way you can use it in the rest of your plugin ;)
            	plugin.method = methods.getMethod();
                plugin.log(Level.INFO, "Payment method found (" + plugin.method.getName() + " version: " + plugin.method.getVersion() + ")");
            }
        }
    }
}