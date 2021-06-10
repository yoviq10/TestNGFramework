package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethod;
import utils.ConfigReader;

public class LoginPage extends CommonMethod {

    @FindBy(id= "txtUsername")   //our object repository used to find the elements
    public WebElement usernamebox;

    @FindBy(id= "txtPassword")
   public WebElement passwordbox;  //make sure its public

    @FindBy(id= "btnLogin")
    public WebElement loginBtn;  //make sure it is public to access on LoginTest class


    @FindBy(id= "spanMessage")
    public WebElement errormessage;

    public LoginPage(){     //creating a constructor to initialize an object
        PageFactory.initElements(driver, this);
    }


/*
there are 3 ways to bring the driver on line 22 to work, either EXTENDs class to "commonMehtod"
 or  "PageFactory.initElements(CommonMethods.driver,this);" for line 22
 or include it in line 21 "LoginPage(WebElement, driver)"
*/


    //login Method
    public void login(String username, String password){
        sendText(usernamebox, username);
        sendText(passwordbox, password);
        click(loginBtn);    }


}
