package core.dev.kezzler.net.pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by ipodoliak on 19/02/17.
 */
public class EditOrderPage extends BasePage {


    public EditOrderPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        webDriverWait(3).until((WebDriver webDriver) -> driver.getCurrentUrl().contains("edit"));
    }

    @FindBy(id= "product")
    private WebElement selectProduct;

    @FindBy(id ="metadataSchema")
    private WebElement selectMetadataProduct;


    @FindBy(id ="edit-order-save-button")
    private WebElement saveButton;

    @FindBy(id ="lock-order-button")
    private WebElement lockOrderBtn;


    @Step("{method}")
    public OrderPage editOrder (String productName, String metadata)
    {

        new Select(selectProduct).selectByVisibleText(productName);
        new Select(selectMetadataProduct).selectByVisibleText(metadata);

        return save();
    }

    @Step("{method}")
    public OrderPage save() {
        saveButton.click();
        return PageFactory.initElements(driver, OrderPage.class);
    }


    @Step("{method}")
    public boolean lockOrder()
    {
        webDriverWait(3).until((WebDriver webDriver) -> lockOrderBtn.isDisplayed());
        // check is button enabled
        if (!lockOrderBtn.isEnabled())
        {
            // order is already locked
            return true;
        }

        lockOrderBtn.click();
        checkAlert();
        return !lockOrderBtn.isEnabled();

    }

}
