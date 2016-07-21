package net.cubblesfakemythric.playerclasses.classes;

import net.cubblesfakemythric.playerclasses.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerClassAbility {

    private static List<Player> cooldown = new ArrayList<>();
    public static List<Player> getCooldown() {
        return cooldown;
    }

    public static boolean isCooldown(Player player){
        if (getCooldown().contains(player))
            return true;
        else
            return false;
    }

    public static void runCooldown(final Player player){
        getCooldown().add(player);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), new Runnable() {
            @Override
            public void run() {
                getCooldown().remove(player);
            }
        }, 20 * PlayerClassManager.getPlayerClass(player).abilitySecondCooldown());
    }

    private static List<PlayerClassAbility> abilities = new ArrayList<>();
    public static List<PlayerClassAbility> getAbilities(){
        return abilities;
    }

    public enum AbilityType {
        ON_HIT, ON_WALK, ON_BLOCK, ON_SHIFT, ON_TAKEDAMAGE, ON_BREAKBLOCK, ON_PLACEBLOCK, ON_BLOCK_SHIFT, ON_SHIFT_HIT, PASSIVE
    }

    private String name;

    public PlayerClassAbility(String name) {
        this.name = name;
    }

    private Runnable run;
    AbilityType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Runnable getRun() {
        return run;
    }

    public void setRun(Runnable run){
        this.run = run;
    }

    public AbilityType getType() {
        return type;
    }

    public void setType(AbilityType type) {
        this.type = type;
    }
}
