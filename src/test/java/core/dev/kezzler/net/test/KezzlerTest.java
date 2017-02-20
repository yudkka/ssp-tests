package core.dev.kezzler.net.test;

import core.dev.kezzler.net.data.KezzletDataProvider;
import core.dev.kezzler.net.models.KezzlerCode;
import core.dev.kezzler.net.models.KezzlerOrder;
import core.dev.kezzler.net.models.KezzlerProduct;
import core.dev.kezzler.net.models.KezzlerSection;
import core.dev.kezzler.net.pages.CodesPage;
import core.dev.kezzler.net.pages.LoginPage;
import core.dev.kezzler.net.pages.OrderPage;
import core.dev.kezzler.net.pages.OrdersPage;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.ArrayList;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static java.util.concurrent.TimeUnit.SECONDS;



/**
 * Created by ipodoliak on 19/02/17.
 */
public class KezzlerTest {
    private WebDriver driver;

    @BeforeClass
    public void setUp ()
    {
        // init driver
        ChromeDriverManager.getInstance().setup();

    }
    @BeforeMethod
    public void setupTest() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, SECONDS);

        driver.get("https://core.dev.kezzler.net");

        login();
    }

    @AfterClass (alwaysRun = true)
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Step("Login user")
    private void login(){
        LoginPage loginPage = new LoginPage(driver);

        //todo upadte usename and password
        loginPage.login("userName", "password");

    }

    //test #1
    @Test( dataProvider = "test-orders", dataProviderClass = KezzletDataProvider.class)
    public void createOrderTest(String orderName, boolean isSectioned)
    {
        //open Orders Page
        OrdersPage page = new OrdersPage(driver);
        //read existing orders
        ArrayList<KezzlerOrder> oldOrders = page.orders();
        int oldSize = oldOrders.size();
        // create new order
        page.createOrder(orderName, isSectioned);
        //update order page
        page = page.updateOrdersList();

        // read new order list
        ArrayList<KezzlerOrder> newOrders = page.orders();
        int newSize = newOrders.size();

        //make assertion
        Assert.assertTrue(newSize==oldSize+1, "Unexpected list size after adding new order ["+ newSize +"]");
        newOrders.removeAll(oldOrders);
        Assert.assertEquals(newOrders.get(0).getName(), orderName, "Create order failed");
    }

    @Test
    public void negativeCreateOrderTest()
    {
        // Test that order with empty data wouldn't be created
        OrdersPage page = new OrdersPage(driver);
        //read existing orders
        ArrayList<KezzlerOrder> oldOrders = page.orders();
        int oldSize = oldOrders.size();
        // create new order
        page.createOrder("", false);
        // !usually some pop up should be here
        //update order page
        page = page.updateOrdersList();

        // read new order list
        ArrayList<KezzlerOrder> newOrders = page.orders();
        int newSize = newOrders.size();

        //make assertion
        Assert.assertTrue(newSize==oldSize, "Unexpected list size after adding new order ["+ newSize +"]");

    }

    // test #2
    @Test(dataProvider = "test-orders-two", dataProviderClass = KezzletDataProvider.class)
    public void connectToProductTest(String orderName, boolean isSectioned, KezzlerProduct product)
    {
        // open orders page
        OrdersPage page = new OrdersPage(driver);
        //create new order
        ArrayList<KezzlerOrder> oldOrders = page.orders();
        page.createOrder(orderName, isSectioned);
        // update orders page
        page = page.updateOrdersList();
        ArrayList<KezzlerOrder> newOrders = page.orders();
        newOrders.removeAll(oldOrders);
        // get created order
        KezzlerOrder order = newOrders.get(0);
        order.setSectioned(isSectioned);
        // open just created order
        OrderPage orderPage = page.goToOrder(order.getId());
        // connect order to product
        orderPage.connectToProduct(product);
        order.setProduct(product);
        // read updated order
        KezzlerOrder realOrder = orderPage.order();
        //assert product was connected
        Assert.assertEquals(order, realOrder, "Connect product to order failed");

    }

    //test #3
    @Test
    public void lockOrderTest()
    {

        // open orders page
        OrdersPage page = new OrdersPage(driver);
        // create order to lock
        page.createOrder("Lock order", false);
        // update orders page
        page = page.updateOrdersList();
        KezzlerOrder order =page.orders().get(0);

        // open just created order
        OrderPage orderPage = page.goToOrder(order.getId());
        // connect order to product
        orderPage.lockOrder();

        // read updated order
        boolean isLocked = orderPage.order().isLocked();
        //assert product was connected
        Assert.assertTrue(isLocked, "Order wasn't locked");
        // here should be some other checks to identify that order is unchangeable
        // but as I can modify all parts except codes I didn't add them

    }

    //todo add data provider (test #4a and #4b)
    @Test
    public void orderToppedUpCodeTest()
    {
        // open orders page
        OrdersPage page = new OrdersPage(driver);
        // create order to lock
        page.createOrder("ToppedUp order", false);
        // update orders page
        page = page.updateOrdersList();
        KezzlerOrder order =page.orders().get(0);

        // open just created order
        OrderPage orderPage = page.goToOrder(order.getId());
        KezzlerCode expectedCode = new KezzlerCode("TestCode", 1, KezzlerCode.CodeType.Alphanumeric, KezzlerCode.CodeCase.Lower_Case, 15);

        // connect order to product
        CodesPage codesPage = orderPage
                .codes()
                .toppedUp(expectedCode.getSize(), expectedCode.getName(), expectedCode.getCodeType(), expectedCode.getCodeLength(), expectedCode.getCodeCase());

        sleepUninterruptibly(2, SECONDS);
        KezzlerCode code = codesPage
                .codes()
                .get(0);

        Assert.assertEquals( expectedCode, code, "Topped up code failed");
    }

    //todo test to add paired codes (test #4c)

    //test #4d
    @Test
    public void orderUploadCodeTest()
    {
        // open orders page
        OrdersPage page = new OrdersPage(driver);
        // create order to lock
        page.createOrder("Upload order", false);
        // update orders page
        page = page.updateOrdersList();
        KezzlerOrder order =page.orders().get(5);

        // open just created order
        OrderPage orderPage = page.goToOrder(order.getId());


        // connect order to product
        CodesPage codesPage = orderPage
                .codes()
                .uploadCodes("/Users/ipodoliak/Downloads/kezzler-ssp-tests/src/main/resources/test_codes_2.csv");

        sleepUninterruptibly(2, SECONDS);
        KezzlerCode code = codesPage
                .codes()
                .get(0);

        Assert.assertEquals( "", code.getName(), "Topped up code failed");

        String error = codesPage
                .uploadCodesWithError("/Users/ipodoliak/Downloads/kezzler-ssp-tests/src/main/resources/test_codes.csv");
        Assert.assertTrue(error.contains("Error Code: 1062"));


    }

    // todo add testCodeSetStatusPrinted test
    // todo add testDownloadCsvCode test
    // todo add toppedUpWithSameConfig test

    //test 4f
    @Test
    public void checkCodeTest()
    {
        // open orders page
        OrdersPage page = new OrdersPage(driver);
        // create order to lock
        page.createOrder("Check order", false);
        // update orders page
        page = page.updateOrdersList();
        KezzlerOrder order =page.orders().get(0);

        // open just created order
        OrderPage orderPage = page.goToOrder(order.getId());
        KezzlerCode expectedCode = new KezzlerCode("TestCode", 1, KezzlerCode.CodeType.Alphanumeric, KezzlerCode.CodeCase.Lower_Case, 15);

        // connect order to product
        CodesPage codesPage = orderPage
                .codes()
                .toppedUp(expectedCode.getSize(), expectedCode.getName(), expectedCode.getCodeType(), expectedCode.getCodeLength(), expectedCode.getCodeCase());

        sleepUninterruptibly(2, SECONDS);
        String qrCode = codesPage.getQRCodeContent(1);


        Assert.assertFalse(qrCode.isEmpty(), "Topped up code failed");
    }

    // test #5 , test #6a,d,e
    @Test
    public void createSectionTest()
    {
        KezzlerProduct product = new KezzlerProduct("Product#1", "Metadata Schema");
        // open orders page
        OrdersPage page = new OrdersPage(driver);
        //create new order
        ArrayList<KezzlerOrder> oldOrders = page.orders();
        page.createOrder("SectionOrder", true);
        // update orders page
        page = page.updateOrdersList();
        ArrayList<KezzlerOrder> newOrders = page.orders();
        newOrders.removeAll(oldOrders);
        // get created order
        KezzlerOrder order = newOrders.get(0);
        order.setSectioned(true);
        // open just created order
        OrderPage orderPage = page.goToOrder(order.getId());
        // connect order to product
        sleepUninterruptibly(2, SECONDS);
        orderPage.connectToProduct(product);
        order.setProduct(product);
        sleepUninterruptibly(2, SECONDS);

        KezzlerSection expectedSection = new KezzlerSection(1, 1,true,  "3" , product);

        KezzlerSection section = orderPage
                .section()
                .createNewSection()
                .createSection(1, 1, product,  true, true, 3)
                .section();

        Assert.assertEquals(expectedSection, section, "Create section failed");



    }

    //todo add negative test - create section without Index ( test #6.a)
    // todo addPickCodeTest (test #6.c)
    // todo add directAccessToSection




}
