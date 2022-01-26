package dev.nanosync.viputilities;

import dev.nanosync.viputilities.api.DiscordAPI;
import dev.nanosync.viputilities.commands.GeneralVIPCommands;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import org.bukkit.plugin.java.JavaPlugin;

public final class VIPUtilities extends JavaPlugin {

    private static final DiscordAPI discordAPI = new DiscordAPI();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        BukkitFrame frame = new BukkitFrame(this);
        frame.registerCommands(new GeneralVIPCommands());

        if (!getConfig().getString("DiscordToken").isEmpty()) {
            discordAPI.build(getConfig().getString("DiscordToken"), getConfig().getString("GuildID"));
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public DiscordAPI getDiscordAPI(){
        return discordAPI;
    }

    public static VIPUtilities getInstance(){
        return getPlugin(VIPUtilities.class);
    }
}
