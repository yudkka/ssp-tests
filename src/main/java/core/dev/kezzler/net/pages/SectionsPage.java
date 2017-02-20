package core.dev.kezzler.net.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by ipodoliak on 20/02/17.
 */
public class SectionsPage extends BasePage {
    public SectionsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        webDriverWait(3).until((WebDriver webDriver) -> driver.getCurrentUrl().contains("sections"));
    }

    @FindBy(css =".btn.btn-default i.fa.fa-kz-new")
    private WebElement createNewSection;

    @Step("{method}")
    public CreateSectionPage createNewSection()
    {
        createNewSection.click();
        return  PageFactory.initElements(driver, CreateSectionPage.class);
    }



}
