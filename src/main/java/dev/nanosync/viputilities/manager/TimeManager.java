package dev.nanosync.viputilities.manager;

public class TimeManager {

    private final long seconds;

    public TimeManager(long seconds){
        this.seconds = seconds;
    }

    public long getDays(){
        if (seconds >= 86400) return (seconds / 86400);
        return 0;
    }

    public long getHours(){
        if (seconds >= 3600) {
            if (seconds / 3600 > 24) return 24;
            return (seconds / 3600);
        }
        return 0;
    }

    public long getMinutes(){
        if (seconds >= 60) {
            if (seconds / 60 > 60) return 60;
            return (seconds / 60);
        }
        return 0;
    }
}
