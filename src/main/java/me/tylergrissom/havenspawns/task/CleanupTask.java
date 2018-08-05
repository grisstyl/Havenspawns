package me.tylergrissom.havenspawns.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.tylergrissom.havenspawns.HavenspawnsController;
import me.tylergrissom.havenspawns.HavenspawnsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

/**
 * Copyright Tyler Grissom 2018
 */
@AllArgsConstructor
public class CleanupTask extends BukkitRunnable {

    @Getter
    private HavenspawnsPlugin plugin;

    @Override
    public void run() {
        HavenspawnsController controller = getPlugin().getController();

        for (UUID uuid : controller.getExcludedMobs()) {
            Entity entity = Bukkit.getEntity(uuid);

            if (entity == null || Bukkit.getEntity(uuid).isDead()) {
                controller.getExcludedMobs().remove(uuid);
            }
        }
    }
}
