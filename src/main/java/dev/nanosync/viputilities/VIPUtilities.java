package dev.nanosync.viputilities;

import dev.nanosync.nanoapi.external.DiscordConnector;
import dev.nanosync.nanoapi.external.NanoDiscord;
import dev.nanosync.viputilities.commands.GeneralVIPCommands;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import org.bukkit.plugin.java.JavaPlugin;

public final class VIPUtilities extends JavaPlugin {

    private static DiscordConnector discordConnector;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        discordConnector = new DiscordConnector();

        BukkitFrame frame = new BukkitFrame(this);
        frame.registerCommands(new GeneralVIPCommands());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static NanoDiscord getDiscord(){
        if (discordConnector.isEnabled()) return discordConnector.getNanoDiscord();
        return null;
    }

    public static VIPUtilities getInstance(){
        return getPlugin(VIPUtilities.class);
    }
}
