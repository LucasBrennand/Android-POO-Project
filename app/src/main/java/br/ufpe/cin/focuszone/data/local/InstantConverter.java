package br.ufpe.cin.focuszone.data.local;

import java.time.Instant;

import androidx.room.TypeConverter;

public class InstantConverter {

    @TypeConverter
    public static Long fromInstant(Instant instant) {
        return instant == null ? null : instant.toEpochMilli();
    }

    @TypeConverter
    public static Instant toInstant(Long epochMilli) {
        return epochMilli == null ? null : Instant.ofEpochMilli(epochMilli);
    }
}
