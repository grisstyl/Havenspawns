package me.tylergrissom.havenspawns;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright Tyler Grissom 2018
 */
@AllArgsConstructor
public class HavenspawnsController {

    @Getter
    private HavenspawnsPlugin plugin;

    public List<LivingEntity> getNearbyEntities(Player player) {
        World world = player.getWorld();
        List<LivingEntity> entities = new ArrayList<>();

        for (LivingEntity entity : world.getLivingEntities()) {
            if (entity.getLocation().distance(player.getLocation()) <= getRange()) {
                entities.add(entity);
            }
        }

        return entities;
    }

    public List<LivingEntity> getNearbyFriendlyEntities(Player player) {
        List<LivingEntity> nearby = getNearbyEntities(player);
        List<LivingEntity> friendly = new ArrayList<>();

        for (LivingEntity entity : nearby) {
            if (isFriendly(entity)) {
                friendly.add(entity);
            }
        }

        return friendly;
    }

    public List<LivingEntity> getNearbyHostileEntities(Player player) {
        List<LivingEntity> nearby = getNearbyEntities(player);
        List<LivingEntity> hostile = new ArrayList<>();

        for (LivingEntity entity : nearby) {
            if (isHostile(entity)) {
                hostile.add(entity);
            }
        }

        return hostile;
    }

    public List<LivingEntity> getWorldFriendlyEntities(World world) {
        List<LivingEntity> entities = world.getLivingEntities();
        List<LivingEntity> friendly = new ArrayList<>();

        for (LivingEntity entity : entities) {
            if (isFriendly(entity)) {
                friendly.add(entity);
            }
        }

        return friendly;
    }

    public List<LivingEntity> getWorldHostileEntities(World world) {
        List<LivingEntity> entities = world.getLivingEntities();
        List<LivingEntity> hostile = new ArrayList<>();

        for (LivingEntity entity : entities) {
            if (isHostile(entity)) {
                hostile.add(entity);
            }
        }

        return hostile;
    }

    public boolean isFriendly(LivingEntity entity) {
        return entity instanceof Animals;
    }

    public boolean isHostile(LivingEntity entity) {
        return entity instanceof Monster;
    }

    public int getFriendlyCap() {
        return getPlugin().getConfig().getInt("friendly_cap", 25);
    }

    public int getHostileCap() {
        return getPlugin().getConfig().getInt("hostile_cap", 65);
    }

    public int getRange() {
        return getPlugin().getConfig().getInt("range", 128);
    }
}
