package logToFile.logtofile;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigHandler {
    private final YamlConfiguration config;
    private final File configFile;

    public ConfigHandler(LogToFile logToFile) {
        File configFolder = logToFile.getDataFolder();
        configFile = new File(configFolder, "config.yml");

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
