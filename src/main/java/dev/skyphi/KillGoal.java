package dev.skyphi;

import org.bukkit.plugin.java.JavaPlugin;

import dev.skyphi.Commands.SetGoalCommand;
import dev.skyphi.Listeners.KillListener;
import dev.skyphi.Listeners.SnowballListener;
import dev.skyphi.Listeners.SpawnerListener;
import dev.skyphi.Models.PlayerData;

public class KillGoal extends JavaPlugin {

    public static KillGoal INSTANCE;

    public static int GOAL;
    public static int TOTAL_KILLS;

    @Override
    public void onEnable() {
        KillGoal.INSTANCE = this;

        initConfig();
        PlayerData.load();

        getServer().getPluginManager().registerEvents(new SnowballListener(), this);
        getServer().getPluginManager().registerEvents(new SpawnerListener(), this);
        getServer().getPluginManager().registerEvents(new KillListener(), this);

        this.getCommand("setgoal").setExecutor(new SetGoalCommand());
    }

    @Override
    public void onDisable() {
        PlayerData.save();
    }

    private void initConfig() {
        saveDefaultConfig();
        KillGoal.GOAL = getConfig().getInt("goal");
    }

    public static int addToKills() {
        if(++TOTAL_KILLS == GOAL) onKillGoalReached();
        INSTANCE.getConfig().set("progress", TOTAL_KILLS);
        INSTANCE.saveConfig();
        return TOTAL_KILLS;
    }

    public static void onKillGoalReached() {
        // TO-DO
        // whatever happens when the goal is reached
    }

}
