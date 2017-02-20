package core.dev.kezzler.net.data;

import core.dev.kezzler.net.models.KezzlerProduct;
import org.testng.annotations.DataProvider;

/**
 * Created by ipodoliak on 19/02/17.
 */
public class KezzletDataProvider {

    @DataProvider(name = "test-orders")
    public static Object[][] createOrders() {
        return new Object[][]{  {"SectionedOrder" , true},
                {"NonSectionedOrder", false}};
    }

    @DataProvider(name = "test-orders-two")
    public static Object[][] createOrdersWithProducts() {
        return new Object[][]{  {"ProductSectionedOrder" , true, new KezzlerProduct("Product#1", "Metadata Schema")},
                {"ProductNonSectionedOrder", false, new KezzlerProduct("Product#1", "Metadata Schema")}};
    }

}
