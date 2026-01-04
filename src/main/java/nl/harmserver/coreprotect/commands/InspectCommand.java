package nl.harmserver.coreprotect.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.harmserver.coreprotect.listeners.BlockLogger;

public class InspectCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Defensieve null-check om IDE-hints te elimineren
        if (sender == null) {
            return true;
        }

        if (!(sender instanceof Player)) {
            String msg = ChatColor.RED + "Alleen spelers kunnen dit commando gebruiken.";
            sender.sendMessage(msg);
            return true;
        }

        Player player = (Player) sender;

        Block target = player.getTargetBlockExact(5);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "Geen blok geselecteerd.");
            return true;
        }

        Location targetLoc = target.getLocation();
        boolean found = false;

        for (BlockLogger.LogEntry entry : BlockLogger.logs) {
            if (entry == null || entry.location == null) {
                continue;
            }
            if (entry.location.equals(targetLoc)) {
                String who = entry.player != null ? entry.player : "Onbekend";
                String act = entry.action != null ? entry.action : "UNKNOWN";
                String type = entry.blockType != null ? entry.blockType : "UNKNOWN";

                player.sendMessage(
                    ChatColor.YELLOW + who
                    + ChatColor.GRAY + " heeft dit blok "
                    + ChatColor.AQUA + act
                    + ChatColor.GRAY + " (" + type + ")"
                    + ChatColor.GRAY + " op tijdstip "
                    + ChatColor.WHITE + entry.time
                );
                found = true;
            }
        }

        if (!found) {
            player.sendMessage(ChatColor.GRAY + "Geen log gevonden voor dit blok.");
        }

        return true;
    }
}
