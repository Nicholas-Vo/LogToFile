package logToFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Utils {
	
	public static String copyableLocation(Location l) {
		return l.getBlockX() + " " + l.getBlockY() + " " + l.getBlockZ() + " in \"" + l.getWorld().getName() + "\"";
	}

	public static String locationToString(Player p) {
		Location l = p.getLocation();
		return "x" + l.getBlockX() + ", y" + l.getBlockY() + ", z" + l.getBlockZ();
	}
	
	static Date date = new Date();
	static SimpleDateFormat niceLookingDate = new SimpleDateFormat("yyyy-MM-dd");
	static String format = niceLookingDate.format(date);
	
	public static void logToFile(String message, String fileName) {
		try {
			File dataFolder = (Bukkit.getPluginManager().getPlugin("LogToFile")).getDataFolder();
			if (!dataFolder.exists()) {
				dataFolder.mkdir();
			}
			File saveTo = new File((Bukkit.getPluginManager().getPlugin("LogToFile")).getDataFolder(),
					fileName + ".txt");
			if (!saveTo.exists()) {
				saveTo.createNewFile();
			}
			FileWriter fw = new FileWriter(saveTo, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println("[" + format.toString() + "] " + message);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void logSpace(String fileName) {
		try {
			File dataFolder = (Bukkit.getPluginManager().getPlugin("LogToFile")).getDataFolder();
			if (!dataFolder.exists()) {
				dataFolder.mkdir();
			}
			File saveTo = new File((Bukkit.getPluginManager().getPlugin("LogToFile")).getDataFolder(),
					fileName + ".txt");
			if (!saveTo.exists()) {
				saveTo.createNewFile();
			}
			FileWriter fw = new FileWriter(saveTo, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println("");
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
