package dev.nanosync.viputilities.manager;

import dev.nanosync.viputilities.VIPUtilities;

public class ConfigManager {
    public static String getConfigMessage(String configPath){
        return VIPUtilities.getInstance().getConfig().getString(configPath)
                .replace("&", "§");
    }

    public static String getMessageByConfigExpiry(String configPath, String playername, long days, long hours, long minutes) {
        return VIPUtilities.getInstance().getConfig().getString(configPath)
                .replace("{player}", playername)
                .replace("{days}", days + " " + getTimeString(days, "DAYS"))
                .replace("{hours}", hours + " " + getTimeString(hours, "HOURS"))
                .replace("{minutes}", minutes + " " + getTimeString(minutes, "MINUTES"))
                .replace("&", "§");
    }

    private static String getTimeString(long time, String path){
        String timeString = getConfigMessage(path);
        if (time == 1){
            timeString = timeString.substring(0, timeString.length() - 1);
            return timeString;
        }
        return timeString;
    }

}
