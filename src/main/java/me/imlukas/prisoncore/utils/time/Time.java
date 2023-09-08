package me.imlukas.prisoncore.utils.time;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

/**
 * Represents a time value.
 */
public class Time {

    private final int time;
    private final TimeUnit unit;

    public Time(int time, TimeUnit unit) {
        this.time = time;
        this.unit = unit;
    }


    public static Time sinceEpoch(long epoch) {
        long currentEpoch = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        return new Time((int) (currentEpoch - epoch), TimeUnit.SECONDS);
    }

    public static Time parseTime(String time) {
        String[] split = time.split(" ");

        if (split.length != 2) {
            System.err.println("Invalid time format: " + time);
            return null;
        }

        int timeValue = Integer.parseInt(split[0]);
        TimeUnit timeUnit = TimeUnit.valueOf(split[1].toUpperCase());
        return new Time(timeValue, timeUnit);
    }

    /**
     * Converts the time to the specified unit.
     *
     * @param unit The unit to convert to
     * @return The converted time
     */
    public long as(TimeUnit unit) {
        return unit.convert(time, this.unit);
    }

    /**
     * Converts the time to ticks.
     *
     * @return The converted time
     */
    public long asTicks() {
        return as(TimeUnit.MILLISECONDS) / 50;
    }

    public Time add(Time time) {
        return new Time((int) (as(TimeUnit.MILLISECONDS) + time.as(TimeUnit.MILLISECONDS)), TimeUnit.MILLISECONDS);
    }

    public Time subtract(Time time) {
        return new Time((int) (as(TimeUnit.MILLISECONDS) - time.as(TimeUnit.MILLISECONDS)), TimeUnit.MILLISECONDS);
    }


    @Override
    public String toString() {
        return time + " " + unit.name().toLowerCase();
    }
}
