package me.tylergrissom.havenspawns;

import lombok.Getter;
import me.tylergrissom.havenspawns.command.HavenspawnsCommand;
import me.tylergrissom.havenspawns.listener.EntityListener;
import me.tylergrissom.havenspawns.listener.MythicMobsListener;
import me.tylergrissom.havenspawns.task.CleanupTask;
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

    @Getter
    private HavenspawnsController controller;

    @Override
    public void onEnable() {
        plugin = this;
        controller = new HavenspawnsController(this);

        if (!new File(getDataFolder(), "config.yml").exists()) {
            Bukkit.getLogger().log(Level.INFO, "Config not found. Regenerating...");

            saveDefaultConfig();
        }

        if (!new File(getDataFolder(), "messages.yml").exists()) {
            Bukkit.getLogger().log(Level.INFO, "Messages not found. Regenerating...");

            getController().getMessages().saveDefaults();
        }

        if (Bukkit.getPluginManager().getPlugin("MythicMobs") != null) {
            Bukkit.getLogger().log(Level.INFO, "MythicMobs found. Compatibility enabled.");

            Bukkit.getPluginManager().registerEvents(new MythicMobsListener(this), this);
        } else {
            Bukkit.getLogger().log(Level.INFO, "MythicMobs not found. Compatibility will not be enabled.");
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new CleanupTask(this), 0, 20);

        getCommand("havenspawns").setExecutor(new HavenspawnsCommand(this));

        Bukkit.getPluginManager().registerEvents(new EntityListener(this), this);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }
}
