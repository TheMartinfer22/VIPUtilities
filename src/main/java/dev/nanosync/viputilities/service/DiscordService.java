package dev.nanosync.viputilities.service;

import dev.nanosync.viputilities.VIPUtilities;
import dev.nanosync.viputilities.api.DiscordAPI;
import dev.nanosync.viputilities.manager.ConfigManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class DiscordService {

    private static final DiscordAPI discord = VIPUtilities.getInstance().getDiscordAPI();
    private static final String channel = ConfigManager.getConfigMessage("ChannelID");

    public static void announceNewVip(String player, String group){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.YELLOW);
        embed.setDescription(ConfigManager.getConfigMessage("AnnounceNewVipMessage")
                .replace("{player}", player)
                .replace("{group}", group));
        TextChannel channel = discord.getGuild().getTextChannelById(DiscordService.channel);
        channel.sendMessageEmbeds(embed.build()).queue();
    }
}
