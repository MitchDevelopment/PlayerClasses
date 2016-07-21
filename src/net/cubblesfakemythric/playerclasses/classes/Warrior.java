package net.cubblesfakemythric.playerclasses.classes;

import net.cubblesfakemythric.playerclasses.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Warrior implements PlayerClass {
    private List<Player> members = new ArrayList<>();

    @Override
    public String name() {
        return "Warriors";
    }

    @Override
    public String description() {
        return "Brutally beat your enemies with this powerful and destructive class.";
    }

    @Override
    public int costExp() {
        return 0;
    }

    @Override
    public int abilitySecondCooldown() {
        return 90;
    }

    @Override
    public PlayerClassAbility.AbilityType abilityType() {
        return PlayerClassAbility.AbilityType.ON_BLOCK;
    }

    @Override
    public void ability(final Player player) {
        if (PlayerClassAbility.isCooldown(player)){
            player.sendMessage(Main.TAG + "You have used §6§lBrutal Force §erecently, try again soon!");
        } else {
            player.sendMessage(Main.TAG + "You have launched anyone in §6§l< 10 blocks §einto the air!");
            for (Player all : Bukkit.getOnlinePlayers()){
                if (all.getLocation().distance(player.getLocation()) < 10){
                    all.setVelocity(new Vector(0, 1.5, 0));
                    all.getActivePotionEffects().add(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 5, 0, false, false));
                }
            }

            PlayerClassAbility.runCooldown(player);
        }
    }

    @Override
    public PlayerClassAbility.AbilityType passiveType() {
        return PlayerClassAbility.AbilityType.PASSIVE;
    }

    @Override
    public void passive(final Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 7, 0, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 7, 0, false, false));
    }

    @Override
    public List<Player> members() {
        return members;
    }
}
