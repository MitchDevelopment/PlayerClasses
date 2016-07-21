package net.cubblesfakemythric.playerclasses.listeners;

import net.cubblesfakemythric.playerclasses.Main;
import net.cubblesfakemythric.playerclasses.classes.PlayerClass;
import net.cubblesfakemythric.playerclasses.classes.PlayerClassManager;
import net.cubblesfakemythric.playerclasses.utils.JsonBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Click implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        Player player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
            PlayerClass playersClass = PlayerClassManager.getPlayerClass(player);
            if (player.getItemInHand() != null){
                if (player.getItemInHand().getType() == Material.DIAMOND_SWORD ||
                        player.getItemInHand().getType() == Material.GOLD_SWORD ||
                        player.getItemInHand().getType() == Material.IRON_SWORD ||
                        player.getItemInHand().getType() == Material.STONE_SWORD ||
                        player.getItemInHand().getType() == Material.WOOD_SWORD){
                    if (playersClass != null){
                        switch (playersClass.abilityType()){
                            case ON_BLOCK:
                                playersClass.ability(player);
                                break;
                            case ON_BLOCK_SHIFT:
                                if (player.isSneaking()){
                                    playersClass.ability(player);
                                }
                                break;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractAtEntityEvent event){
        Player player = event.getPlayer();
        Entity clicked = event.getRightClicked();

        if (clicked instanceof Player) {
            PlayerClass selectedClass = null;

            if (PlayerClassManager.getPlayerClass(ChatColor.stripColor(clicked.getName())) != null) {
                selectedClass = PlayerClassManager.getPlayerClass(ChatColor.stripColor(clicked.getName()));

                if (Connection.newPlayers.contains(player) || player.getName().equalsIgnoreCase("CodenameFlip")){
                    event.setCancelled(true);
                    player.sendMessage(Main.TAG + "§6Confirm Selection for §a§l" + selectedClass.name().toUpperCase());
                    player.sendMessage(Main.TAG + "§7§o" + selectedClass.description());
                    player.sendMessage(" ");
                    player.sendMessage(" ");
                    new JsonBuilder("                       ACCEPT").withColor(org.bukkit.ChatColor.GREEN).withColor(org.bukkit.ChatColor.BOLD).withHoverEvent(JsonBuilder.HoverAction.SHOW_TEXT, "§7Click to select this class!").withHoverEvent(JsonBuilder.HoverAction.SHOW_TEXT, "§cOnce you choose a class you cannot go back!").withClickEvent(JsonBuilder.ClickAction.RUN_COMMAND, "/chooseclass " + selectedClass.name()).sendJson(player);
                    player.sendMessage(" ");
                    player.sendMessage(" ");
                }
            }
        }
    }

    @EventHandler
    public void onTP(PlayerTeleportEvent event){
        Player player = event.getPlayer();
        if (Connection.newPlayers.contains(player)){
            event.setCancelled(true);
            player.sendMessage(Main.TAG + "§cNot so fast there! You forgot to select a class!");
        }
    }

}
