import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class assigment01 {
    public static WebDriver driver;
    public static long PAGE_LOAD_TIMEOUT = 40;
    public static long IMPLICIT_WAIT = 40;
    public static String Baseurl = "https://quantra.quantinsti.com/";
    public String username = "rajeshrathod044@gmail.com";
    public String password = "password";

    @BeforeSuite
    public void BeforeSuite(){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
        driver.get(Baseurl);
    }
    @Test
    public void Test(){
        waitForPageLoad(driver);
        WebElement loginBtn = driver.findElement(By.xpath("//span[contains(text(),'Login')]"));
        WebElement pushNotification = driver.findElement(By.xpath("//button[@class='web-push-btn remind-btn']"));

        if (pushNotification.isDisplayed()){
            pushNotification.click();
        }
        waitForElementClickable(driver,loginBtn,3);
        loginBtn.click();

        waitForPageLoad(driver);
        WebElement username_textbox = driver.findElement(By.name("email"));
        WebElement password_textbox = driver.findElement(By.name("password"));
        WebElement submit_Btn = driver.findElement(By.xpath("//button[contains(@text,(Login))]"));

        waitForElementClickable(driver,username_textbox,3);
        username_textbox.click();
        username_textbox.sendKeys(username);
        password_textbox.click();
        password_textbox.sendKeys(password);
        submit_Btn.click();

        waitForPageLoad(driver);
        WebElement course_label = driver.findElement(By.xpath("//span/a[contains(@text,Courses)]"));
        WebElement sentmentAnalyis_label = driver.findElement(By.xpath("//span[contains(text(),'Sentiment Analysis in Trading')]"));

        mouseHover(driver,course_label);
        mouseHover(driver,sentmentAnalyis_label);
        sentmentAnalyis_label.click();

        waitForPageLoad(driver);
        WebElement Enroll_Btn = driver.findElement(By.xpath("//span[contains(text(),'Enroll Now+@10% OFF')]"));
        String CourseName = driver.findElement(By.xpath("//div[@class='container course-detail-inner-container']//h1")).getText();
        String CourseBasePrice = driver.findElement(By.xpath("//span[@class='cd__data-unit__striked']/span[1]")).getText();
        String CourseDisPrice = driver.findElement(By.xpath("//div[@class='cd__data-unit__info']/span[2]/span")).getText();

        System.out.println("Course Name :- " + CourseName + " Base Price :- " + CourseBasePrice + " Discounted Price :- " + CourseDisPrice);
        Enroll_Btn.click();

        waitForPageLoad(driver);
        String BaseAmount = driver.findElement(By.xpath("//div[@class='cart-container']/div[@class='cart-summary-section']/div[@class='cart-summary']/div[2]")).getText();
        String PayableAmount = driver.findElement(By.xpath("//div[@class='cart-container']/div[@class='cart-summary-section']/div[@class='cart-summary']/div[7]")).getText();
        WebElement viewDetails = driver.findElement(By.xpath("//div[@class='cart-item'][1]//div[@class='cart-item-cta']/a[contains(text(),'View Details')]"));

        System.out.println(BaseAmount + "\n" + PayableAmount);

        waitForElementClickable(driver,viewDetails,3);
        viewDetails.click();

        waitForPageLoad(driver);
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.close();
        driver.switchTo().window(tabs.get(0));

        waitForPageLoad(driver);
        WebElement removeDetails = driver.findElement(By.xpath("//div[@class='cart-item'][1]//div[@class='cart-item-cta']/a[contains(text(),'Remove')]"));
        removeDetails.click();

        WebElement succesToast = driver.findElement(By.xpath("//div[@class='toasted toasted-primary info']"));

        if(ExpectedConditions.visibilityOf(succesToast).equals(true)){
           succesToast.getText();
        }
        System.out.println(succesToast);

        if(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='text-success'][contains(text(),'applied')]")).equals(true)){
            driver.findElement(By.xpath("//b[contains(text(),'Edit')]")).click();
            WebElement removeCoupon = driver.findElement(By.xpath("//span[contains(text(),'Remove Coupon')]"));
            waitForElementClickable(driver,removeCoupon,3);
            removeCoupon.click();
        }
        else {
            WebElement applycode = driver.findElement(By.xpath("//span[contains(text(),'Apply Coupon')]"));
            applycode.click();
        }

        WebElement EnterCoupon_textboox = driver.findElement(By.xpath("//input[@placeholder='Type coupon code']"));
        waitForElementClickable(driver,EnterCoupon_textboox,3);
        EnterCoupon_textboox.clear();
        EnterCoupon_textboox.sendKeys("ABC");

        WebElement applycode = driver.findElement(By.xpath("//div[@class='coupon-form__button']//span[@class='default-slot'][contains(text(),'Apply')]"));
        waitForElementClickable(driver,applycode,3);
        applycode.click();

        if (ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='coupon-modal-alert alert alert-danger']")).equals(true)){
            String CouponError = driver.findElement(By.xpath("//div[@class='coupon-modal-alert alert alert-danger']/span")).getText();
            System.out.println(CouponError);
        }
        driver.findElement(By.xpath("//button[@class='close']")).click();
         WebElement profileOption = driver.findElement(By.xpath("//div[@class='profile-pic-initials']"));
         WebElement logout = driver.findElement(By.xpath("//ul[@class='avatar-menu']//a[@class='test link'][contains(text(),'Logout')]"));

         waitForElementClickable(driver,profileOption,3);
         profileOption.click();
         waitForElementClickable(driver,logout,3);
         loginBtn.click();
    }

    @AfterSuite
    public void AfterSuite(){
        if (driver != null)
            driver.quit();
    }
    public static void waitForElementClickable(WebDriver driver, WebElement element, long timeOutInSeconds) {
        waitForPageLoad(driver);
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        try{Thread.sleep(2000);}catch (Exception e){System.out.println(e);}
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForPageLoad(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
    }

    public static void mouseHover(WebDriver driver,WebElement element){
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public static void waitUntilElementVisible(WebDriver driver,WebElement element,long timeOutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

}
