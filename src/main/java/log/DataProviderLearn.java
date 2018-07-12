package log;

import baseinfo.httpclient;
import net.sf.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.Date;

public class DataProviderLearn {


    @Test(dataProvider="user")
    public void verifyUser(String userName, String password,String address,String city,String createTime,String content,String uuid,
                           String weather,String isSync,String deleted,String isHidden,String type,String device,String year,
                           String monthOfYear,String dayOfMonth,String updateTime,String objectId,String updatedAt,String userId,
                           String createAt,String orderNum,String updateAt,String dataVersion,String types,String token,String xdd
                           ){
        System.out.println("Username: "+ userName + ";   Password: "+ password);
        JSONObject bodys=new JSONObject();
        JSONObject contents = new JSONObject();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        JSONObject json = new JSONObject();
        json.put("address",address);
        json.put("city",city);
        createTime =""+new Date().getTime();
        json.put("createTime",createTime);
        json.put("content",content);
        json.put("uuid",uuid);
        json.put("weather",weather);
        json.put("isSync",isSync);
        json.put("deleted",deleted);
        json.put("isHidden",isHidden);
        json.put("type",type);
        json.put("device",device);
        json.put("year",year);
        json.put("monthOfYear",monthOfYear);
        json.put("dayOfMonth",dayOfMonth);
        updateTime = ""+new Date().getTime();
        json.put("updateTime",updateTime);
        list.add(json);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String createdAt =df.format(new Date()); // new Date()为获取当前系统时间
        contents.put("createdAt",createdAt);
        contents.put("json",list);
        contents.put("objectId",objectId);
        contents.put("updatedAt",createdAt);
        contents.put("userId",userId);
        contents.put("createAt",createAt);
        contents.put("orderNum",orderNum);
        contents.put("updateAt",updateAt);
        contents.put("dataVersion",dataVersion);
        contents.put("xdd",xdd);
        bodys.put("a",userName);
        bodys.put("w",password);
        bodys.put("content",contents);
        bodys.put("type","1");
        bodys.put("token",token);
        bodys.put("xdd",xdd);

        JSONObject response = new JSONObject();
        Logger log = Logger.getLogger("");
        log.info(bodys.toString());
        response = httpclient.sendPost("http://api.1diary.me/update",bodys.toString());






    }


    @DataProvider(name="user")
    public Object[][] Users(){
        return new Object[][]{
                {"cmq800@163.com","U2FsdGVkX1+4HuJOo0sSCsso/VpwZaNnJ2pwr9wGfnM=","","","","内容","057658d5-8b27-4bba-a5f0-e6b3bb6b3119",
                "","true","false","false","","MacIntel","2018",
                "7","11","1531318683978","","","",
                "","0","","0","1","64367a08ad","92d52679d8"},

        };
    }
}
