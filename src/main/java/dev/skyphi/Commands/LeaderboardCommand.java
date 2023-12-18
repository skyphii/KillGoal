package dev.skyphi.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.skyphi.KillGoal;
import dev.skyphi.Models.Leaderboard;

public class LeaderboardCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
            return true;
        }
        Player player = (Player)sender;

        if(!player.hasPermission("killgoal.leaderboard")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        if(KillGoal.LEADERBOARD != null) {
            KillGoal.LEADERBOARD.destroy();
        }

        int numPlayers = 10;
        if(args.length > 0) {
            try {
                numPlayers = Integer.parseInt(args[0]);
            }catch(NumberFormatException e) {}
        }

        KillGoal.LEADERBOARD = new Leaderboard(numPlayers, player.getLocation().add(0, 2, 0));
        KillGoal.LEADERBOARD.initialize();

        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BIT, SoundCategory.RECORDS, 1.0f, 1.0f);
        
        return true;
    }
    
}
