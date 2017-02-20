package core.dev.kezzler.net.pages;

import core.dev.kezzler.net.models.KezzlerOrder;
import core.dev.kezzler.net.models.KezzlerProduct;
import core.dev.kezzler.net.models.KezzlerSection;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by ipodoliak on 19/02/17.
 */
public class SectionPage extends BasePage {
    public SectionPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        webDriverWait(3).until((WebDriver webDriver) -> driver.getCurrentUrl().contains("/section/"));
    }



    @FindBy(css = "div.panel-body  div.col-sm-4")
    private List<WebElement> sectionInfo;


    @FindBy(css = "div.panel-body  div.col-sm-6")
    private List<WebElement> sectionActivationInfo;





    @Step("{method}")
    public KezzlerSection section()
    {

        webDriverWait(3).until((WebDriver webDriver) -> sectionInfo.get(0).isDisplayed());
        sleepUninterruptibly(1, SECONDS);
        String id = sectionInfo.get(0).getText().trim();
        String productName   = sectionInfo.get(1).findElement(By.tagName("span")).getText().trim();
        int startIndex = Integer.parseInt(sectionInfo.get(2).getText().trim());
        int endIndex = Integer.parseInt(sectionInfo.get(4).getText().trim());
        int size = Integer.parseInt(sectionInfo.get(6).getText().trim());
        String metadata = sectionInfo.get(7).getText().trim();


        boolean isActivated= sectionActivationInfo.get(1).getText().trim().equals("yes");
        String maxLimit = sectionActivationInfo.get(2).getText().trim();

        return new KezzlerSection(id, startIndex, endIndex, size, isActivated, maxLimit, new KezzlerProduct(productName, metadata));

    }



}
