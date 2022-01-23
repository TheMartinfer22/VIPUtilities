package dev.nanosync.viputilities.service;

import dev.nanosync.viputilities.VIPUtilities;
import dev.nanosync.viputilities.api.LuckPermsAPI;

import java.util.ArrayList;
import java.util.Collection;

public class VIPGroupService {
    private final static Collection<String> vipGroups = VIPUtilities.getInstance().getConfig().getStringList("VIPGroups");
    private final static LuckPermsAPI luckPermsAPI = new LuckPermsAPI();
    private final static Collection<String> verifiedGroups = new ArrayList<>();

    public static Collection<String> getVIPGroups(){
        if (verifiedGroups.size() < 1) {
            for (String group : vipGroups){
                if (luckPermsAPI.isValidGroup(group)){
                    verifiedGroups.add(group);
                }
            }
        }
        return verifiedGroups;
    }
}
