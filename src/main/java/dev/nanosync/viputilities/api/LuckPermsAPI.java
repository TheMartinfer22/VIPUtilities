package dev.nanosync.viputilities.api;

import dev.nanosync.viputilities.service.VIPGroupService;
import net.luckperms.api.LuckPermsProvider;

import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;

import java.time.Duration;
import java.util.*;

public class LuckPermsAPI {

    public String getPlayerGroup(String player, Collection<String> vipGroups){
        User user = getLuckPermsUser(player);
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

    public boolean hasVIPPlayer(String player, Collection<String> vipGroups){
        return getPlayerGroup(player, vipGroups) != null;
    }

    public boolean isValidGroup(String group){
        return LuckPermsProvider.get().getGroupManager().getGroup(group) != null;
    }

    public long getExpiryTime(String player, String group){
        User user = getLuckPermsUser(player);
        if (user == null) return 0;
        Collection<Node> nodes = user.data().toCollection();
        for (Node node: nodes) {
            if (node.getKey().equals("group." + group)){
                return node.getExpiryDuration().getSeconds();
            }
        }
        return 0;
    }

    public void addPlayerVIP(String player, String group, Integer days){
        User user = getLuckPermsUser(player);
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
        User user = getLuckPermsUser(player);
        if (user == null) return;
        Collection<Node> nodes = user.data().toCollection();
        for (Node node : nodes){
            if (node.getKey().equals("group." + playerGroup)){
                user.data().remove(node);
                LuckPermsProvider.get().getUserManager().saveUser(user);
            }
        }
    }

    public Map<String, String> getPlayersVIP(){
        Set<User> loadedUsers = LuckPermsProvider.get().getUserManager().getLoadedUsers();
        Map<String, String> vipPlayers = new HashMap<>();
        for (User user : loadedUsers){
            Collection<Node> nodes = user.data().toCollection();
            for (Node node : nodes){
                for (String vipGroup : VIPGroupService.getVIPGroups()){
                    if (node.getKey().equals("group." + vipGroup)){
                        vipPlayers.put(user.getUsername(), vipGroup);
                    }
                }
            }
        }
        return vipPlayers;
    }

    public String getGroupDisplayName(String group){
        String displayName = LuckPermsProvider.get().getGroupManager().getGroup(group).getDisplayName();
        return Objects.requireNonNullElse(displayName, group);
    }

    private User getLuckPermsUser(String player){
        return LuckPermsProvider.get().getUserManager().getUser(player);
    }
}
