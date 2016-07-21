package net.cubblesfakemythric.playerclasses.cmds;

import net.cubblesfakemythric.playerclasses.Main;
import net.cubblesfakemythric.playerclasses.classes.PlayerClass;
import net.cubblesfakemythric.playerclasses.classes.PlayerClassManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class CmdClass implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("class")){
            if (commandSender.hasPermission("playerclasses.class")){
                if (commandSender instanceof Player){
                    Player player = (Player) commandSender;
                    PermissionUser user = PermissionsEx.getUser(player);

                    if (strings.length < 2){
                        player.sendMessage(Main.TAG + "Invalid parameters. Try §f/class <player> <class>");
                        return false;
                    }

                    PlayerClass playerClass = PlayerClassManager.getPlayerClass(strings[1]);
                    Player target = Bukkit.getPlayerExact(strings[0]);

                    if (target == null) {
                        player.sendMessage(Main.TAG + "The player you have entered is not online.");
                        return false;
                    }

                    if (playerClass == null) {
                        player.sendMessage(Main.TAG + "The class you have entered is not valid. Available classes are listed below.");
                        for (PlayerClass classes : PlayerClassManager.getClasses()) {
                            player.sendMessage(Main.TAG + "  §7- §a§l" + classes.name().toUpperCase());
                        }
                        return false;
                    }

                    for (PlayerClass classes : PlayerClassManager.getClasses()){
                        if (classes.members().contains(target)){
                            classes.members().remove(target);
                        }
                    }

                    playerClass.members().add(target);
                    player.sendMessage(Main.TAG + "You have set the class of §a" + target.getName() + " §eto §a§l" + playerClass.name().toUpperCase());
                    target.sendMessage(Main.TAG + "Your class has been set to §a§l" + playerClass.name().toUpperCase() + " §eby: §6" + player.getName());
                    startPassive(target);

                    user.addPermission("playerclasses." + playerClass.name().toLowerCase());

                }
            }
        }
        return false;
    }

    private void startPassive(final Player player){
        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), new Runnable() {
            @Override
            public void run() {
                PlayerClassManager.getPlayerClass(player).passive(player);
            }
        }, 0, 10);
        PlayerClassManager.passive.put(player, id);
    }

    private void stopPassive(Player player){
        Bukkit.getScheduler().cancelTask(PlayerClassManager.passive.get(player));
    }
}
