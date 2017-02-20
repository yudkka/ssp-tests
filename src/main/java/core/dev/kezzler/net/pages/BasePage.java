package core.dev.kezzler.net.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by ipodoliak on 19/02/17.
 */
public  abstract class BasePage {


    public static final String BASE_URL = "https://core.dev.kezzler.net/";
    protected WebDriver driver;

        public BasePage(WebDriver driver) {
            this.driver = driver;

        }

        public String getPageTitle() {
            return driver.getTitle();
        }

        public String getPageUrl() {
            return driver.getCurrentUrl();
        }

        public void goToUrl(String url){
            driver.get(url);
        }

        protected WebDriverWait webDriverWait(long timeOutInSeconds) {
            return new WebDriverWait(driver, timeOutInSeconds);
        }

        public OrdersPage openOrdersPage()
        {
            return new OrdersPage(driver);
        }

    public void checkAlert() {

            WebDriverWait wait = new WebDriverWait(driver, 2);
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.accept();

    }

}
