package io.rong.imkit.demo.parser;

import com.sea_monster.core.exception.InternalException;
import com.sea_monster.core.exception.ParseException;
import com.sea_monster.core.network.StatusCallback;
import com.sea_monster.core.network.parser.IEntityParser;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import io.rong.imkit.demo.model.Status;

public class RegisterParser implements IEntityParser<Status> {


    @Override
    public Status parse(HttpEntity httpEntity) throws IOException, ParseException, InternalException {
        if (EntityUtils.toString(httpEntity).equals("ok")) {
            Status status = new Status();
            status.setCode(200);
            return status;
        }
        return null;
    }

    @Override
    public Status parse(HttpEntity httpEntity, StatusCallback<?> statusCallback) throws IOException, ParseException, InternalException {
        return null;
    }

    @Override
    public Status parseGzip(HttpEntity httpEntity) throws IOException, ParseException, InternalException {
        if (EntityUtils.toString(httpEntity).equals("ok")) {
            Status status = new Status();
            status.setCode(200);
            return status;
        }
        return null;
    }

    @Override
    public Status parseGzip(HttpEntity httpEntity, StatusCallback<?> statusCallback) throws IOException, ParseException, InternalException {
        return null;
    }

}
