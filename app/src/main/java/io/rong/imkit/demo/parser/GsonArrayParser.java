package io.rong.imkit.demo.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.MalformedJsonException;
import com.sea_monster.core.exception.InternalException;
import com.sea_monster.core.exception.ParseException;

import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DragonJ on 14-7-15.
 */
public class GsonArrayParser<T extends Serializable> extends JsonObjectParser<ArrayList<T>>  {

    TypeToken<ArrayList<T>> type;
    Gson gson;
    public GsonArrayParser(TypeToken<ArrayList<T>> typeToken)
    {
        gson = new Gson();
        this.type = typeToken;
    }

    @Override
    public ArrayList<T> jsonParse(JsonReader reader) throws MalformedJsonException, JSONException, IOException, ParseException, InternalException {
        return gson.fromJson(reader,type.getType());
    }
}
