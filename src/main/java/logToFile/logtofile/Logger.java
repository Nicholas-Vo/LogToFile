package logToFile.logtofile;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private final SimpleDateFormat niceLookingDate = new SimpleDateFormat("yyyy-MM-dd");
    private final String currentDate = niceLookingDate.format(new Date());
    private final File dataFolder;

    public Logger() {
        dataFolder = Bukkit.getPluginManager().getPlugin("LogToFile").getDataFolder();

        if (!dataFolder.exists()) {
            if (!dataFolder.mkdir()) {
                Bukkit.getLogger().info(ChatColor.RED + "Failed to create a new plugin folder.");
            }
        }
    }

    // TODO - add time in addition to date
    public void info(String message, String fileName) {
        try {
            File theFile = new File(dataFolder, fileName + ".txt");

            if (!theFile.exists()) {
                if (theFile.createNewFile()) {
                    Bukkit.getLogger().info(ChatColor.RED + "Failed to create a new " + fileName + " file.");
                }
            }

            PrintWriter writer = new PrintWriter(new FileWriter(theFile, true));
            writer.println("[" + currentDate + "] " + message);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
