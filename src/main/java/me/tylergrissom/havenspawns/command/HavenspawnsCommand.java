package me.tylergrissom.havenspawns.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.tylergrissom.havenspawns.HavenspawnsController;
import me.tylergrissom.havenspawns.HavenspawnsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

/**
 * Copyright Tyler Grissom 2018
 */
@AllArgsConstructor
public class HavenspawnsCommand extends CommandBase {

    @Getter
    private HavenspawnsPlugin plugin;

    @Override
    void execute(CommandSender sender, Command command, String[] args) {
        HavenspawnsController controller = getPlugin().getController();
        String[] help = {
                "/havenspawns Help",
                "  /havenspawns reload - Reloads the plugin",
                "  /havenspawns check <player> - Check if a player has reached the cap"
        };

        if (args.length == 0) {
            sender.sendMessage(help);
        } else {
            String sub = args[0];

            if (sub.equalsIgnoreCase("reload")) {
                if (!sender.hasPermission(new Permission("havenspawns.reload"))) {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to do this!");
                } else {
                    try {
                        getPlugin().reloadConfig();

                        sender.sendMessage("Config reloaded.");
                    } catch (Exception exception) {
                        sender.sendMessage(ChatColor.RED + "Could not reload config. Error has been logged to console.");

                        exception.printStackTrace();
                    }
                }
            } else if (sub.equalsIgnoreCase("check")) {
                if (args.length == 1) {
                    sender.sendMessage(help);
                } else {
                    Player target = Bukkit.getPlayer(args[1]);

                    if (target == null) {
                        sender.sendMessage(ChatColor.RED + "That player is not online!");
                    } else {
                        sender.sendMessage("Friendly: " + controller.getNearbyFriendlyEntities(target).size() + "/" + controller.getFriendlyCap() + " (" + controller.getWorldFriendlyEntities(target.getWorld()).size() + " in world)");
                        sender.sendMessage("Hostile: " + controller.getNearbyHostileEntities(target).size() + "/" + controller.getHostileCap() + " (" + controller.getWorldHostileEntities(target.getWorld()).size() + " in world)");
                    }
                }
            } else {
                sender.sendMessage(help);
            }
        }
    }
}
