package core.dev.kezzler.net.pages;

import core.dev.kezzler.net.models.KezzlerCode;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;
import static core.dev.kezzler.net.models.KezzlerCode.CodeCase.Lower_Case;
import static core.dev.kezzler.net.models.KezzlerCode.CodeCase.Upper_Case;
import static core.dev.kezzler.net.models.KezzlerCode.CodeType.Alphanumeric;
import static core.dev.kezzler.net.models.KezzlerCode.CodeType.Numeric;
import static java.util.concurrent.TimeUnit.SECONDS;


/**
 * Created by ipodoliak on 19/02/17.
 */
public class CodesPage extends BasePage {

    @FindBy(id = "codes-top-up-button")
    private WebElement topUpBtn;

    @FindBy( css = "tbody tr[id^='codes-tableid']")
    private List<WebElement> codesList;

    @FindBy(css = "button.btn.btn-default > i.fa.fa-cloud-upload")
    private WebElement uploadButton;

    @FindBy(id = "check-code-button")
    private  WebElement checkCodeBtn;



    public CodesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        webDriverWait(3).until((WebDriver webDriver) -> driver.getCurrentUrl().contains("codes"));
    }

    @Step("{method}")
    public CodesPage toppedUp(int size, String codename, KezzlerCode.CodeType codeType, int codelength, KezzlerCode.CodeCase codeCase)
    {
        topUpBtn.click();

        // open advanced settings
        ((RemoteWebDriver) driver).findElementByCssSelector(".modal-dialog #advanced-button").click();

        //set size
        WebElement topUpSize =((RemoteWebDriver)driver).findElementByCssSelector(".modal-dialog #top-up-size-input");
        topUpSize.clear();
        topUpSize.sendKeys(""+size);

        //set Name
        WebElement codeName =((RemoteWebDriver)driver).findElementByCssSelector(".modal-dialog #generate-codes-name-input");
        codeName.clear();
        codeName.sendKeys(""+codename);

        // set code type
        WebElement codeTypeSel = ((RemoteWebDriver) driver).findElementByCssSelector(".modal-dialog #type");
        if (codeType.equals(Alphanumeric))
        {
            new Select(codeTypeSel).selectByIndex(1);
        }
        if (codeType.equals(Numeric))
        {
            new Select(codeTypeSel).selectByIndex(2);
        }

        // set code length
        //set Name
        WebElement codeLength =((RemoteWebDriver)driver).findElementByCssSelector(".modal-dialog #length");
        codeLength.clear();
        codeLength.sendKeys(""+codelength);

        // set code case
        WebElement codeCaseSel = ((RemoteWebDriver) driver).findElementByCssSelector(".modal-dialog #case");
        if (codeCase.equals(Upper_Case))
        {
            new Select(codeCaseSel).selectByIndex(0);
        }
        if (codeCase.equals(Lower_Case))
        {
            new Select(codeCaseSel).selectByIndex(1);
        }

        ((RemoteWebDriver) driver).findElementByCssSelector(".modal-dialog #top-up-button").click();
        return  PageFactory.initElements(driver, CodesPage.class);

    }

     @Step("{method}")
     public ArrayList<KezzlerCode> codes() {
        ArrayList<KezzlerCode> codes = new ArrayList<>();
        webDriverWait(3).until((WebDriver webDriver)-> codesList.size()!=0);

        for (WebElement code : codesList){



            List<WebElement> codeEl = code.findElements(By.cssSelector("td"));
            String id = codeEl.get(0).getText().trim();
            int size = Integer.parseInt(codeEl.get(2).getText().trim());
            String name = codeEl.get(4).getText().trim();

            codes.add(new KezzlerCode(id, name, size));
        }
        return  codes;
     }

     @Step("{method}")
    public CodesPage uploadCodes(String pathToFile){
         sleepUninterruptibly(2, SECONDS);

         uploadButton.click();
         sleepUninterruptibly(2, SECONDS);


         WebElement fileInput = ((RemoteWebDriver)driver).findElementByCssSelector("#uploadForm #uploadFileName");
         fileInput.sendKeys(pathToFile);

         ((RemoteWebDriver) driver).findElementByCssSelector(".modal-footer button.btn-primary").click();

         sleepUninterruptibly(2, SECONDS);
         return PageFactory.initElements(driver, CodesPage.class);

     }

    @Step("{method}")
    public String uploadCodesWithError(String pathToFile){
        sleepUninterruptibly(2, SECONDS);

        uploadButton.click();
        sleepUninterruptibly(2, SECONDS);


        WebElement fileInput = ((RemoteWebDriver)driver).findElementByCssSelector("#uploadForm #uploadFileName");
        fileInput.sendKeys(pathToFile);

        ((RemoteWebDriver) driver).findElementByCssSelector(".modal-footer button.btn-primary").click();

        sleepUninterruptibly(2, SECONDS);
        ((RemoteWebDriver) driver).findElementByCssSelector(".modal-footer button.btn-default").click();


         return driver.findElement(By.cssSelector("div.alert > div.clickable.showdetails")).getText();

    }

    @Step ("{method}")
    public String getQRCodeContent(int index)
    {
        sleepUninterruptibly(1, SECONDS);
        checkCodeBtn.click();

        sleepUninterruptibly(1, SECONDS);
        WebElement input = ((RemoteWebDriver)driver).findElementByCssSelector(".modal-body #check-code-index-input");
        input.sendKeys(""+index);

        ((RemoteWebDriver) driver).findElementByCssSelector(".modal-body #get-code-button").click();
        sleepUninterruptibly(2, SECONDS);

        String qrCode = ((RemoteWebDriver) driver).findElementByCssSelector(".modal-body #qr-code-content-input").getAttribute("value");

        ((RemoteWebDriver) driver).findElementByCssSelector("#close-popup-button").click();

        return  qrCode;


    }


}

