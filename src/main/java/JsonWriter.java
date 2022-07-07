import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class JsonWriter {
    private static JsonWriter instance;

    private JsonWriter() {
    }

    public static JsonWriter get() {
        if (instance == null) {
            instance = new JsonWriter();
        }
        return instance;
    }


    protected String listToJSON(List<PageEntry> list) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Type listType = new TypeToken<List<PageEntry>>() {
        }.getType();
        return gson.toJson(list, listType);
    }


}