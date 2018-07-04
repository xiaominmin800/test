package log;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviderLearn {


    @Test(dataProvider="user")
    public void verifyUser(String userName, String password){
        System.out.println("Username: "+ userName + ";   Password: "+ password);
    }


    @DataProvider(name="user")
    public Object[][] Users(){
        return new Object[][]{
                {"root","passowrd"},
                {"cnblogs.com", "tankxiao"},
                {"tank","xiao"}
        };
    }
}
