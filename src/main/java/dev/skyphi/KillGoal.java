package dev.skyphi;

import org.bukkit.plugin.java.JavaPlugin;

import dev.skyphi.Commands.SetGoalCommand;
import dev.skyphi.Commands.SetSpawnerCommand;
import dev.skyphi.Commands.StartCommand;
import dev.skyphi.Listeners.SnowballListener;
import dev.skyphi.Managers.SpawnerManager;

public class KillGoal extends JavaPlugin {

    public static KillGoal INSTANCE;
    public static SpawnerManager SPAWNER_MANAGER;

    public static int GOAL;
    public static int TOTAL_KILLS;

    @Override
    public void onEnable() {
        KillGoal.INSTANCE = this;
        KillGoal.SPAWNER_MANAGER = new SpawnerManager();

        getServer().getPluginManager().registerEvents(new SnowballListener(), this);

        this.getCommand("setgoal").setExecutor(new SetGoalCommand());
        this.getCommand("setspawner").setExecutor(new SetSpawnerCommand());
        this.getCommand("start").setExecutor(new StartCommand());
    }

}
