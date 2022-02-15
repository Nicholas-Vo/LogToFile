package logToFile.logtofile;

import org.bukkit.Location;

public class Utils {

    public static String locationToString(Location l) {
        return l.getBlockX() + " " + l.getBlockY() + " " + l.getBlockZ() + " in \"" + l.getWorld().getName() + "\"";
    }

}
