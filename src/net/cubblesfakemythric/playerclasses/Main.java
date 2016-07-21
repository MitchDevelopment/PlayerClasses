package net.cubblesfakemythric.playerclasses;

import net.cubblesfakemythric.playerclasses.classes.Dwarves;
import net.cubblesfakemythric.playerclasses.classes.Monsters;
import net.cubblesfakemythric.playerclasses.classes.PlayerClassManager;
import net.cubblesfakemythric.playerclasses.classes.Warrior;
import net.cubblesfakemythric.playerclasses.cmds.CmdChooseClass;
import net.cubblesfakemythric.playerclasses.cmds.CmdClass;
import net.cubblesfakemythric.playerclasses.listeners.Chat;
import net.cubblesfakemythric.playerclasses.listeners.Click;
import net.cubblesfakemythric.playerclasses.listeners.Connection;
import net.cubblesfakemythric.playerclasses.listeners.Hit;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static final String TAG = "§e§l(!) §e";

    private static Main main;
    public static Main getMain(){
        return main;
    }

    public void onEnable(){
        main = this;

        registerListeners();
        registerCommands();
        loadClasses();
    }

    public void onDisable(){

    }

    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new Hit(), this);
        Bukkit.getPluginManager().registerEvents(new Click(), this);
        Bukkit.getPluginManager().registerEvents(new Connection(), this);
        Bukkit.getPluginManager().registerEvents(new Chat(), this);
        // TODO: Limit EXP gain
        // TODO: Load the class from config on join
    }

    private void registerCommands(){
        getCommand("class").setExecutor(new CmdClass());
        getCommand("chooseclass").setExecutor(new CmdChooseClass());
    }

    private void loadClasses(){
        PlayerClassManager.getClasses().add(new Warrior());
        PlayerClassManager.getClasses().add(new Dwarves());
        PlayerClassManager.getClasses().add(new Monsters());
    }

}
