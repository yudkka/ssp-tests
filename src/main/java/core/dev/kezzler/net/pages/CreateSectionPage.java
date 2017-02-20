package core.dev.kezzler.net.pages;

import core.dev.kezzler.net.models.KezzlerProduct;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by ipodoliak on 20/02/17.
 */
public class CreateSectionPage extends BasePage {

    public CreateSectionPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        webDriverWait(3).until((WebDriver webDriver) -> driver.getCurrentUrl().contains("section/new"));
    }

    @FindBy(id = "startIndex")
    private WebElement startIndex;

    @FindBy( id = "endIndex")
    private  WebElement endIndex;

    @FindBy (css = "select[ng-model='section.product.productId']")
    private  WebElement selectProduct;

    @FindBy (id = "metadataSchema")
    private WebElement selectMetadata;

    @FindBy (id = "enabled-checkbox")
    private WebElement activateCbox;


    @FindBy( css = "input[ng-checked='limitedValidations']")
    private WebElement limitValidationCbox;

    @FindBy (id = "max")
    private WebElement maxLimit;

    @FindBy(id = "save-activation-button")
    private  WebElement saveBtn;

    @Step("{method}")
    public SectionPage createSection (int startIndexValue, int endIndexValue, KezzlerProduct product, boolean isActivated, boolean isLimited, int maxLimitValue)
    {
        startIndex.sendKeys(""+startIndexValue);
        endIndex.sendKeys(""+endIndexValue);

        new Select(selectProduct).selectByVisibleText( product.getProductName());
        new Select(selectMetadata).selectByVisibleText( product.getMetadata());

        if (isActivated)
        {
            activateCbox.click();
        }


        if (isLimited){

            limitValidationCbox.click();
            limitValidationCbox.click();
            webDriverWait(3).until((WebDriver webDriver)->limitValidationCbox.isSelected());

            webDriverWait(3).until((WebDriver webDriver)->maxLimit.isDisplayed());
            maxLimit.clear();
            maxLimit.sendKeys(""+maxLimitValue);
        }

        saveBtn.click();

        return PageFactory.initElements(driver, SectionPage.class);

    }



}
