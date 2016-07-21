package net.cubblesfakemythric.playerclasses.classes;

import org.bukkit.entity.Player;

import java.util.List;

public interface PlayerClass {

    enum AbilityType {
        ON_HIT, ON_WALK, ON_BLOCK, ON_SHIFT, ON_TAKEDAMAGE, ON_BREAKBLOCK, ON_PLACEBLOCK, ON_BLOCK_SHIFT, ON_SHIFT_HIT, PASSIVE
    }

    String name();
    String description();
    int costExp();
    int abilitySecondCooldown();
    PlayerClassAbility.AbilityType abilityType();
    void ability(Player player);
    PlayerClassAbility.AbilityType passiveType();
    void passive(Player player);
    List<Player> members();

}
