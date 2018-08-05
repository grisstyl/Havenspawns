package me.tylergrissom.havenspawns.listener;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobSpawnEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.tylergrissom.havenspawns.HavenspawnsController;
import me.tylergrissom.havenspawns.HavenspawnsPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Copyright Tyler Grissom 2018
 */
@AllArgsConstructor
public class MythicMobsListener implements Listener {
    
    @Getter
    private HavenspawnsPlugin plugin;
    
    @EventHandler
    public void onMMSpawn(final MythicMobSpawnEvent event) {
        HavenspawnsController controller = getPlugin().getController();

        controller.getExcludedMobs().add(event.getEntity().getUniqueId());
    }

    @EventHandler
    public void onMMDeath(final MythicMobDeathEvent event) {
        HavenspawnsController controller = getPlugin().getController();

        controller.getExcludedMobs().remove(event.getEntity().getUniqueId());
    }
}
