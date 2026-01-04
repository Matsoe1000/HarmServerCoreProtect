package nl.harmserver.coreprotect.commands;

import nl.harmserver.coreprotect.listeners.BlockLogger;
import nl.harmserver.coreprotect.listeners.BlockLogger.LogEntry;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RollbackCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Gebruik: /rollback <speler>");
            return true;
        }

        String targetPlayer = args[0];
        int count = 0;

        for (LogEntry entry : BlockLogger.logs) {
            if (entry != null && entry.player.equalsIgnoreCase(targetPlayer) && "PLACE".equals(entry.action)) {
                Block block = entry.location.getBlock();
                block.setType(Material.AIR);
                count++;
            }
        }

        if (count > 0) {
            sender.sendMessage(ChatColor.GREEN + "[Rollback] " +
                    ChatColor.YELLOW + targetPlayer +
                    ChatColor.GRAY + " → " +
                    ChatColor.GOLD + count +
                    ChatColor.GRAY + " blokken verwijderd.");
        } else {
            sender.sendMessage(ChatColor.GRAY + "[Rollback] Geen blokken gevonden voor speler " +
                    ChatColor.YELLOW + targetPlayer);
        }

        return true;
    }
}
