import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.linkText;

public class WebSteps {
    @Step("Открытие главной страницы")
    public WebSteps openMainPage(){
        open("https://github.com");
        return this;
    }
    @Step("Поиск репозитория {repo}")
    public WebSteps searchForRepository(String repo){
        $(".header-search-input").setValue(repo).pressEnter();
        return this;
    }
    @Step("Переход в репозиторий{repo}")
    public WebSteps clickOnRepositoryLink(String repo){
        $(linkText(repo)).click();
        return this;
    }
    @Step("Переход во вкладку Issues")
    public WebSteps clickOnIssueTab(){
        $("#issues-tab").click();
        return this;
    }
    @Step("Проверка, что Issues с номером {number} имеет название {repo}")
    public WebSteps checkIssueName(int number, String repo){
        $("#issue_" + number + "_link").shouldHave(text(repo));
        return this;
    }
    @Attachment(value =  "Screenshot", type = "image/png", fileExtension = "png")
    public byte[] takeScreenshot(){
       return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
