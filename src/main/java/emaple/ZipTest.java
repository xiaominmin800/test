package emaple;

import baseinfo.JsonUtil;
import baseinfo.httpclient;
import net.sf.json.JSONObject;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileReader;

/**
 * created by chenminqing
 */
public class ZipTest {

    public static void main(String[] args) throws Exception {
        JSONObject boys = new JSONObject();
        boys = httpclient.sendGet("http://47.97.171.60:30001/eaglehorn-admin/api/v1/auth/getCrypter","");
        String publicExponent= JsonUtil.parseJsonStr(boys.toString(), "publicExponent").get(0).toString();
        String publicModulus= JsonUtil.parseJsonStr(boys.toString(), "publicModulus").get(0).toString();
        String sessionId= JsonUtil.parseJsonStr(boys.toString(), "sessionId").get(0).toString();



        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        //File file = new File("src/main/resources/jsss.js");
        String jsFileName="src/main/resources/security.js";
        FileReader reader = new FileReader(jsFileName);   // 执行指定脚本
        engine.eval(reader);
        if(engine instanceof Invocable) {
            Invocable invoke = (Invocable)engine;    // 调用merge方法，并传入两个参数
            Object key = invoke.invokeFunction("getKeyPair",publicExponent,"",publicModulus);
            String encryptedString =(String) invoke.invokeFunction("encryptedString",key,"123456A-");
            System.out.println(encryptedString);


            //Double c = (Double)invoke.invokeFunction("aa", 2, 3); //调用了js的aa方法
            //System.out.println(c);
        }

        // engine.eval("alert(\"js alert\");");    // 不能调用浏览器中定义的js函数 // 错误，会抛出alert引用不
    }


}
