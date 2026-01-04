public class InspectPlayerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Gebruik: /inspectplayer <speler>");
            return true;
        }
        String targetPlayer = args[0];
        for (BlockLogger.LogEntry entry : BlockLogger.logs) {
            if (entry != null && entry.player.equalsIgnoreCase(targetPlayer)) {
                sender.sendMessage(ChatColor.YELLOW + entry.player + ChatColor.GRAY +
                        " " + entry.action + " " + entry.blockType + " @ " + entry.location +
                        " (" + entry.time + ")");
            }
        }
        return true;
    }
}

