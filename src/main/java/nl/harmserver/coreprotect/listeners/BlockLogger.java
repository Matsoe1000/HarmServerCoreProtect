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

import java.io.*;
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

        // Bij plugin start: laad bestaande logs
        loadFromFile();
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

    private void loadFromFile() {
        if (!logFile.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Formaat: player;action;blockType;world,x,y,z;time
                String[] parts = line.split(";");
                if (parts.length == 5) {
                    String player = parts[0];
                    String action = parts[1];
                    Material blockType = Material.matchMaterial(parts[2]);

                    String[] locParts = parts[3].split(",");
                    String world = locParts[0];
                    int x = Integer.parseInt(locParts[1]);
                    int y = Integer.parseInt(locParts[2]);
                    int z = Integer.parseInt(locParts[3]);

                    Location location = HarmServerCoreProtect.getInstance().getServer().getWorld(world).getBlockAt(x, y, z).getLocation();
                    long time = Long.parseLong(parts[4]);

                    logs.add(new LogEntry(player, action, blockType, location, time));
                }
            }
            HarmServerCoreProtect.getInstance().getLogger().info("Block logs geladen: " + logs.size() + " entries.");
        } catch (IOException e) {
            HarmServerCoreProtect.getInstance().getLogger().warning("Kon logs niet laden: " + e.getMessage());
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
