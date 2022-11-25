package com.example.ui_tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.*;
import java.util.List;

import static java.lang.Thread.sleep;

public class MainTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupDriver()
    {
        WebDriverManager.chromedriver().setup();
        //WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    void setUp()
    {
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void openDharaSite() throws IOException {
        driver.get("https://www.tpb.gov.au/registrations_search?field_companies_state=nsw&conjunction=AND&search-type=advance&f%5B0%5D=field_registration_type_str%3ATax%20agent&f%5B1%5D=field_registration_status%3Aregistered");
        driver.manage().window().maximize();

        //move down the web page
        //((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");

        //sleep for 2 seconds (2000 milli seconds)
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //excel file related configurations
        //File src = new File("D:\\BISTEC LAB\\RegisterData\\DharaData.xlsx");
        File src = new File("/Users/charakaw/Downloads/My_Apps/RegisterData.xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("New Sheet");

        int excelRowCount = 0;
        //int pageCount = 2737; //this is the table's pagination count that we are going to process
        int pageCount = 2; //this is the table's pagination count that we are going to process

        //navigate each page by page of the table
        for(int i=0; i<=pageCount; i++ )
        {
            System.out.println("Page : " + i);

            List<WebElement> allRows =  driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/div[2]/div/div[2]/div/div/div/div[4]/table/tbody/tr"));
            int rowCount = allRows.size(); //get all rows of the table

            //List<WebElement> allCols =  driver.findElements(By.xpath("//*[@id=\"content\"]/div/div/div[2]/div/div[2]/div/div/div/div[4]/table/tbody/tr[1]/td"));
            //int colCount = allCols.size(); //get all columns of the table

            //go by each row and save column data into excel file
            for(int x=1; x<= rowCount; x++)
            {
                /*
                for(int y=1; y<=colCount; y++)
                {
                    WebElement cellValue = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/div[2]/div/div[2]/div/div/div/div[4]/table/tbody/tr["+ x +"]/td["+ y +"]"));
                    System.out.println(cellValue.getText());
                }
                */

                WebElement legalName = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/div[2]/div/div[2]/div/div/div/div[4]/table/tbody/tr["+ x +"]/td[1]"));
                WebElement businessName = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/div[2]/div/div[2]/div/div/div/div[4]/table/tbody/tr["+ x +"]/td[2]"));
                WebElement suburbName = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/div[2]/div/div[2]/div/div/div/div[4]/table/tbody/tr["+ x +"]/td[5]"));

                //System.out.println(legalName.getText() + " | " + businessName.getText() + " | " + suburbName.getText());

                sheet.createRow(excelRowCount).createCell(0).setCellValue(legalName.getText());
                sheet.getRow(excelRowCount).createCell(1).setCellValue(businessName.getText());
                sheet.getRow(excelRowCount).createCell(2).setCellValue(suburbName.getText());

                excelRowCount++;
            }

            //if we are in last page then dont click next btn
            if(i<pageCount)
            {
                WebElement nextBtn = driver.findElement(By.xpath("//a[@title='Go to next page']"));

                if(nextBtn.isDisplayed())
                {
                    nextBtn.click();
                    driver.navigate().refresh();
                }

                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");

                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //create the output excel file and save data into it then close the file
            FileOutputStream fileOutputStream = new FileOutputStream(src);
            workbook.write(fileOutputStream);
            fileOutputStream.close();
        }
        workbook.close();
    }
}
