package testcases;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.DashBoardPage;
import pages.LoginPage;
import utils.CommonMethod;
import utils.ConfigReader;

public class LoginTest extends CommonMethod {

    @Test(groups = "smoke")
    public void adminLogin(){

        //login to HRMS
        LoginPage loginPage=new LoginPage();   //make sure it is public on LoginPage class>>line 20
        sendText(loginPage.usernamebox, ConfigReader.getPropertyValue("username"));
        sendText(loginPage.passwordbox, ConfigReader.getPropertyValue("password"));
        click(loginPage.loginBtn);

        //validation
        //assertion

        DashBoardPage dashboard=new DashBoardPage(); //used dashboard page from another class
        Assert.assertTrue(dashboard.welcomemessege.isDisplayed(),"welcome message is not displayed"); //hard assertion
    }

    @Test(dataProvider = "invalidData", groups = "sanity") // groups = "regression"  //groups = "sanity"
    public void invalidLoginErrorMessageValidation(String username, String password, String message){
    LoginPage loginPage=new LoginPage();
    sendText(loginPage.usernamebox, username);
    sendText(loginPage.passwordbox, password);
    click(loginPage.loginBtn);

    String actualError=loginPage.errormessage.getText();

    Assert.assertEquals(actualError, message, "Error message is not matched");

    }



    @DataProvider
    public Object [] [] invalidData() {
        Object[][] data = {
                {"James", "123!", "Invalid credentials"},
                {"Admin1", "Syntax123!", "Invalid credentials"},
                {"James", "", "Password cannot be empty"},
                {"", "Syntax123!", "Username cannot be empty"},
        };

        return data;

    }

}
