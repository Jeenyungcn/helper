package online.jeenyung.helper.utils;

import java.util.UUID;

public class UUIDTool
{
    public static Long getLong()
    {
        return UUID.randomUUID().getMostSignificantBits();
    }
}
