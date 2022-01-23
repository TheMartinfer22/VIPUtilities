package dev.nanosync.viputilities;

import dev.nanosync.viputilities.commands.VIPManagerCommands;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import org.bukkit.plugin.java.JavaPlugin;

public final class VIPUtilities extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        BukkitFrame frame = new BukkitFrame(this);
        frame.registerCommands(new VIPManagerCommands());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static VIPUtilities getInstance(){
        return getPlugin(VIPUtilities.class);
    }
}
