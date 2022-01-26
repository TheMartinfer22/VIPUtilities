package dev.nanosync.viputilities.manager;

public class TimeManager {

    private final long seconds;

    public TimeManager(long seconds){
        this.seconds = seconds;
    }

    public long getDays(){
        return seconds/60/60/24;
    }

    public long getHours(){
        return (seconds/60/60) % 24;
    }

    public long getMinutes(){
        return (seconds/60) % 60;
    }
}
