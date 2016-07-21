package net.cubblesfakemythric.playerclasses.cmds;

import net.cubblesfakemythric.playerclasses.Main;
import net.cubblesfakemythric.playerclasses.classes.PlayerClass;
import net.cubblesfakemythric.playerclasses.classes.PlayerClassManager;
import net.cubblesfakemythric.playerclasses.listeners.Connection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class CmdChooseClass implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("chooseclass")){
            if (commandSender instanceof Player){
                Player player = (Player) commandSender;
                PermissionUser user = PermissionsEx.getUser(player);

                if (Connection.newPlayers.contains(player) || player.getName().equalsIgnoreCase("CodenameFlip")){
                    if (strings.length < 1){
                        player.sendMessage(Main.TAG + "Invalid parameters. Try §f/class <class>");
                        return false;
                    }

                    PlayerClass playerClass = PlayerClassManager.getPlayerClass(strings[0]);

                    if (playerClass == null) {
                        player.sendMessage(Main.TAG + "The class you have entered is not valid. Available classes are listed below.");
                        for (PlayerClass classes : PlayerClassManager.getClasses()) {
                            player.sendMessage(Main.TAG + "  §7- §a§l" + classes.name().toUpperCase());
                        }
                        return false;
                    }

                    for (PlayerClass classes : PlayerClassManager.getClasses()){
                        if (classes.members().contains(player)){
                            classes.members().remove(player);
                            user.removePermission("playerclasses." + classes.name().toLowerCase());
                        }
                    }

                    playerClass.members().add(player);
                    player.sendMessage(" ");
                    player.sendMessage(Main.TAG + "You have chose the class §a§l" + playerClass.name().toUpperCase() + "!");
                    player.sendMessage(" ");

                    Connection.newPlayers.remove(player);
                    player.teleport(new Location(Bukkit.getWorld("jewjew"), 72, 70, 560));

                    user.addPermission("playerclasses." + playerClass.name().toLowerCase());

                    for (Player all : playerClass.members()){
                        all.sendMessage(Main.TAG + " §l[" + playerClass.name().toUpperCase() + "]: §6" + player.getName() + " has joined the team!");
                    }

                    startPassive(player);
                } else {
                    player.sendMessage(Main.TAG + "You are not a new player! You cannot reselect your class, you were warned...");
                }
            }
        }
        return false;
    }

    private void startPassive(final Player player){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), new Runnable() {
            @Override
            public void run() {
                PlayerClassManager.getPlayerClass(player).passive(player);
            }
        }, 0, 10);
    }
}
