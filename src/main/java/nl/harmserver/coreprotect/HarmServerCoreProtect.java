package nl.harmserver.coreprotect;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import nl.harmserver.coreprotect.commands.InspectCommand;
import nl.harmserver.coreprotect.commands.RollbackCommand;
import nl.harmserver.coreprotect.listeners.BlockLogger;

public class HarmServerCoreProtect extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("HarmServerCoreProtect plugin ingeschakeld!");

        // Events
        getServer().getPluginManager().registerEvents(new BlockLogger(), this);

        // Commands met expliciete variabele + null-check
        PluginCommand inspectCmd = getCommand("inspect");
        if (inspectCmd != null) {
            inspectCmd.setExecutor(new InspectCommand());
        } else {
            getLogger().severe("Command 'inspect' ontbreekt in plugin.yml!");
        }

        PluginCommand rollbackCmd = getCommand("rollback");
        if (rollbackCmd != null) {
            rollbackCmd.setExecutor(new RollbackCommand());
        } else {
            getLogger().severe("Command 'rollback' ontbreekt in plugin.yml!");
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("HarmServerCoreProtect plugin uitgeschakeld!");
    }
}
