package dev.skyphi.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.skyphi.KillGoal;

public class SetGoalCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
            return true;
        }
        Player player = (Player)sender;

        int goal = -1;
        try {
            goal = Integer.parseInt(args[0]);
        }catch(NumberFormatException | ArrayIndexOutOfBoundsException e ) {
            player.sendMessage(ChatColor.RED + "Usage: /setgoal <number>");
            return true;
        }

        KillGoal.GOAL = goal;
        KillGoal.INSTANCE.getConfig().set("goal", goal);
        KillGoal.INSTANCE.saveConfig();
        player.sendMessage(ChatColor.AQUA + "Server-wide kill goal set to " + ChatColor.BOLD +""+goal);
        
        return true;
    }
    
}
