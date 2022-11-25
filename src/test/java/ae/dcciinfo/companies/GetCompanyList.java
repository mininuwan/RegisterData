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

public class GetCompanyList {
    private WebDriver driver;
    List< StartupCompany> startupCompanyList = new ArrayList<>();

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

        driver.get("https://www.startupsl.lk/masterSearchMainWindow");
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

        //link 1
        //h2[not(contains(@style,'display:none'))]
        //span[@itemprop='telephone']//b
        //a[contains(text(),'https')]


        //move down the web page
        //((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");
        //((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)"); // Charaka, this is working
        //driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.END); // Charaka, this is working

        for(int i=0; i<=10; i++)
        {
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.END); // Charaka, this is working
            //sleep for 2 seconds (2000 milli seconds)
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        //link 2 - 693 items
        //a[@class='startup_name']
        //a[@class='startup_tagline']
        //p[@class='card-text']
        //a[@class='startup_url']

        List<WebElement> allCards    =  driver.findElements(By.xpath("//div[@class='card container-fluid']"));
        int allCards_idx = 0;
        for(WebElement startupCard :allCards)
        {
            allCards_idx++;
            String curCardXpath = "(//div[@class='card container-fluid'])[" + allCards_idx + "]";

            System.out.println("Startup: " + allCards_idx);
            System.out.println("{");

            String startupName      = driver.findElement(By.xpath(curCardXpath + "//a[@class='startup_name']")).getText();
            String startupTaglines  = driver.findElement(By.xpath(curCardXpath + "//a[@class='startup_tagline']")).getText();
            String cardTexts        = driver.findElement(By.xpath(curCardXpath + "//p[@class='card-text']")).getText();
            String startupURLs      = driver.findElement(By.xpath(curCardXpath + "//a[@class='startup_url']")).getText();

            System.out.println("  " + startupName);
            System.out.println("  " + startupTaglines);
            System.out.println("  " + cardTexts);
            System.out.println("  " + startupURLs);


            System.out.println("  " + "Founder");
            System.out.println("  " + "{");

            String curFounderXpath = curCardXpath + "//div[normalize-space()='Founder']";
            List<WebElement> curFounders = driver.findElements(By.xpath(curFounderXpath));
            int curFounder_idx = 0;
            for(WebElement curFounder :curFounders)
            {
                curFounder_idx++;
                if(curFounder.isDisplayed()){
                    String curFoundersItemsXpath = "(" + curFounderXpath + ")[" + curFounder_idx + "]//following-sibling::div";
                    List<WebElement> curFoundersItems = driver.findElements(By.xpath(curFoundersItemsXpath));
                    for(int i=0; i<curFoundersItems.size(); i++)
                    {
                        WebElement curFoundersItem = curFoundersItems.get(i);
                        System.out.println("  " + "  " + curFoundersItem.getText());
                    }
                }
            }
            System.out.println("  " + "}");

            System.out.println("  " + "Startup Catogory");
            System.out.println("  " + "{");

            String startupCatogories_xpath = curCardXpath + "//div[normalize-space()='Startup Catogory']";
            List<WebElement> startupCatogories = driver.findElements(By.xpath(startupCatogories_xpath));
            int startupCatogory_idx = 0;
            for(WebElement startupCatogory :startupCatogories)
            {
                startupCatogory_idx++;
                if(startupCatogory.isDisplayed()){
                    String startupCatogoryItems_xpath = "(" + startupCatogories_xpath + ")[" + startupCatogory_idx + "]//following-sibling::div";
                    List<WebElement> startupCatogoryItems = driver.findElements(By.xpath(startupCatogoryItems_xpath));
                    for(int i=0; i<startupCatogoryItems.size(); i++)
                    {
                        WebElement startupCatogoryItem = startupCatogoryItems.get(i);
                        System.out.println("  " + "  " + startupCatogoryItem.getText());
                    }
                }
            }
            System.out.println("  " + "}");
            System.out.println("}");
            System.out.println(" ");
        }


        /*
        List<WebElement> allStartupNames    =  driver.findElements(By.xpath("//a[@class='startup_name']"));
        List<WebElement> allStartupTaglines =  driver.findElements(By.xpath("//a[@class='startup_tagline']"));
        List<WebElement> allCardTexts       =  driver.findElements(By.xpath("//p[@class='card-text']"));
        List<WebElement> allStartupURLs     =  driver.findElements(By.xpath("//a[@class='startup_url']"));

        int rowCount = allStartupNames.size(); //get all rows of the table
        int idx = 0;
        */

        /*
        for(WebElement startupName :allStartups)
        {
            idx++;
            System.out.println(idx + " " + startupName.getText());
        }
        */

        /*
        for (int i = 0; i <allStartupNames.size(); i++) {
            idx++;

            System.out.println("Startup: " + idx);
            System.out.println("{");
            System.out.println("    " + allStartupNames.get(i).getText());
            System.out.println("    " + allStartupTaglines.get(i).getText());
            System.out.println("    " + allCardTexts.get(i).getText());
            System.out.println("    " + allStartupURLs.get(i).getText());
            System.out.println("}");
            System.out.println(" ");
        }
        */



        //sleep for 2 seconds (2000 milli seconds)
        try {
            sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
