package net.cubblesfakemythric.playerclasses.classes;

import net.cubblesfakemythric.playerclasses.Main;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Dwarves implements PlayerClass {
    private List<Player> members = new ArrayList<>();

    @Override
    public String name() {
        return "Dwarves";
    }

    @Override
    public String description() {
        return "Quickly navigate the dangerous world with agility and wisdom.";
    }

    @Override
    public int costExp() {
        return 0;
    }

    @Override
    public int abilitySecondCooldown() {
        return 60 * 2;
    }

    @Override
    public PlayerClassAbility.AbilityType abilityType() {
        return PlayerClassAbility.AbilityType.ON_BLOCK_SHIFT;
    }

    @Override
    public void ability(final Player player) {
        if (PlayerClassAbility.isCooldown(player)){
            player.sendMessage(Main.TAG + "You have used §6§lEvade §erecently, try again soon!");
        } else {
            player.getLocation().getWorld().playEffect(player.getLocation(), Effect.EXPLOSION_HUGE, 1);

            player.sendMessage(Main.TAG + "You have entered the §b§lShadow Dimension§e!");
            player.sendMessage(Main.TAG + "You will be removed in 10 seconds. Until then you have these effects:");
            player.sendMessage(Main.TAG + "   • §lInvisibility I");
            player.sendMessage(Main.TAG + "   • §lSpeed V");
            player.sendMessage(Main.TAG + "   • §lBlindness I");

            player.setWalkSpeed((float) .4);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), new Runnable() {
                @Override
                public void run() {
                    player.setWalkSpeed((float) .2);
                }
            }, 20 * 10);
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 10, 1, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 10, 1, false, false));

            PlayerClassAbility.runCooldown(player);
        }
    }

    @Override
    public PlayerClassAbility.AbilityType passiveType() {
        return PlayerClassAbility.AbilityType.PASSIVE;
    }

    @Override
    public void passive(final Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 7, 1, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 7, 0, false, false));
    }

    @Override
    public List<Player> members() {
        return members;
    }
}
