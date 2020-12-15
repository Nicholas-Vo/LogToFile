package logToFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class LogToFile extends JavaPlugin implements Listener {
	public static LogToFile plugin;
	public static String PLUGIN_VERSION = "1.0.5";
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
	
	// Location String without commas
	public String copyableLocation(Location l) {
		return l.getBlockX() + " " + l.getBlockY() + " " + l.getBlockZ() + " in \"" + l.getWorld().getName() + "\"";
	}

	public String locationToString(Player p) {
		Location l = p.getLocation();
		return "x" + l.getBlockX() + ", y" + l.getBlockY() + ", z" + l.getBlockZ();
	}

	private Date date = new Date();
	private SimpleDateFormat niceLookingDate = new SimpleDateFormat("yyyy-MM-dd");
	private String format = niceLookingDate.format(date);

	public void logSpace(String fileName) {
		try {
			File dataFolder = (Bukkit.getPluginManager().getPlugin("LogToFile")).getDataFolder();
			if (!dataFolder.exists())
				dataFolder.mkdir();	
			File saveTo = new File((Bukkit.getPluginManager().getPlugin("LogToFile")).getDataFolder(),
					fileName + ".txt");
			if (!saveTo.exists())
				saveTo.createNewFile();
			FileWriter fw = new FileWriter(saveTo, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println("");
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void logToFile(String message, String fileName) {
		try {
			File dataFolder = (Bukkit.getPluginManager().getPlugin("LogToFile")).getDataFolder();
			if (!dataFolder.exists())
				dataFolder.mkdir();
			File saveTo = new File((Bukkit.getPluginManager().getPlugin("LogToFile")).getDataFolder(),
					fileName + ".txt");
			if (!saveTo.exists())
				saveTo.createNewFile();
			FileWriter fw = new FileWriter(saveTo, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println("[" + format.toString() + "] " + message);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
