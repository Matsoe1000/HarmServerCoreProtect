package nl.harmserver.coreprotect.commands;

import nl.harmserver.coreprotect.HarmServerCoreProtect;
import nl.harmserver.coreprotect.listeners.BlockLogger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Config opnieuw laden
        HarmServerCoreProtect.getInstance().reloadConfig();

        // Logs wissen
        BlockLogger.logs.clear();

        sender.sendMessage(ChatColor.GREEN + "[CoreProtectReload] Config herladen en logs gewist.");
        return true;
    }
}
