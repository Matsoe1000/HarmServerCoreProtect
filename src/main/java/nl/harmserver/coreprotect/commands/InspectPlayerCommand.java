package nl.harmserver.coreprotect.commands;

import nl.harmserver.coreprotect.listeners.BlockLogger;
import nl.harmserver.coreprotect.listeners.BlockLogger.LogEntry;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InspectPlayerCommand implements CommandExecutor {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Gebruik: /inspectplayer <speler>");
            return true;
        }

        String targetPlayer = args[0];
        boolean found = false;

        for (LogEntry entry : BlockLogger.logs) {
            if (entry != null && entry.player.equalsIgnoreCase(targetPlayer)) {
                found = true;
                String timeFormatted = dateFormat.format(new Date(entry.time));
                sender.sendMessage(ChatColor.YELLOW + "[InspectPlayer] " +
                        ChatColor.AQUA + entry.player +
                        ChatColor.GRAY + " heeft " +
                        ChatColor.GOLD + entry.action +
                        ChatColor.GRAY + " gedaan op " +
                        ChatColor.GREEN + entry.blockType.name() +
                        ChatColor.GRAY + " @ " +
                        ChatColor.DARK_GREEN + "(" + entry.location.getBlockX() + "," +
                        entry.location.getBlockY() + "," +
                        entry.location.getBlockZ() + ")" +
                        ChatColor.GRAY + " (" + timeFormatted + ")");
            }
        }

        if (!found) {
            sender.sendMessage(ChatColor.GRAY + "[InspectPlayer] Geen logs gevonden voor speler " +
                    ChatColor.YELLOW + targetPlayer);
        }

        return true;
    }
}
