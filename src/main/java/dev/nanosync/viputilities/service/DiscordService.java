package dev.nanosync.viputilities.service;

import dev.nanosync.nanoapi.external.NanoDiscord;
import dev.nanosync.viputilities.VIPUtilities;
import dev.nanosync.viputilities.manager.ConfigManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.Locale;
import java.util.Objects;

public class DiscordService {

    private static final NanoDiscord discord = VIPUtilities.getDiscord();
    private static final String channel = ConfigManager.getConfigMessage("ChannelID");

    public static void announceNewVip(String player, String group){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.YELLOW);
        embed.setDescription(ConfigManager.getConfigMessage("AnnounceNewVipMessage")
                .replace("{player}", player)
                .replace("{group}", group.toUpperCase(Locale.ROOT)));
        TextChannel channel = Objects.requireNonNull(discord).getGuild().getTextChannelById(DiscordService.channel);
        Objects.requireNonNull(channel).sendMessageEmbeds(embed.build()).queue();
    }
}
