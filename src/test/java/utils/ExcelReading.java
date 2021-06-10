package utils;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelReading {


    static Workbook book;
    static Sheet sheet;

    public static void openExel(String filePath){    //starting from workbook
        try {
            FileInputStream fis=new FileInputStream(filePath);
            book= new XSSFWorkbook(fis);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void getSheet(String sheetName){
        sheet=book.getSheet(sheetName);              //getting your sheet

    }

    public static int getRowCount(){
        return sheet.getPhysicalNumberOfRows();   //getting # of rows
    }


    public static int getColsCount(int rowIndex){
        return sheet.getRow(rowIndex).getPhysicalNumberOfCells();  //getting # of columns
    }


    public static String getCellData(int rowIndex, int colIndex){
        return sheet.getRow(rowIndex).getCell(colIndex).toString(); //getting the # of cells
    }


    public static List<Map<String, String>> excelIntoListMap(String filePath, String sheetName){   //passing filepath & sheetName
        openExel(filePath);
        getSheet(sheetName);  //getting sheetName

        List<Map<String, String>> ListData=new ArrayList<>(); //(inside List, there is a map) will allow to keep duplicate values

        //outer loop
        for(int row=1;row<getRowCount(); row++){  //starting from row 1 because we are creating a (map for keys & values) rows for every/all single rows except the Header

            Map<String, String> map= new LinkedHashMap<>();//creating a map for every row
            //looping through the values of all cells
            for(int col=0;col<getColsCount(row); col++){  //starting columns from 0 (dynamic)
                map.put(getCellData(0,col), getCellData(row,col)); //to add the values into the map we use the .put
            }
            ListData.add(map);  //adding it to the list

        }
        return ListData;  //returning 1 list
    }


}
