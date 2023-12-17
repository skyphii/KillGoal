package dev.skyphi;

import org.bukkit.plugin.java.JavaPlugin;

import dev.skyphi.Commands.SetGoalCommand;
import dev.skyphi.Listeners.SnowballListener;
import dev.skyphi.Listeners.SpawnerListener;

public class KillGoal extends JavaPlugin {

    public static KillGoal INSTANCE;

    public static int GOAL;
    public static int TOTAL_KILLS;

    @Override
    public void onEnable() {
        KillGoal.INSTANCE = this;

        getServer().getPluginManager().registerEvents(new SnowballListener(), this);
        getServer().getPluginManager().registerEvents(new SpawnerListener(), this);

        this.getCommand("setgoal").setExecutor(new SetGoalCommand());
    }

}
