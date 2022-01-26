package dev.nanosync.viputilities.commands;

import dev.nanosync.viputilities.api.LuckPermsAPI;
import dev.nanosync.viputilities.manager.ConfigManager;
import dev.nanosync.viputilities.manager.TimeManager;
import dev.nanosync.viputilities.service.DiscordService;
import dev.nanosync.viputilities.service.VIPGroupService;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class GeneralVIPCommands {

    private final LuckPermsAPI luckPermsAPI = new LuckPermsAPI();
    private final DiscordService discordService = new DiscordService();

    @Command(name = "viputilities")
    public void mainCommand(Context<CommandExecutor> context){
        CommandSender commandSender = (CommandSender) context.getSender();

        if (isAdmin(commandSender)){
            commandSender.sendMessage("Great you are admin see commands below");
            return;
        }

        if (isVIP(commandSender)){
            commandSender.sendMessage("Great you are user see commands below");
            return;
        }

        sendNoPermission(commandSender);
    }

    @Command(name = "viputilities.time")
    public void vipTimeCommand(Context<CommandExecutor> context){
        CommandSender commandSender = (CommandSender) context.getSender();
        if (isAdmin(commandSender)){
            if (context.argsCount() == 1){
                String playerNickname = context.getArg(0);
                if (!luckPermsAPI.hasVIPPlayer(playerNickname, VIPGroupService.getVIPGroups())){
                    commandSender.sendMessage(ConfigManager.getConfigMessage("ErrorPlayerNotVIP"));
                    return;
                }
                commandSender.sendMessage(getConfigExpiryTime(playerNickname, "ExpiryVIPOtherMessage"));
                return;
            }
        }

        if (isVIP(commandSender)){
            commandSender.sendMessage(getConfigExpiryTime(commandSender.getName(), "ExpiryVIPMessage"));
            return;
        }

        sendNoPermission(commandSender);
    }

    @Command(name = "viputilities.add")
    public void adminAddCommand(Context<CommandExecutor> context){
        CommandSender commandSender = (CommandSender) context.getSender();
        if (isAdmin(commandSender)){
            if (context.argsCount() < 1){
                commandSender.sendMessage(ConfigManager.getConfigMessage("ErrorSpecifyPlayerGroupDaysAdd"));
                return;
            }

            if (context.argsCount() < 2){
                commandSender.sendMessage(ConfigManager.getConfigMessage("ErrorSpecifyGroup"));
                return;
            }

            if (context.argsCount() < 3){
                commandSender.sendMessage(ConfigManager.getConfigMessage("ErrorSpecifyDays"));
                return;
            }

            if (!luckPermsAPI.isValidGroup(context.getArg(1))){
                commandSender.sendMessage(ConfigManager.getConfigMessage("ErrorInvalidGroupVIP"));
                return;
            }

            luckPermsAPI.addPlayerVIP(context.getArg(0), context.getArg(1), context.getArg(2, Integer.class));
            commandSender.sendMessage(ConfigManager.getConfigMessage("SuccessAdded"));
            Bukkit.getServer().broadcastMessage(ConfigManager.getConfigMessage("BroadcastMessage")
                    .replace("{player}", context.getArg(0))
                    .replace("{group}", context.getArg(1)));
            DiscordService.announceNewVip(context.getArg(0), context.getArg(1));
            return;
        }
        sendNoPermission(commandSender);
    }

    @Command(name = "viputilities.remove")
    public void adminRemoveCommand(Context<CommandExecutor> context){
        CommandSender commandSender = (CommandSender) context.getSender();
        if (isAdmin(commandSender)){
            if (context.argsCount() < 1){
                commandSender.sendMessage(ConfigManager.getConfigMessage("ErrorSpecifyPlayerGroupRemove"));
                return;
            }

            if (context.argsCount() < 2){
                commandSender.sendMessage(ConfigManager.getConfigMessage("ErrorSpecifyGroup"));
                return;
            }

            luckPermsAPI.removePlayerVIP(context.getArg(0), context.getArg(1));
            commandSender.sendMessage(ConfigManager.getConfigMessage("SuccessRemoved"));
            return;
        }
        sendNoPermission(commandSender);
    }

    private boolean isAdmin(CommandSender commandSender){
        return commandSender instanceof ConsoleCommandSender || commandSender.hasPermission("viputilities.admin");
    }

    private boolean isVIP(CommandSender commandSender){
        return commandSender.hasPermission(ConfigManager.getConfigMessage("VIP_PERMISSION"));
    }

    private void sendNoPermission(CommandSender commandSender){
        commandSender.sendMessage(ConfigManager.getConfigMessage("ErrorNoPermission"));
    }

    private String getConfigExpiryTime(String player, String expiryConfig){
        TimeManager timeManager = new TimeManager(luckPermsAPI.getExpiryTime(player, luckPermsAPI.getPlayerGroup(player, VIPGroupService.getVIPGroups())));
        return ConfigManager.getMessageByConfigExpiry(expiryConfig, player, timeManager.getDays(), timeManager.getHours(), timeManager.getMinutes());
    }
}
