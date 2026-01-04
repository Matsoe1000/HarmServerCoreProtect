public class RollbackTimeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Gebruik: /rollbacktime <speler> <minuten>");
            return true;
        }
        String targetPlayer = args[0];
        int minutes = Integer.parseInt(args[1]);
        long cutoff = System.currentTimeMillis() - (minutes * 60 * 1000);

        int count = 0;
        for (BlockLogger.LogEntry entry : BlockLogger.logs) {
            if (entry != null && entry.player.equalsIgnoreCase(targetPlayer)
                && "PLACE".equals(entry.action) && entry.time >= cutoff) {
                Block block = entry.location.getBlock();
                block.setType(Material.AIR);
                count++;
            }
        }
        sender.sendMessage(ChatColor.GREEN + "Rollback uitgevoerd voor speler "
                + ChatColor.YELLOW + targetPlayer
                + ChatColor.GRAY + " (laatste " + minutes + " minuten, " + count + " blokken verwijderd)");
        return true;
    }
}

