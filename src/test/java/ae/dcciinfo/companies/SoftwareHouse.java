package ae.dcciinfo.companies;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Thread.sleep;

public class SoftwareHouse {
    private WebDriver driver;

    @BeforeAll
    public static void setupDriver() {
        WebDriverManager.chromedriver().setup();
        //WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();

        driver.get("https://dcciinfo.ae/software-house-dubai/7682");
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void getCompanyList() throws IOException {
        wait_MiliSec(2000);

        for(int i=1; i<=63; i++)
        {
            //move down the web page
            //((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
            //((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.END); // Charaka, this is working
            wait_MiliSec(1000);

            WebElement loadMoreBtn = driver.findElement(By.xpath("//button[@id='load-more-btn']"));
            loadMoreBtn.click();

            wait_MiliSec(2000);
        }

        //excel file related configurations
        //File src = new File("D:\\BISTEC LAB\\RegisterData\\DharaData.xlsx");
        File src = new File("/Users/charakaw/Downloads/My_Apps/SoftwareHouse.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("New Sheet");
        int excelRowCount = 0;

        List<WebElement> first10ComNames    = driver.findElements(By.xpath("//div[@class='comp-box-header']/div/h2/a"));
        List<WebElement> otherComNames      = driver.findElements(By.xpath("//div[@class='comp-box-header']/div/strong/a"));
        List<WebElement> phoneNos           = driver.findElements(By.xpath("//a[@class='telephoneClicks dark-blue']"));
        List<WebElement> categoryNames      = driver.findElements(By.xpath("//h4[@class='panel-title']/span[contains(text(),'Category')]//following-sibling::a"));

        for(int i=0; i<first10ComNames.size(); i++)
        {
            WebElement first10ComName   = first10ComNames.get(i);
            WebElement phoneNo          = phoneNos.get(i);
            WebElement categoryName     = categoryNames.get(i);

            System.out.println("Company " + (i+1));
            System.out.println("  Name:       " + first10ComName.getText());
            System.out.println("  Phone:      " + phoneNo.getText());
            System.out.println("  Category:   " + categoryName.getText());
            System.out.println(" ");

            sheet.createRow(excelRowCount).createCell(0).setCellValue(first10ComName.getText());
            sheet.getRow(excelRowCount).createCell(1).setCellValue(phoneNo.getText());
            sheet.getRow(excelRowCount).createCell(2).setCellValue(categoryName.getText());
            excelRowCount++;
        }

        for(int i=0; i<otherComNames.size(); i++)
        {
            WebElement otherComName = otherComNames.get(i);

            String str_phoneNo = "";
            if(635 >= i+10) {
                WebElement phoneNo = phoneNos.get(i+10);
                str_phoneNo = phoneNo.getText();
            }
            else{
                str_phoneNo = "";
            }

            String str_categoryName = "";
            if(635 >= i+10) {
                WebElement categoryName = categoryNames.get(i+10);
                str_categoryName = categoryName.getText();
            }
            else{
                str_categoryName = "";
            }

            System.out.println("Company " + (i+1+10));
            System.out.println("  Name:       " + otherComName.getText());
            System.out.println("  Phone:      " + str_phoneNo);
            System.out.println("  Category:   " + str_categoryName);
            System.out.println(" ");

            sheet.createRow(excelRowCount).createCell(0).setCellValue(otherComName.getText());
            sheet.getRow(excelRowCount).createCell(1).setCellValue(str_phoneNo);
            sheet.getRow(excelRowCount).createCell(2).setCellValue(str_categoryName);
            excelRowCount++;

            if((i+1+10) == 635){
                break;
            }
        }

        //create the output excel file and save data into it then close the file
        FileOutputStream fileOutputStream = new FileOutputStream(src);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();
    }

    void wait_MiliSec(int miliSeconds){
        if(miliSeconds == 0){
            miliSeconds = 1000;
        }
        //sleep for 2 seconds (2000 milli seconds)
        try {
            sleep(miliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
