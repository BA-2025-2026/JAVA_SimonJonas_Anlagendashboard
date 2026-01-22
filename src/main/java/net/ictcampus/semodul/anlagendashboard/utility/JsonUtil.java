package net.ictcampus.semodul.anlagendashboard.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Utility class using GSON (JSON parsing library by Google) to easily parse a java object to a JSON string: toJson();
 * Works the other way round too: Create a java object from a JSON string: fromJson();
 */
public class JsonUtil {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }
}
