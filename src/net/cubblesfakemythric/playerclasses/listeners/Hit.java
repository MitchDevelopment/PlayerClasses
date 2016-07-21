package net.cubblesfakemythric.playerclasses.listeners;

import net.cubblesfakemythric.playerclasses.classes.PlayerClass;
import net.cubblesfakemythric.playerclasses.classes.PlayerClassManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Hit implements Listener {

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        // take damage
        // give damage
        // shift damage

        if (event.getDamager() instanceof Player){
            Player player = (Player) event.getDamager();

            PlayerClass playersClass = PlayerClassManager.getPlayerClass(player);
            if (playersClass != null){
                switch (playersClass.abilityType()){
                    case ON_HIT:
                        playersClass.ability(player);
                        break;
                    case ON_SHIFT_HIT:
                        if (player.isSneaking()){
                            playersClass.ability(player);
                        }
                        break;
                }
            }
        }
    }

}
