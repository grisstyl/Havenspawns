package me.tylergrissom.havenspawns;

import lombok.Getter;
import me.tylergrissom.havenspawns.command.HavenspawnsCommand;
import me.tylergrissom.havenspawns.listener.EntityListener;
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

        getCommand("havenspawns").setExecutor(new HavenspawnsCommand(this));

        Bukkit.getPluginManager().registerEvents(new EntityListener(this), this);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }
}
