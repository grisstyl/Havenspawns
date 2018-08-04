package me.tylergrissom.havenspawns;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

/**
 * Copyright Tyler Grissom 2018
 */
public class HavenspawnsPlugin extends JavaPlugin {

    @Getter
    private HavenspawnsPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        if (!new File(getDataFolder(), "config.yml").exists()) {
            Bukkit.getLogger().log(Level.INFO, "Config not found. Regenerating...");

            saveDefaultConfig();
        }
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }
}
