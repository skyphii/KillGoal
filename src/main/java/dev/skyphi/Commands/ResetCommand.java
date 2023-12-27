package dev.skyphi.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.skyphi.KillGoal;
import dev.skyphi.Models.PlayerData;

public class ResetCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
            return true;
        }
        Player player = (Player)sender;

        if(!player.hasPermission("killgoal.reset")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        PlayerData.reset();
        KillGoal.LEADERBOARD.destroy();
        KillGoal.LEADERBOARD.initialize();
        
        return true;
    }
    
}
