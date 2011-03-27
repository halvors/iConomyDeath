package com.bukkit.halvors.iConomyDeath;

import com.nijiko.coelho.iConomy.iConomy;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.config.Configuration;

/**
* iConomyDeath plugin for Bukkit
*
* @author halvors
*/

public class iConomyDeath extends JavaPlugin
{

	private static PluginListener PluginListener = null;
    private static iConomy iConomy = null;
    private static Server Server = null;

    /**
     * Controller for permissions and security.
     */
    
    public static PermissionHandler Permissions;

    /**
     * Configuration
	*/
    
    public DefaultConfiguration config;

    private final iConomyDeathPlayerListener playerListener = new iConomyDeathPlayerListener(this);
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();

    private void setupPermissions()
	{
        Plugin permissions = this.getServer().getPluginManager().getPlugin("Permissions");

        if (Permissions == null) {
            if (permissions != null) {
                Permissions = ((Permissions)permissions).getHandler();
            } else {
                System.out.println("Permission system not detected, defaulting to OP");
            }
        }
    }
    
    public void onDisable()
	{
        // TODO: Place any custom disable code here

        // NOTE: All registered events are automatically unregistered when a plugin is disabled

        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        //System.out.println("Goodbye world!");
    }

    public void onEnable()
	{
    	// Create files.
        getDataFolder().mkdirs();
        
        try {
        	(new File(getDataFolder(), "config.yml")).createNewFile();
        } catch (IOException ex) {
        }

        // Initilize configuration.
        config = new ConfigurationHandler(getConfiguration());

        // Load Configuration File
        getConfiguration().load();

        // Load Configuration Settings
        config.load();
    	
        // Register our events
        PluginManager pm = getServer().getPluginManager();
        Server = getServer();
        PluginListener = new PluginListener();
        
        pm.registerEvent(Event.Type.PLUGIN_ENABLE, PluginListener, Event.Priority.Monitor, this);
        pm.registerEvent(Event.Type.PLAYER_RESPAWN, playerListener, Event.Priority.Normal, this);

        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
        setupPermissions();
    }
    
    public static boolean hasPermissions(Player p, String s)
	{
        if (Permissions != null) {
            return Permissions.has(p, s);
        } else {
            return p.isOp();
        }
    }
    
    public static Server getBukkitServer()
	{
        return Server;
    }

    public static iConomy getiConomy()
	{
        return iConomy;
    }
    
    public static boolean setiConomy(iConomy plugin)
	{
        if (iConomy == null) {
            iConomy = plugin;
        } else {
            return false;
        }
        return true;
    }
    
    public boolean isDebugging(final Player player)
	{
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value)
	{
        debugees.put(player, value);
    }
}