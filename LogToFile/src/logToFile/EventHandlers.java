package logToFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerTakeLecternBookEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.inventory.meta.BookMeta;

public class EventHandlers extends Utils implements Listener {
	public File dataFolder;
	public static File file;
	public static YamlConfiguration yml;

	public EventHandlers(LogToFile logToFile) {
		logToFile.getServer().getPluginManager().registerEvents(this, logToFile);
		dataFolder = LogToFile.plugin.getDataFolder();
		file = new File(dataFolder, "config.yml");
		yml = YamlConfiguration.loadConfiguration(file);
		yml.addDefault("logProjectiles", true);
		yml.addDefault("logCreeperProvoke", true);
		yml.addDefault("logLecternTake", true);
		yml.addDefault("logHorsemounts", true);
		yml.addDefault("logNametagUse", true);
		yml.options().copyDefaults(true);
		saveYml();
	}

	@EventHandler
	public void onProjectile(ProjectileHitEvent e) {
		if (yml.getBoolean("logProjectiles") == true) {
			if (e != null && e.getHitEntity() != null) {
				EntityType hitBlock = e.getHitEntity().getType();
				if (hitBlock == EntityType.ARMOR_STAND || hitBlock == EntityType.ITEM_FRAME) {
					if (e.getEntity().getShooter() instanceof Player) {
						Player shooter = (Player) e.getEntity().getShooter();
						String hit = hitBlock.getName().replace("_", " ");

						logToFile(shooter.getName() + " shot a projectile at an " + hit + " at "
								+ copyableLocation(e.getHitEntity().getLocation()), "projectiles");
					}
				}
			}
		}
	}

	@EventHandler
	public void onCreeperExplosion(EntityExplodeEvent e) {
		if (yml.getBoolean("logCreeperProvoke") == true) {
			if (e.getEntityType() == EntityType.CREEPER) {
				List<Entity> nearby = e.getEntity().getNearbyEntities(10, 5, 10);
				for (Entity entity : nearby) {
					if (entity instanceof Player) {
						logToFile("Creeper exploded at " + copyableLocation(e.getLocation()), "creeperProvoke");
						logToFile("- Nearby: " + entity.getName(), "creeperProvoke");
						logSpace("creeperProvoke");
					}
				}
			}
		}
	}

	@EventHandler
	public void onBookTake(PlayerTakeLecternBookEvent e) {
		if (yml.getBoolean("logLecternTake") == true) {
			Player player = e.getPlayer();
			BookMeta book = (BookMeta) e.getBook().getItemMeta();
			if (book != null) {
				logToFile(player.getName() + " removed \"" + book.getTitle() + "\" by " + book.getAuthor() + " from "
						+ copyableLocation(e.getLectern().getLocation()), "lecterntake");
			}
		}
	}

	@EventHandler // Log getting on a horse
	public void onHorseTake(VehicleEnterEvent e) {
		if (yml.getBoolean("logHorsemounts") == true) {
			if (e.getVehicle() instanceof Horse) {
				logToFile(e.getEntered().getName() + " mounted a horse at "
						+ copyableLocation(e.getVehicle().getLocation()), "horsemounts");
			}
		}
	}

	@EventHandler // Log nametag use
	public void onNameTag(PlayerInteractEntityEvent e) {
		if (yml.getBoolean("logNametagUse") == true) {
			if (e.getPlayer().getItemInHand().getType() == Material.NAME_TAG) {
				Entity entity = e.getRightClicked();
				String tag = e.getPlayer().getItemInHand().getItemMeta().getDisplayName();
				logToFile(e.getPlayer().getName() + " named " + entity.getName() + " at "
						+ copyableLocation(entity.getLocation()) + ": " + tag, "nametaguse");
			}
		}
	}

	public static void saveYml() {
		try {
			yml.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
