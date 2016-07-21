package net.cubblesfakemythric.playerclasses.listeners;

import net.cubblesfakemythric.playerclasses.Main;
import net.cubblesfakemythric.playerclasses.classes.PlayerClass;
import net.cubblesfakemythric.playerclasses.classes.PlayerClassManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();

        if (!Connection.newPlayers.contains(player)){
            if (event.getMessage().startsWith("@")){
                if (PlayerClassManager.getPlayerClass(player) != null){
                    PlayerClass playerClass = PlayerClassManager.getPlayerClass(player);

                    for (Player all : playerClass.members()){
                        event.setCancelled(true);
                        all.sendMessage(Main.TAG + "§e§l[" + playerClass.name().toUpperCase() + "] §6" + player.getName() + ": §7" + event.getMessage().replaceFirst("@", ""));
                    }
                }
            } else {
                if (!event.isCancelled()){
                    if (PlayerClassManager.getPlayerClass(player) != null){
                        PlayerClass playerClass = PlayerClassManager.getPlayerClass(player);

                        event.setCancelled(true);
                        Bukkit.broadcastMessage("§e[" + playerClass.name() + "] " + player.getDisplayName() + " §8» §7" + event.getMessage());
                    } else {
                        event.setCancelled(true);
                        Bukkit.broadcastMessage("§c• " + player.getDisplayName() + " §8» §7" + event.getMessage());
                    }
                }

            }
        } else {
            event.setCancelled(true);
            player.sendMessage(Main.TAG + "§cYou must select a class before you can start interacting!");
        }
    }

}
