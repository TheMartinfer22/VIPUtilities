package dev.nanosync.viputilities.api;

import net.luckperms.api.LuckPermsProvider;

import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import org.bukkit.command.CommandSender;

import java.time.Duration;
import java.util.Collection;

public class LuckPermsAPI {

    public String getPlayerGroup(CommandSender player, Collection<String> vipGroups){
        User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
        if (user == null) return null;
        Collection<Node> nodes = user.data().toCollection();
        for (Node node : nodes) {
            for (String vipGroup : vipGroups) {
                if (node.getKey().equals("group." + vipGroup)) {
                    return vipGroup;
                }
            }
        }
        return null;
    }

    public boolean hasVIPPlayer(CommandSender player, Collection<String> vipGroups){
        return getPlayerGroup(player, vipGroups) != null;
    }

    public boolean isValidGroup(String group){
        return LuckPermsProvider.get().getGroupManager().getGroup(group) != null;
    }

    public Duration getExpiryTime(CommandSender player, String group){
        User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
        if (user == null) return null;
        Collection<Node> nodes = user.data().toCollection();
        for (Node node: nodes) {
            if (node.getKey().equals("group." + group)){
                return node.getExpiryDuration();
            }
        }
        return null;
    }

    public void addPlayerVIP(String player, String group, Integer days){
        User user = LuckPermsProvider.get().getUserManager().getUser(player);
        if (user == null) return;
        user.setPrimaryGroup(group);
        Node node = Node.builder("group." + group)
                .value(true)
                .expiry(Duration.ofDays(days))
                .build();
        user.data().clear(NodeType.INHERITANCE.predicate(mn -> mn.getKey().equals("group." + group)));
        user.data().add(node);
        LuckPermsProvider.get().getUserManager().saveUser(user);
    }

    public void removePlayerVIP(String player, String playerGroup){
        User user = LuckPermsProvider.get().getUserManager().getUser(player);
        if (user == null) return;
        Collection<Node> nodes = user.data().toCollection();
        for (Node node : nodes){
            if (node.getKey().equals("group." + playerGroup)){
                user.data().remove(node);
                LuckPermsProvider.get().getUserManager().saveUser(user);
            }
        }
    }
}
