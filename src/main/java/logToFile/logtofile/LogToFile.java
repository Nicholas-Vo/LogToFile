package logToFile.logtofile;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LogToFile extends JavaPlugin {
	private Logger logger;
	private ConfigHandler config;

	@Override
	public void onEnable() {
		Bukkit.getLogger().info("Enabling LogToFile version 2.0.0...");
		logger = new Logger();
		config = new ConfigHandler(this);
		new EventHandlers(this);
	}

	@Override
	public void onDisable() {
		Bukkit.getLogger().info("Disabling LogToFile version 2.0.0...");
	}

	public ConfigHandler getConfigHandler() {
		return config;
	}

	public Logger getPluginLogger() {
		return logger;
	}

}
