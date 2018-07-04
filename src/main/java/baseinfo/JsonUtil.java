package baseinfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class JsonUtil {

    private static List<Object> values;
    /**
     * 解析json字符串中指定key的value
     *
     * @param jsonString
     *            json字符串
     * @param key
     *            json字符串中的key
     *
     * @return Object key对应的value
     *
     */
    public static List<Object> parseJsonStr(String jsonString, String key) {
        values = new ArrayList<Object>();
        if (jsonString != null || key != null) {
            if (jsonString.startsWith("[")) {
                JSONArray jsonArr = JSONArray.fromObject(jsonString);
                getValueFromJSONArray(jsonArr, key);
            } else if (jsonString.startsWith("{")) {
                JSONObject jsonObj = JSONObject.fromObject(jsonString);
                getValueFromJSONObject(jsonObj, key);
            }
        }
        return values;
    }


    /**
     * 递归解析JSONArray中的指定key
     */
    private static void getValueFromJSONArray(JSONArray jsonArr, String key) {
        for (int i = 0; i < jsonArr.size(); i++) {
            Object obj = jsonArr.get(i);
            if (obj.toString().startsWith("[")) {
                JSONArray subArr = JSONArray.fromObject(obj);
                getValueFromJSONArray(subArr, key);
            } else if (obj.toString().startsWith("{")) {
                JSONObject subObj = JSONObject.fromObject(obj);
                getValueFromJSONObject(subObj, key);
            }
        }
    }


    /**
     * 递归解析JSONObject中的指定key
     */
    private static void getValueFromJSONObject(JSONObject jsonObj, String key) {
        Object value = null;
        if (jsonObj.containsKey(key)) {// json字符串的查找，终结于所有的JSONObject
            value = jsonObj.get(key);
            values.add(value);
        }
        Set keys = jsonObj.keySet();
        Iterator<String> keyIt = keys.iterator();
        while (keyIt.hasNext()) {
            String onekey = keyIt.next();
            Object vObj = jsonObj.get(onekey);
            if (vObj.toString().startsWith("{")) {
                try {
                    JSONObject subJs = JSONObject.fromObject(vObj);
                    getValueFromJSONObject(subJs, key);
                } catch (Exception e) {
                    //do nothing
                }
            } else if (vObj.toString().startsWith("[")) {
                JSONArray subArr = JSONArray.fromObject(vObj);
                getValueFromJSONArray(subArr, key);
            }
        }
    }

}
