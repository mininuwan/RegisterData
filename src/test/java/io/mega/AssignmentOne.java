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

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Thread.sleep;

public class AssignmentOne {
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

        driver.get("https://mega.io");
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
    void assignmentOneTest() throws IOException {
        wait_MiliSec(5000);

        WebElement loginBtn = driver.findElement(By.xpath("(//button[@class='mega-button top-login-button'])[1]"));
        loginBtn.click();
        wait_MiliSec(5000);

        WebElement inputLoginName = driver.findElement(By.xpath("//input[@name='login-name2']"));
        WebElement inputLoginPassword = driver.findElement(By.xpath("//input[@name='login-password2']"));
        WebElement btnLogin = driver.findElement(By.xpath("//button[@class='mega-button positive login-button large right']"));

        inputLoginName.sendKeys("mininuwan@gmail.com");
        inputLoginPassword.sendKeys("Qwer1234@a1");
        btnLogin.click();
        wait_MiliSec(5000);

        Actions actions = new Actions(driver);
        WebElement rightClickBody = driver.findElement(By.xpath("//div[@class='megaList-content']"));
        actions.contextClick(rightClickBody).perform();
        wait_MiliSec(3000);

        WebElement optNewFile = driver.findElement(By.xpath("//span[text()='New text file']"));
        if(optNewFile.isDisplayed()){
            optNewFile.click();
            wait_MiliSec(3000);

            WebElement inputNewFileName = driver.findElement(By.xpath("//input[@name='dialog-new-file']"));
            String fileName = RandomStringUtils.randomAlphabetic(3);
            inputNewFileName.sendKeys(fileName);
            wait_MiliSec(3000);

            WebElement btnCreate = driver.findElement(By.xpath("//button[@class='mega-button positive fm-dialog-new-file-button create-file']"));
            btnCreate.click();
            wait_MiliSec(5000);

            WebElement txtBody = driver.findElement(By.xpath("//pre[@class=' CodeMirror-line ']/span"));
            String txtBodyContent = RandomStringUtils.randomAlphabetic(10);
            txtBodyContent = "megatesting";

            new Actions(driver).sendKeys(txtBodyContent).perform();

            //txtBody.sendKeys(Keys.ENTER);
            //JavascriptExecutor js = (JavascriptExecutor)driver;
            //js.executeScript("arguments[0].innerHTML='Berlín'", txtBody);

            WebElement btnSave = driver.findElement(By.xpath("//button[@class='mega-button positive gradient save-btn']"));
            btnSave.click();
            wait_MiliSec(5000);

            WebElement btnCloseTextArea = driver.findElement(By.xpath("(//button[@class='close-btn']) [2]"));
            btnCloseTextArea.click();
            wait_MiliSec(5000);

            String xPathFileName = "(//span[text()='" + fileName + ".txt']) [1]";
            //span[text()='OHZ.txt']
            //span[@class='data-block-bg ']
            WebElement savedFile = driver.findElement(By.xpath(xPathFileName));
            actions.contextClick(savedFile).perform();
            wait_MiliSec(3000);


            WebElement moveToBin = driver.findElement(By.xpath("//a[@class='dropdown-item remove-item']"));
            if(moveToBin.isDisplayed()){
                moveToBin.click();
                wait_MiliSec(3000);

                WebElement btnMoveToBinYes = driver.findElement(By.xpath("(//button[@class='mega-button positive confirm']) [1]"));
                btnMoveToBinYes.click();
                wait_MiliSec(3000);
            }

            //move to Rubbish bin
            WebElement btnBinMenu = driver.findElement(By.xpath("(//button[normalize-space()='Rubbish bin'])[1]"));
            btnBinMenu.click();
            wait_MiliSec(3000);

            //span[text()='sss.txt']
            String xRubbishFileName = "//span[text()='" + fileName + ".txt']";
            WebElement deletedFile = driver.findElement(By.xpath(xRubbishFileName));
            actions.contextClick(deletedFile).perform();
            wait_MiliSec(3000);

            WebElement optRestore = driver.findElement(By.xpath("//a[@class='dropdown-item revert-item']"));
            if(optRestore.isDisplayed()) {
                optRestore.click();
                wait_MiliSec(4000);
            }

            WebElement restoredFile = driver.findElement(By.xpath(xPathFileName));
            Assertions.assertTrue(restoredFile.isDisplayed(),"Restored file is not available");
            wait_MiliSec(2000);
        }
    }
}
