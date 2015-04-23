package tw.supra.lib.supower.model;

import android.content.ContentValues;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

public abstract class ModelObj {
	public final UUID ID = UUID.randomUUID();
	   
    protected static void checkAndPutString(ContentValues values, HashMap<String, String> map) {
        for (Entry<String, String> item : map.entrySet()) {
            String key = item.getKey();
            String value = item.getValue();
            if (!isEmpty(value)) {
                values.put(key, value);
            }
        }
    }

    protected static void checkAndPutInt(ContentValues values, HashMap<String, Integer> map) {
        for (Entry<String, Integer> item : map.entrySet()) {
            String key = item.getKey();
            int value = item.getValue();
            if (!isEmpty(value)) {
                values.put(key, value);
            }
        }
    }

    protected static void checkAndPutLong(ContentValues values, HashMap<String, Long> map) {
        for (Entry<String, Long> item : map.entrySet()) {
            String key = item.getKey();
            long value = item.getValue();
            if (!isEmpty(value)) {
                values.put(key, value);
            }
        }
    }

    protected static boolean isEmpty(String value) {
        return TextUtils.isEmpty(value);
    }

    protected static boolean isEmpty(int value) {
        return (-1 == value) ? true : false;
    }

    protected static boolean isEmpty(long value) {
        return (-1 == value) ? true : false;
    }
}
