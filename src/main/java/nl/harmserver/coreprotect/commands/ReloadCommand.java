public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Hier kun je config opnieuw laden of logs resetten
        BlockLogger.logs.clear();
        sender.sendMessage(ChatColor.GREEN + "CoreProtect plugin herladen. Logs zijn gewist.");
        return true;
    }
}

