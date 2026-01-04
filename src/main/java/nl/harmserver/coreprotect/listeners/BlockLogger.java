package nl.harmserver.coreprotect.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockLogger implements Listener {

    public static class LogEntry {
        public final String player;
        public final String action;
        public final String blockType;
        public final Location location;
        public final long time;

        public LogEntry(String player, String action, String blockType, Location location, long time) {
            this.player = player;
            this.action = action;
            this.blockType = blockType;
            this.location = location;
            this.time = time;
        }
    }

    public static final List<LogEntry> logs = new ArrayList<>();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        logs.add(new LogEntry(player.getName(), "BREAK", block.getType().toString(),
                block.getLocation(), System.currentTimeMillis()));
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();
        logs.add(new LogEntry(player.getName(), "PLACE", block.getType().toString(),
                block.getLocation(), System.currentTimeMillis()));
    }
}
