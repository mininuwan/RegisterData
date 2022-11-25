package lk.startupsrilanka.startups;

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

public class StartupCompanies {
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

        driver.get("https://www.startupsrilanka.lk/search-users/?sectors_checkbox=Startup&sort=ASC&sectorsort_btn=Submit");
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void getCompanyList() throws IOException {
        //sleep for 2 seconds (2000 milli seconds)
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //String allCardsXpath = "//div[@class='category-card search-card new-eco-card']";
        String allCardsXpath = "//a[contains(@href, '/search-user/?id')]";
        List<WebElement> allCards    =  driver.findElements(By.xpath(allCardsXpath));
        int allCards_idx = 0;
        String[] urlArray = new String[allCards.size()];
        for(WebElement startupCard :allCards)
        {
            String hrefValue = startupCard.getAttribute("href");
            urlArray[allCards_idx] = hrefValue;
            allCards_idx++;

            //System.out.println(hrefValue);
            //driver.navigate().to(hrefValue);
        }

        for(int i=0; i< urlArray.length; i++)
        {
            driver.navigate().to(urlArray[i]);
            //sleep for 2 seconds (2000 milli seconds)
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(driver.findElement(By.xpath("//h5")).isDisplayed())
            {
                String strName          = driver.findElement(By.xpath("//h5")).getText();
                String strInterested    = driver.findElement(By.xpath("(//p[@class='colom-text'])[1]")).getText();
                String strSector        = driver.findElement(By.xpath("(//p[@class='colom-text'])[2]")).getText();
                String strProfile       = driver.findElement(By.xpath("(//p[@class='colom-text'])[3]")).getText();
                String strStageFocus    = driver.findElement(By.xpath("(//p[@class='colom-text'])[4]")).getText();
                String strSectorFocus   = driver.findElement(By.xpath("(//p[@class='colom-text'])[5]")).getText();
                String strRevenue       = driver.findElement(By.xpath("(//p[@class='colom-text'])[6]")).getText();

                System.out.println(strName);
                System.out.println("{");
                System.out.println("  URL: " + urlArray[i]);
                System.out.println("  Interested in: " + strInterested);
                System.out.println("  Sector Interested in: " + strSector);
                System.out.println("  Startup Ecosystem profile: " + strProfile);
                System.out.println("  Stage Focus: " + strStageFocus);
                System.out.println("  Sector Focus: " + strSectorFocus);
                System.out.println("  Annual Revenue (USD): " + strSectorFocus);
                System.out.println("}");
                System.out.println(" ");
            }
        }
    }
}
