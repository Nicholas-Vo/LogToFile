package logToFile.logtofile;

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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class EventHandlers implements Listener {
	private final Logger log;
	private final YamlConfiguration config;

	public EventHandlers(LogToFile logToFile) {
		logToFile.getServer().getPluginManager().registerEvents(this, logToFile);

		config = logToFile.getConfigHandler().getConfig();
		log = logToFile.getPluginLogger();
	}

	@EventHandler
	public void onProjectile(ProjectileHitEvent e) {
		if (!config.getBoolean("logProjectiles")) return;
		if (e.getHitEntity() == null) return;
		if (!(e.getEntity().getShooter() instanceof Player shooter)) return;

		EntityType entity = e.getHitEntity().getType();

		// TODO add a config list to allow for users to input any entity they want to be alerted for
		if (!(entity == EntityType.ITEM_FRAME || entity == EntityType.ARMOR_STAND)) {
			return;
		}

		String hitEntity = entity.name().replace("_", " ");
		String location = Utils.locationToString(e.getHitEntity().getLocation());

		log.info(shooter.getName() + " shot a projectile at an " + hitEntity + " at " + location, "projectiles");
	}

	@EventHandler
	public void onCreeperExplosion(EntityExplodeEvent e) {
		if (!config.getBoolean("logCreeperProvoke")) return;
		if (e.getEntityType() != EntityType.CREEPER) return;

		var nearby = e.getEntity().getNearbyEntities(15, 5, 15)
				.stream().filter(entity -> entity instanceof Player);

		nearby.forEach(entity -> {
			log.info("Creeper exploded at " + Utils.locationToString(e.getLocation()), "creeperProvoke");
			log.info("- Nearby: " + entity.getName() + "\n", "creeperProvoke");
		});
	}

	@EventHandler
	public void onBookTake(PlayerTakeLecternBookEvent e) {
		if (!config.getBoolean("logLecternTake")) return;
		if (e.getBook() == null) return;

		BookMeta book = (BookMeta) e.getBook().getItemMeta();

		String message = book.getTitle() == null ? " an untitled book " : "\"" + book.getTitle() + "\"";
		String location = Utils.locationToString(e.getLectern().getLocation()) + ".";

		String msg = e.getPlayer().getName() + " removed " + message + " by " + book.getAuthor() + " from " + location;
		log.info(msg, "lecterntake");
	}

	/*
    Logs when a player mounts a horse
     */
	@EventHandler
	public void onHorseTake(VehicleEnterEvent e) {
		if (!config.getBoolean("logHorsemounts")) return;
		if (e.getVehicle() instanceof Horse) return;

		String location = Utils.locationToString(e.getVehicle().getLocation());

		log.info(e.getEntered().getName() + " mounted a horse at " + location, "horsemounts");
	}

	/*
    Log name tag use
     */
	@EventHandler
	public void onNameTag(PlayerInteractEntityEvent e) {
		if (!config.getBoolean("logNametagUse")) return;

		ItemStack mainHand = e.getPlayer().getEquipment().getItemInMainHand();
		ItemStack offHand = e.getPlayer().getEquipment().getItemInOffHand();

		if (!(mainHand.getType() == Material.NAME_TAG || offHand.getType() == Material.NAME_TAG)) return;

		Entity entity = e.getRightClicked();
		String name = entity.getCustomName();

		String location = Utils.locationToString(entity.getLocation());
		log.info(e.getPlayer().getName() + " named " + entity.getName() + " at " + location + ": " + name, "nametaguse");
	}
}
