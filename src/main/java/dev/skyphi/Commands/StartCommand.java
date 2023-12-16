package dev.skyphi.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.skyphi.KillGoal;

public class StartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
            return true;
        }

        if(!KillGoal.SPAWNER_MANAGER.hasSpawners()) {
            sender.sendMessage(ChatColor.RED + "You must first setup spawners using /setspawner");
            return true;
        }

        KillGoal.SPAWNER_MANAGER.start();
        
        return true;
    }
}
