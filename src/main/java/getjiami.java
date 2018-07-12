
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

import baseinfo.JsonUtil;
import baseinfo.httpclient;
import com.sun.tools.javac.tree.JCTree;
import net.sf.json.JSONObject;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;


public class getjiami {
    @Test
    public  void gettest() {

        JSONObject boys = new JSONObject();
        boys = httpclient.sendGet("http://47.97.171.60:30026/eaglehorn-risk-data/api/v1/engine/derivative-factors/1143","");
        System.out.println(boys.get("status"));
        String sta= JsonUtil.parseJsonStr(boys.toString(), "status").get(0).toString();
        System.out.println("这个status为："+sta);
        AssertJUnit.assertEquals(sta, "200");
        Logger log = Logger.getLogger("");
        log.info(sta);
    }
}
