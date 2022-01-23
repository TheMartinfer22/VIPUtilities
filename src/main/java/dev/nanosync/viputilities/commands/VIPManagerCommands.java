package dev.nanosync.viputilities.commands;

import dev.nanosync.viputilities.api.LuckPermsAPI;
import dev.nanosync.viputilities.service.VIPGroupService;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.data.NodeMap;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.time.Duration;

public class VIPManagerCommands {

    LuckPermsAPI luckPermsAPI = new LuckPermsAPI();

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
                commandSender.sendMessage("O vip de " + playerNickname + " vai expirar em " + getExpiryTime(Bukkit.getPlayer(playerNickname)));
                return;
            }
            if (isVIP(commandSender)) commandSender.sendMessage("Vai expirar em " + getExpiryTime(commandSender));
            return;
        }

        if (isVIP(commandSender)){
            commandSender.sendMessage("Vai expirar em " + getExpiryTime(commandSender));
            return;
        }

        sendNoPermission(commandSender);
    }

    @Command(name = "viputilities.add")
    public void adminAddCommand(Context<CommandExecutor> context){
        CommandSender commandSender = (CommandSender) context.getSender();
        if (isAdmin(commandSender)){
            if (context.argsCount() < 1){
                commandSender.sendMessage("Você deve especificar o jogador, cargo e quantidade de dias!");
                return;
            }

            if (context.argsCount() < 2){
                commandSender.sendMessage("Você deve especificar o cargo!");
                return;
            }

            if (context.argsCount() < 3){
                commandSender.sendMessage("Você deve especificar os dias!");
                return;
            }

            luckPermsAPI.addPlayerVIP(context.getArg(0), context.getArg(1), context.getArg(2, Integer.class));
            commandSender.sendMessage("Adicionado com sucesso");
            return;
        }
        sendNoPermission(commandSender);
    }

    @Command(name = "viputilities.remove")
    public void adminRemoveCommand(Context<CommandExecutor> context){
        CommandSender commandSender = (CommandSender) context.getSender();
        if (isAdmin(commandSender)){
            if (context.argsCount() < 1){
                commandSender.sendMessage("Você deve especificar o jogador e o cargo!");
                return;
            }

            if (context.argsCount() < 2){
                commandSender.sendMessage("Você deve especificar o cargo!");
                return;
            }

            luckPermsAPI.removePlayerVIP(context.getArg(0), context.getArg(1));
            commandSender.sendMessage("Removido com sucesso");
            return;
        }
        sendNoPermission(commandSender);
    }

    @Command(name = "debugvip")
    public void dev(Context<CommandExecutor> context){
        CommandSender commandSender = (CommandSender) context.getSender();
        NodeMap data = LuckPermsProvider.get().getUserManager().getUser(commandSender.getName()).data();
        data.toCollection().forEach(System.out::println);
    }

    private boolean isAdmin(CommandSender commandSender){
        return commandSender instanceof ConsoleCommandSender || commandSender.hasPermission("viputilities.admin");
    }

    private boolean isVIP(CommandSender commandSender){
        return commandSender.hasPermission("viputilities.vip");
    }

    private void sendNoPermission(CommandSender commandSender){
        commandSender.sendMessage("you are not permission to execute this!");
    }

    private Duration getExpiryTime(CommandSender commandSender){
        return luckPermsAPI.getExpiryTime(commandSender, luckPermsAPI.getPlayerGroup(commandSender, VIPGroupService.getVIPGroups()));
    }
}
