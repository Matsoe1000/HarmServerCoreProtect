package nl.harmserver.coreprotect.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import nl.harmserver.coreprotect.listeners.BlockLogger;

public class RollbackCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Gebruik: /rollback <speler>");
            return true;
        }

        String targetPlayer = args[0];
        int count = 0;

        for (BlockLogger.LogEntry entry : BlockLogger.logs) {
            if (entry == null || entry.location == null) {
                continue;
            }
            if (entry.player != null && entry.player.equalsIgnoreCase(targetPlayer) && "PLACE".equals(entry.action)) {
                Block block = entry.location.getBlock(); // nooit null
                block.setType(Material.AIR);
                count++;
            }
        }

        sender.sendMessage(ChatColor.GREEN + "Rollback uitgevoerd voor speler "
                + ChatColor.YELLOW + targetPlayer
                + ChatColor.GRAY + ". (" + count + " blokken verwijderd)");
        return true;
    }
}
