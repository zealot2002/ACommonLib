package com.zzy.commonlib.http;

import org.json.JSONException;

public interface HInterface {
    interface DataCallback{
        void requestCallback(boolean bResult, final Object data, Object tagData);
    }

    interface JsonParser {
        Object[] parse(String str) throws JSONException;
    }

    interface Decrypter {
        String decrypt(String s);
    }

    interface Validator {
        void validate(Object obj) throws NetDataInvalidException;
    }
}