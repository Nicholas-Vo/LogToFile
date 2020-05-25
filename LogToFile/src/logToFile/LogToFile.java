package logToFile;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class LogToFile extends JavaPlugin implements Listener {
	public static LogToFile plugin;
	public static String PLUGIN_VERSION = "1.0.0";
	Logger log = Bukkit.getLogger();

	public void onEnable() {
		plugin = this;
		this.log.info("Enabling LogToFile " + PLUGIN_VERSION + "...");
		getServer().getPluginManager().registerEvents(this, this);
		new EventHandlers(this);
	}

	public void onDisable() {
		this.log.info("Disabled LogToFile " + PLUGIN_VERSION + ".");
	}
}
