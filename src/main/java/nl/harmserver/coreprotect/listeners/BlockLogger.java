package nl.harmserver.coreprotect.listeners;

import nl.harmserver.coreprotect.HarmServerCoreProtect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlockLogger implements Listener {

    public static final List<LogEntry> logs = new ArrayList<>();
    private static File logFile;

    public BlockLogger() {
        // Maak logbestand aan in plugin folder
        File dataFolder = HarmServerCoreProtect.getInstance().getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        logFile = new File(dataFolder, "blocklogs.txt");
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();
        LogEntry entry = new LogEntry(player.getName(), "PLACE", block.getType(), block.getLocation(), System.currentTimeMillis());
        logs.add(entry);
        writeToFile(entry);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        LogEntry entry = new LogEntry(player.getName(), "BREAK", block.getType(), block.getLocation(), System.currentTimeMillis());
        logs.add(entry);
        writeToFile(entry);
    }

    private void writeToFile(LogEntry entry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            writer.write(entry.toString());
            writer.newLine();
        } catch (IOException e) {
            HarmServerCoreProtect.getInstance().getLogger().warning("Kon log niet wegschrijven: " + e.getMessage());
        }
    }

    public static class LogEntry {
        public final String player;
        public final String action;
        public final Material blockType;
        public final Location location;
        public final long time;

        public LogEntry(String player, String action, Material blockType, Location location, long time) {
            this.player = player;
            this.action = action;
            this.blockType = blockType;
            this.location = location;
            this.time = time;
        }

        @Override
        public String toString() {
            return player + ";" + action + ";" + blockType.name() + ";" +
                    location.getWorld().getName() + "," + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ() +
                    ";" + time;
        }
    }
}
