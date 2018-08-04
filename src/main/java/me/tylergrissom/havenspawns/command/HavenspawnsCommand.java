package me.tylergrissom.havenspawns.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.tylergrissom.havenspawns.HavenspawnsController;
import me.tylergrissom.havenspawns.HavenspawnsPlugin;
import me.tylergrissom.havenspawns.config.MessagesYaml;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.HashMap;
import java.util.Map;

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
        MessagesYaml messages = controller.getMessages();
        String[] help = messages.getMessages("command.help");

        if (args.length == 0) {
            sender.sendMessage(help);
        } else {
            String sub = args[0];

            if (sub.equalsIgnoreCase("reload")) {
                if (!sender.hasPermission(new Permission("havenspawns.reload"))) {
                    sender.sendMessage(messages.getMessage("error.no_permission"));
                } else {
                    try {
                        getPlugin().reloadConfig();
                        getPlugin().getController().getMessages().reload();

                        sender.sendMessage(messages.getMessage("command.reloaded"));
                    } catch (Exception exception) {
                        sender.sendMessage(messages.getMessage("command.failed_reload"));

                        exception.printStackTrace();
                    }
                }
            } else if (sub.equalsIgnoreCase("check")) {
                if (args.length == 1) {
                    sender.sendMessage(help);
                } else {
                    Player target = Bukkit.getPlayer(args[1]);

                    if (target == null) {
                        sender.sendMessage(messages.getMessage("error.target_offline"));
                    } else {
                        Map<String, String> replaceSet = new HashMap<>();

                        replaceSet.put("nearby_friendly", String.valueOf(controller.getNearbyFriendlyEntities(target).size()));
                        replaceSet.put("friendly_cap", String.valueOf(controller.getFriendlyCap()));
                        replaceSet.put("world_friendly", String.valueOf(controller.getWorldFriendlyEntities(target.getWorld()).size()));
                        replaceSet.put("nearby_hostile", String.valueOf(controller.getNearbyHostileEntities(target).size()));
                        replaceSet.put("hostile_cap", String.valueOf(controller.getHostileCap()));
                        replaceSet.put("world_hostile", String.valueOf(controller.getWorldHostileEntities(target.getWorld()).size()));

                        sender.sendMessage(messages.getMessages("command.check", replaceSet));
                    }
                }
            } else {
                sender.sendMessage(help);
            }
        }
    }
}
