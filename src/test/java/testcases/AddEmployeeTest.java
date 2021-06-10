package testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AddEmployeePage;
import pages.DashBoardPage;
import pages.EmployeeListPage;
import pages.LoginPage;
import utils.CommonMethod;
import utils.ConfigReader;
import utils.Constants;
import utils.ExcelReading;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AddEmployeeTest extends CommonMethod {

    @Test(groups = "smoke")
    public void addEmployee(){
        LoginPage loginPage=new LoginPage();
        loginPage.login(ConfigReader.getPropertyValue("username"), ConfigReader.getPropertyValue("password"));

        DashBoardPage dash=new DashBoardPage();
        click(dash.pimOption);
        click(dash.addEmployeeButton);


        AddEmployeePage add=new AddEmployeePage();
        sendText(add.firstName, "syntax");
        sendText(add.lastName, "technologies");
        click(add.saveBtn);
    }

    @Test
    public void addMultipleEmployees() throws InterruptedException {
        LoginPage loginPage=new LoginPage();   //creating object for Login pg
        loginPage.login(ConfigReader.getPropertyValue("username"),ConfigReader.getPropertyValue("password"));


        //NAVIGATING TO employee pg
        DashBoardPage dash=new DashBoardPage(); //created objects of all pgs in one place for easier access
        EmployeeListPage emplist=new EmployeeListPage();
         AddEmployeePage addEmployeePage=new AddEmployeePage();

        List<Map<String, String>> newEmployees= ExcelReading.excelIntoListMap(Constants.TESTDATA_FILEPATH,"EmployeeDataFrameWork");

        SoftAssert softAssert=new SoftAssert();
        Iterator<Map<String, String>> it=newEmployees.iterator(); //we need to iterate through all employees
        while(it.hasNext()){
            click(dash.pimOption);
            click(dash.addEmployeeButton);
            Map<String, String > mapNewEmployee= it.next();
            sendText(addEmployeePage.firstName, mapNewEmployee.get("FirstName"));   //passing first name
            sendText(addEmployeePage.middleName, mapNewEmployee.get("MiddleName"));
            sendText(addEmployeePage.lastName, mapNewEmployee.get("LastName"));
            String employeeIDValue = addEmployeePage.employeeId.getAttribute("value"); //storing the empID value
            sendText(addEmployeePage.photograph, mapNewEmployee.get("Photograph")); //path to photograph


            //select checkbox
            if(!addEmployeePage.createLoginCheckBox.isSelected()){  //it will check if it is NOT SELECTED
              //  addEmployeePage.createLoginCheckBox.click();
                click(addEmployeePage.createLoginCheckBox);
            }

            //add login credentials for user
            sendText(addEmployeePage.usernamecreate, mapNewEmployee.get("Username"));
            sendText(addEmployeePage.userpassword, mapNewEmployee.get("Password"));
            sendText(addEmployeePage.repassword,mapNewEmployee.get("Password"));
            click(addEmployeePage.saveBtn);

            //navigate to the employee list
            click(dash.pimOption);
            click(dash.employeeListOption);
          //  click(dash.addEmployeeButton);

            //enter employee id
            waitForClickability(emplist.idEmployee);
            sendText(emplist.idEmployee,employeeIDValue);
            click(emplist.searchButton); //searching the data for employee

            List<WebElement> rowData= driver.findElements(By.xpath("//table[@id= 'resultTable']/tbody/tr"));
            for (int i = 0; i <rowData.size() ; i++) {
                System.out.println("I am inside the loop");
                String rowText=rowData.get(i).getText();
                System.out.println(rowText);  //checking whatever data we got
                Thread.sleep(10000);
                String expectedEmployeeDetails= employeeIDValue+ " "+ mapNewEmployee.get("FirstName")+ " "+mapNewEmployee.get("MiddleName")+ " "+mapNewEmployee.get("LastName");

                softAssert.assertEquals(rowText, expectedEmployeeDetails);

            }
        }

        softAssert.assertAll();

    }


}
