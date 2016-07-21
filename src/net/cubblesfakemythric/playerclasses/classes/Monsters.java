package net.cubblesfakemythric.playerclasses.classes;

import net.cubblesfakemythric.playerclasses.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Monsters implements PlayerClass {

    private List<Player> members = new ArrayList<>();

    @Override
    public String name() {
        return "Monsters";
    }

    @Override
    public String description() {
        return "Scour the land for any scum you manage to find.";
    }

    @Override
    public int costExp() {
        return 0;
    }

    @Override
    public int abilitySecondCooldown() {
        return 60 * 3;
    }

    @Override
    public PlayerClassAbility.AbilityType abilityType() {
        return PlayerClassAbility.AbilityType.ON_SHIFT_HIT;
    }

    @Override
    public void ability(final Player player) {
        if (PlayerClassAbility.isCooldown(player)){
            player.sendMessage(Main.TAG + "You have used §6§lUndead Ruse §erecently, try again soon!");
        } else {
            player.sendMessage(Main.TAG + "You have summoned your evil minions to do your bidding.");
            player.sendMessage(Main.TAG + "Your §d§ldark energy §ehas limited your abilities! Stay safe.");

            player.setMaxHealth(2 * 5);

            final IronGolem golem1 = (IronGolem) player.getWorld().spawnEntity(player.getLocation().add(0, 0, -3), EntityType.IRON_GOLEM);
            golem1.setCustomNameVisible(true);
            golem1.setCustomName("§d§l§kiii§r §d§l" + player.getName().toUpperCase() + "'s MINION §d§l§kiii");

            final IronGolem golem2 = (IronGolem) player.getWorld().spawnEntity(player.getLocation().add(0, 0, 3), EntityType.IRON_GOLEM);
            golem2.setCustomNameVisible(true);
            golem2.setCustomName("§d§l§kiii§r §d§l" + player.getName().toUpperCase() + "'s MINION §d§l§kiii");

            final IronGolem golem3 = (IronGolem) player.getWorld().spawnEntity(player.getLocation().add(-3, 0, 0), EntityType.IRON_GOLEM);
            golem3.setCustomNameVisible(true);
            golem3.setCustomName("§d§l§kiii§r §d§l" + player.getName().toUpperCase() + "'s MINION §d§l§kiii");

            final IronGolem golem4 = (IronGolem) player.getWorld().spawnEntity(player.getLocation().add(3, 0, 0), EntityType.IRON_GOLEM);
            golem4.setCustomNameVisible(true);
            golem4.setCustomName("§d§l§kiii§r §d§l" + player.getName().toUpperCase() + "'s MINION §d§l§kiii");

            Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getMain(), new Runnable() {
                @Override
                public void run() {
                    if (golem1.isDead() && golem2.isDead() && golem3.isDead() && golem4.isDead()){
                        player.sendMessage(Main.TAG + "Your §d§levil minions §ehave been defeated, you are still weak.");
                        player.sendMessage(Main.TAG + "Although, your health has been fully restored! ");
                        player.setMaxHealth(2 * 10);
                        player.setHealth(player.getMaxHealth());

                        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 15, 0, false, false));
                        Bukkit.getScheduler().cancelAllTasks();
                    } else {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 10, 0, false, false));
                        for (Entity all : player.getWorld().getEntities()){
                            if (all instanceof LivingEntity && all.getType() != EntityType.ARMOR_STAND && all.getType() != EntityType.IRON_GOLEM){
                                if (all.getLocation().distance(player.getLocation()) < 10){
                                    if (all != player){
                                        golem1.setTarget((LivingEntity) all);
                                        golem2.setTarget((LivingEntity) all);
                                        golem3.setTarget((LivingEntity) all);
                                        golem4.setTarget((LivingEntity) all);
                                    }
                                }
                            }
                        }
                    }
                }
            }, 20, 20);

            PlayerClassAbility.runCooldown(player);
        }
    }

    @Override
    public PlayerClassAbility.AbilityType passiveType() {
        return PlayerClassAbility.AbilityType.PASSIVE;
    }

    @Override
    public void passive(final Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 20, 0, false, false));
        for (Player all : Bukkit.getOnlinePlayers()){
            if (all.getLocation().distance(player.getLocation()) < 10){
                if (PlayerClassManager.getPlayerClass(all) == PlayerClassManager.getPlayerClass("Monsters")){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 7, 1, false, false));
                    all.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 7, 0, false, false));
                }
            }
        }
    }

    @Override
    public List<Player> members() {
        return members;
    }
}
