package io.mega;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.hc.core5.util.Asserts;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.SourceType;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Thread.sleep;

public class AssignmentTwo {
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

        driver.get("https://mega.io/desktop");
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
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

    @Test
    void assignmentTwoTest() throws IOException {
        boolean isFileDownloaded = false;
        wait_MiliSec(2000);


        WebElement linuxBtn = driver.findElement(By.xpath("(//a[@data-os='linux'])[1]"));
        linuxBtn.click();
        wait_MiliSec(3000);

        //WebElement optOS = driver.findElement(By.xpath("//div[@class='mega-input dropdown-input box-style inline megasync-dropdown active']"));
        WebElement optOS = driver.findElement(By.xpath("(//span[contains(text(),'Please select your Linux distribution')])[1]"));
        optOS.click();
        wait_MiliSec(1000);

        WebElement optMenuArchLinux = driver.findElement(By.xpath("//div[@data-client='Arch Linux']"));
        if(optMenuArchLinux.isDisplayed()){
            String dataLink = optMenuArchLinux.getAttribute("data-link");
            String[] arrDataLink = dataLink.split("/");
            for(int i = 0; i < arrDataLink.length; i++){
                //System.out.println(arrDataLink[i]);
            }
            //System.out.println("last item is");
            //System.out.println(arrDataLink[arrDataLink.length-1]);

            optMenuArchLinux.click();
            wait_MiliSec(2000);

            WebElement btnDownload = driver.findElement(By.xpath("(//button[@class='mega-button positive transition megaapp-linux-download download'])[1]"));
            btnDownload.click();
            wait_MiliSec(8000);

            File dir = new File("/Users/charakaw/Downloads");
            File[] dir_contents = dir.listFiles();
            for (int i = 0; i < dir_contents.length; i++){
                //System.out.println(dir_contents[i].getName());

                if(dir_contents[i].getName().equals(arrDataLink[arrDataLink.length-1])){
                    isFileDownloaded = true;
                    break;
                }
                else {
                    isFileDownloaded = false;
                }

                //data-client="elementary OS Juno"
                //data-link="https://mega.nz/linux/repo/xUbuntu_18.04/amd64/megasync-xUbuntu_18.04_amd64.deb"
            }
        }
        Assertions.assertTrue(isFileDownloaded, "File download is failed.");
    }
}
