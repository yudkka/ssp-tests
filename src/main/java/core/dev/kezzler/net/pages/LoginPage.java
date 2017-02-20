package core.dev.kezzler.net.pages;

import core.dev.kezzler.net.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.concurrent.TimeUnit;


/**
 * Created by ipodoliak on 19/02/17.
 */
public class LoginPage extends BasePage {

    private WebDriver driver;

    @FindBy (id = "username")
    public WebElement userNameInp;

    @FindBy (id = "password")
    public WebElement passwordInp;

    @FindBy (id = "sign-in-button")
    public WebElement signInBtn;

    public LoginPage(final WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        webDriverWait(3).until((WebDriver webDriver) -> driver.getCurrentUrl().contains("login"));

    }


    @Step ("{method}")
    public void login( String username, String password)
    {
        userNameInp.clear();
        userNameInp.sendKeys(username);

        passwordInp.clear();
        passwordInp.sendKeys(password);

        signInBtn.click();
        webDriverWait(3).pollingEvery(500, TimeUnit.MILLISECONDS)
        .until((WebDriver webDriver) -> !getPageUrl().contains("login"));
    }


}
