package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CommonMethod {

        public static WebDriver driver;

        @BeforeMethod(alwaysRun = true)
        public static void setUp(){
            ConfigReader.readProperties(Constants.CONFIGURATION_FILEPATH); //from constants class
            switch (ConfigReader.getPropertyValue("browser")){
                case "chrome":
                 //   System.setProperty("webdriver.chrome.driver", "src/drivers/chromedriver.exe"); // we will remove this and use it from the constants class
                  WebDriverManager.chromedriver().setup(); //from constants class
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    //System.setProperty("webdriver.gecko.driver", "src/drivers/geckodriver.exe");
                 WebDriverManager.firefoxdriver().setup();//from constants class
                    driver = new FirefoxDriver();
                    break;
                default:
                    throw new RuntimeException("Invalid name of browser");
            }
            driver.get(ConfigReader.getPropertyValue("url"));
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT, TimeUnit.SECONDS); //from constants class
        }


        public static void sendText(WebElement element, String textToSend){
            element.clear(); //clearing the data
            element.sendKeys(textToSend);//passing the String, we can use this method anywhere, and many # of times
        }
         public  static WebDriverWait getWait(){
             WebDriverWait wait = new WebDriverWait(driver,Constants.EXPLICIT_WAIT); //providing wait from constants class
              return wait;
         }

         public static void waitForClickability(WebElement element) {
            getWait().until(ExpectedConditions.elementToBeClickable(element));
         }

        public static void click(WebElement element){
            waitForClickability(element);
            element.click();
        }

        public static JavascriptExecutor getJSExecutor(){      //
            JavascriptExecutor js=(JavascriptExecutor) driver; //JVSE now belongs to WebDriver (up casting)
            return js;
        }

        public static void jsClick(WebElement element){  //need to click on WebElement
            getJSExecutor().executeScript("arguments[0].click()", element);
        }

        public static void takeScreenShot(String fileName){
            TakesScreenshot ts=(TakesScreenshot)driver;
            File sourceFile=ts.getScreenshotAs(OutputType.FILE);

            try{
            FileUtils.copyFile(sourceFile, new File(Constants.SCREENSHOT_FILEPATH+fileName+ " "+ getTimeStamp("yyyy-MM-dd-HH-mm-ss")+ ".png"));
        }catch (IOException e){                                                                   //".png" > we moved this after timestamp
                e.printStackTrace();
            }
        }

        //getting the yyyy/day/month of timestamp
        public static String getTimeStamp(String pattern){
            Date date=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat(pattern);
            return sdf.format(date);
        }


      /*  @Test
        public void test(){
            System.out.println(System.getProperty("os.name"));
            System.out.println(System.getProperty("user.name"));
            System.out.println(System.getProperty("user.dir"));
        }*/



       @AfterMethod(alwaysRun = true)
        public static void tearDown(){
            if(driver!=null){
              driver.quit();
            }
        }
    }

