package nl.harmserver.coreprotect;

import nl.harmserver.coreprotect.commands.*;
import nl.harmserver.coreprotect.listeners.BlockLogger;
import nl.harmserver.coreprotect.listeners.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public class HarmServerCoreProtect extends JavaPlugin {

    private static HarmServerCoreProtect instance;

    @Override
    public void onEnable() {
        instance = this;

        // Listeners registreren
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        // Commands registreren
        getCommand("inspect").setExecutor(new InspectCommand());
        getCommand("rollback").setExecutor(new RollbackCommand());
        getCommand("rollbacktime").setExecutor(new RollbackTimeCommand());
        getCommand("inspectplayer").setExecutor(new InspectPlayerCommand());
        getCommand("coreprotectreload").setExecutor(new ReloadCommand());

        getLogger().info("HarmServerCoreProtect is succesvol ingeschakeld!");
    }

    @Override
    public void onDisable() {
        getLogger().info("HarmServerCoreProtect is uitgeschakeld.");
    }

    public static HarmServerCoreProtect getInstance() {
        return instance;
    }
}
