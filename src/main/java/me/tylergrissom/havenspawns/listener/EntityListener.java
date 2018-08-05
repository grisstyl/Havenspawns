package me.tylergrissom.havenspawns.listener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.tylergrissom.havenspawns.HavenspawnsController;
import me.tylergrissom.havenspawns.HavenspawnsPlugin;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;

/**
 * Copyright Tyler Grissom 2018
 */
@AllArgsConstructor
public class EntityListener implements Listener {

    @Getter
    private HavenspawnsPlugin plugin;

    @EventHandler
    public void onSpawn(final CreatureSpawnEvent event) {
        HavenspawnsController controller = getPlugin().getController();

        if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER)) {
            controller.getExcludedMobs().add(event.getEntity().getUniqueId());

            return;
        }

        World world = event.getEntity().getWorld();
        List<Player> players = world.getPlayers();

        for (Player player : players) {
            if (controller.isFriendly(event.getEntity())) {
                if (controller.getNearbyFriendlyEntities(player).size() >= controller.getFriendlyCap()) {
                    event.setCancelled(true);
                }
            }

            if (controller.isHostile(event.getEntity())) {
                if (controller.getNearbyHostileEntities(player).size() >= controller.getHostileCap()) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDeath(final EntityDeathEvent event) {
        HavenspawnsController controller = getPlugin().getController();

        if (controller.getExcludedMobs().contains(event.getEntity().getUniqueId())) {
            controller.getExcludedMobs().remove(event.getEntity().getUniqueId());
        }
    }
}
