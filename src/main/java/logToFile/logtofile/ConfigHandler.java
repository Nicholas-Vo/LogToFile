package logToFile.logtofile;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

// TODO: Add /reload command
public class ConfigHandler {
    private final YamlConfiguration config;
    private final File configFile;

    public ConfigHandler(LogToFile logToFile) {
        File configFolder = logToFile.getDataFolder();
        configFile = new File(configFolder, "config.yml");

        if (!configFile.exists()) {
            try {
                if (configFile.createNewFile()) {
                    Bukkit.getLogger().info("Failed to create a new configuration file.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(configFile);
        config.addDefault("logProjectiles", true);
        config.addDefault("logCreeperProvoke", true);
        config.addDefault("logLecternTake", true);
        config.addDefault("logHorseMounts", true);
        config.addDefault("logNametagUse", true);
        config.options().copyDefaults(true);
        saveConfiguration();
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    private void saveConfiguration() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
