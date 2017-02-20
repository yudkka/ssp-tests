package core.dev.kezzler.net.pages;

import core.dev.kezzler.net.models.KezzlerOrder;
import core.dev.kezzler.net.models.KezzlerProduct;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ipodoliak on 19/02/17.
 */
public class OrdersPage extends BasePage {

    @FindBy(id = "create-new-order")
    private WebElement createOrderBtn;
    @FindBy (css = "button.btn.btn-default  i.glyphicon.glyphicon-record")
    private  WebElement goToOrderBtn;
    @FindBy (css = "button.btn.btn-default  i.glyphicon.glyphicon-refresh")
    private  WebElement refreshBtn;

    @FindBy( css = "#order-table-body tr[id^='orders-tableid']")
    private List<WebElement> orders;



    public OrdersPage(WebDriver driver) {
        super(driver);
        this.driver = driver;

        driver.get(BASE_URL + "#/kcengine/orders");
        PageFactory.initElements(driver, this);
        webDriverWait(3).until((WebDriver webDriver) -> driver.getCurrentUrl().contains("orders"));
    }

    @Step("{method}")
    public OrdersPage  createOrder (String orderName , boolean isSectioned)
    {
        createOrderBtn.click();
        // work with modal dialog

        // enter order name
        WebElement orderNameinput= ((RemoteWebDriver) driver).findElementByCssSelector(".modal-dialog input#order-name-input");
        orderNameinput.clear();
        orderNameinput.sendKeys(orderName);

        // check checkbox Sectioned
        if (isSectioned)
        {
            ((RemoteWebDriver) driver).findElementByCssSelector(".modal-dialog input#mode").click();
        }

        // click create order button
        ((RemoteWebDriver) driver).findElementByCssSelector(".modal-dialog button#create-order-button").click();
        return  PageFactory.initElements(driver, OrdersPage.class);

    }


    @Step("{method}")
    public OrdersPage updateOrdersList ()
    {
        refreshBtn.click();
        return PageFactory.initElements(driver, OrdersPage.class);
    }

    @Step("{method}")
    public ArrayList<KezzlerOrder> orders(){
        ArrayList<KezzlerOrder> ordersList = new ArrayList<>();
        for (WebElement order: orders)
        {
            String id = order.findElement(By.cssSelector("td:nth-child(1)")).getText().trim();
            String name = order.findElement(By.cssSelector(".kz-order-name")).getText().trim();
            KezzlerProduct product = new KezzlerProduct(order.findElement(By.cssSelector("td:nth-child(5)")).getText().trim());

            boolean isLocked = order.findElement(By.cssSelector("i.fa.fa-lock")).isDisplayed();


            ordersList.add(new KezzlerOrder(id, name, product, isLocked));
        }
        return  ordersList;

    }

    @Step("{method}")
    public OrderPage goToOrder(String id)
    {
        goToOrderBtn.click();

        WebElement idInput =((RemoteWebDriver)driver).findElementByCssSelector(".modal-dialog #name");
        idInput.clear();
        idInput.sendKeys(id);

        ((RemoteWebDriver) driver).findElementByCssSelector(".modal-dialog button.btn.btn-primary").click();

        return PageFactory.initElements(driver, OrderPage.class);

    }



}
