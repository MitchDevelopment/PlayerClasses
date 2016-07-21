package net.cubblesfakemythric.playerclasses.classes;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerClassManager {

    private static List<PlayerClass> classes = new ArrayList<>();

    public static List<PlayerClass> getClasses(){
        return classes;
    }

    public static HashMap<Player, Integer> passive = new HashMap<>();

    public static PlayerClass getPlayerClass(String name){
        for (PlayerClass all : getClasses()){
            if (all.name().equalsIgnoreCase(name)){
                return all;
            }
        }
        return null;
    }

    public static PlayerClass getPlayerClass(Player player){
        for (PlayerClass all : getClasses()){
            if (all.members().contains(player)){
                return all;
            }
        }
        return null;
    }

}
