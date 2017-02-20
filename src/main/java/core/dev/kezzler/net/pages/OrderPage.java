package core.dev.kezzler.net.pages;

import core.dev.kezzler.net.models.KezzlerOrder;
import core.dev.kezzler.net.models.KezzlerProduct;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

/**
 * Created by ipodoliak on 19/02/17.
 */
public class OrderPage extends BasePage {
    public OrderPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        webDriverWait(3).until((WebDriver webDriver) -> driver.getCurrentUrl().contains("/order/"));
    }

    @FindBy(id ="edit-order-button")
    private WebElement editOrderBtn;

    @FindBy(css = "div.panel-body  div.col-sm-4")
    private List<WebElement> orderInfo;

    @FindBy(id = "code-order-button")
    private WebElement codeOrderBtn;

    @FindBy(css ="a[href*='sections']")
    private  WebElement sectionOrdeBtn;

    @Step("{method}")
    public OrderPage connectToProduct(KezzlerProduct product)
    {
        EditOrderPage page = editOrder();
        return page.editOrder(product.getProductName(), product.getMetadata());

    }

    @Step("{method}")
    private EditOrderPage editOrder() {
        webDriverWait(3).until((WebDriver webDriver) -> editOrderBtn.isDisplayed());
        editOrderBtn.click();
        return PageFactory.initElements(driver, EditOrderPage.class);
    }

    @Step("{method}")
    public KezzlerOrder order()
    {
        webDriverWait(3).until((WebDriver webDriver) -> orderInfo.get(0).isDisplayed());

        String id = orderInfo.get(0).getText().trim();
        String orderName = orderInfo.get(1).getText().trim();
        String productName = orderInfo.get(2).getText().trim();

        boolean isLocked = !orderInfo.get(4).findElement(By.cssSelector(".fa.fa-unlock")).isDisplayed();
        boolean isSectioned = orderInfo.get(5).getText().trim().equals("yes");

        String metadata = orderInfo.get(9).getText().trim();

        return new KezzlerOrder(id, orderName, isSectioned, new KezzlerProduct(productName, metadata), isLocked);

    }

    @Step("{method}")
    public OrderPage lockOrder(){
        EditOrderPage page = editOrder();
        page.lockOrder();
        return page.save();

    }

    @Step("{method}")
    public CodesPage codes()
    {
        webDriverWait(3).until((WebDriver webDriver) -> codeOrderBtn.isDisplayed());

        codeOrderBtn.click();
        return PageFactory.initElements(driver, CodesPage.class);
    }

    @Step("{method}")
    public SectionsPage section()
    {
        webDriverWait(3).until((WebDriver webDriver) -> sectionOrdeBtn.isDisplayed());
        sectionOrdeBtn.click();
        return PageFactory.initElements(driver, SectionsPage.class);
    }

}
