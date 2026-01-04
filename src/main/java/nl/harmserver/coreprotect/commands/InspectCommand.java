package nl.harmserver.coreprotect.commands;

import nl.harmserver.coreprotect.listeners.BlockLogger;
import nl.harmserver.coreprotect.listeners.BlockLogger.LogEntry;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InspectCommand implements CommandExecutor {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Alleen spelers kunnen dit command gebruiken.");
            return true;
        }

        Player player = (Player) sender;
        Block targetBlock = player.getTargetBlockExact(10); // blok waar speler naar kijkt, max 10 blokken afstand

        if (targetBlock == null || targetBlock.getType() == Material.AIR) {
            player.sendMessage(ChatColor.RED + "Je kijkt niet naar een geldig blok.");
            return true;
        }

        Location loc = targetBlock.getLocation();
        boolean found = false;

        for (LogEntry entry : BlockLogger.logs) {
            if (entry != null && entry.location.equals(loc)) {
                found = true;
                String timeFormatted = dateFormat.format(new Date(entry.time));
                player.sendMessage(ChatColor.YELLOW + "[Inspect] " +
                        ChatColor.AQUA + entry.player +
                        ChatColor.GRAY + " heeft " +
                        ChatColor.GOLD + entry.action +
                        ChatColor.GRAY + " gedaan op " +
                        ChatColor.GREEN + entry.blockType.name() +
                        ChatColor.GRAY + " (" + timeFormatted + ")");
            }
        }

        if (!found) {
            player.sendMessage(ChatColor.GRAY + "[Inspect] Geen log gevonden voor dit blok.");
        }

        return true;
    }
}
