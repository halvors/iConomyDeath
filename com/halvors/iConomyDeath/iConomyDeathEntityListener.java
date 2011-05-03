package com.halvors.iConomyDeath;

import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.inventory.ItemStack;

public class iConomyDeathEntityListener extends EntityListener {
	private final iConomyDeath plugin;
	
	private HashMap<String, ItemStack> itemStacks = new HashMap<String, ItemStack>();
	
	public iConomyDeathEntityListener(iConomyDeath plugin) {
		this.plugin = plugin;
	}
	
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if (!event.isCancelled()) {
			Entity entity = event.getEntity();
			
			if (entity instanceof Player) {
				Player player = (Player)entity;
			}
		}
	}
	
	public void onEntityDeath(EntityDeathEvent event) {
		Entity entity = event.getEntity();
		
		if (entity instanceof Player) {
			Player player = (Player)entity;
		}
	}
}
