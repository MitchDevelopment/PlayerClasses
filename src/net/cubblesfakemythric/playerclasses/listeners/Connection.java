package net.cubblesfakemythric.playerclasses.listeners;

import net.cubblesfakemythric.playerclasses.Main;
import net.cubblesfakemythric.playerclasses.classes.PlayerClass;
import net.cubblesfakemythric.playerclasses.classes.PlayerClassManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;
import java.util.List;

public class Connection implements Listener {

    public static List<Player> newPlayers = new ArrayList<>();

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        if (newPlayers.contains(player)){
            newPlayers.remove(player);
        } else {
            for (PlayerClass all : PlayerClassManager.getClasses()){
                if (all.members().contains(player)){
                    stopPassive(player);
                    all.members().remove(player);
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        final Player player = event.getPlayer();

        if (!player.hasPermission("playerclasses.dwarves") && !player.hasPermission("playerclasses.warriors") && !player.hasPermission("playerclasses.monsters")){
            player.teleport(new Location(Bukkit.getWorld("jewjew"), 69, 41, 563));
            player.sendMessage(Main.TAG + "You have not selected a class this map!");
            player.sendMessage(Main.TAG + "Select an NPC to choose your class.");
            player.sendMessage(Main.TAG + "§c§lWARNING: §cYou cannot choose your player class after selected :(");
            newPlayers.add(player);
        }

        for (PlayerClass all : PlayerClassManager.getClasses()){
            if (!all.members().contains(player)){
                if (!player.isOp()){
                    for (PlayerClass classes : PlayerClassManager.getClasses()) {
                        PermissionUser user = PermissionsEx.getUser(player);
                        if (user.has("playerclasses." + classes.name().toLowerCase())) {
                            classes.members().add(player);
                        }
                    }
                } else {
                    player.sendMessage(" ");
                    player.sendMessage(Main.TAG + "Since you are op, you must reselect your class again!");
                    player.sendMessage(Main.TAG + "Use §f/class <name> <class>");
                    player.sendMessage(" ");
                }
            } else {
                startPassive(player);
            }
        }
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
        PlayerClassManager.passive.remove(player);
    }

}
