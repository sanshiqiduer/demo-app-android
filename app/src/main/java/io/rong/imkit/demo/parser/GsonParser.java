package io.rong.imkit.demo.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.MalformedJsonException;
import com.sea_monster.core.exception.InternalException;
import com.sea_monster.core.exception.ParseException;
import com.sea_monster.core.network.StatusCallback;
import com.sea_monster.core.network.parser.IEntityParser;

import org.apache.http.HttpEntity;
import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * Created by DragonJ on 14-7-15.
 */
public class GsonParser<T extends Serializable> extends JsonObjectParser<T> {


    Type type;
    Gson gson;
    public GsonParser(Class<T> type)
    {
        gson = new Gson();
        this.type = type;
    }


    @Override
    public T jsonParse(JsonReader reader) throws MalformedJsonException, JSONException, IOException, ParseException, InternalException {
        return gson.fromJson(reader,this.type);
    }
}

