import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.attachment;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

public class GitHubIssueNameTest {
    public static final int ISSUE = 81;
    public static final String REPOSITORY = "eroshenkoam/allure-example";
    public static final String ISSUE_NAME = "issue_to_test_allure_report";


    @Test
    void checkIssueNameWithListener() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        open("https://github.com");
        $(".header-search-input").setValue(REPOSITORY).pressEnter();
        $(linkText(REPOSITORY)).click();
        $("#issues-tab").click();
        $("#issue_" + ISSUE + "_link").shouldHave(text(ISSUE_NAME));
    }

    @Test
    void checkIssueNameWithLambdaSteps() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открытие главной страницы", () ->
                open("https://github.com")
        );
        step("Поиск репозитория " + REPOSITORY, () ->
                $(".header-search-input").setValue(REPOSITORY).pressEnter()
        );
        step("Переход в репозиторий " + REPOSITORY, () ->
                $(linkText(REPOSITORY)).click()
        );
        step("Переход во вкладку Issues", () ->
                $("#issues-tab").click()
        );
        step("Проверка, что Issues с номером " + ISSUE + " имеет название " + ISSUE_NAME, () -> {
            $("#issue_" + ISSUE + "_link").shouldHave(text(ISSUE_NAME));
            attachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES)));
        });
    }

    @Test
    void checkIssueNameWithAnnotationSteps() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        WebSteps steps = new WebSteps();
        steps
                .openMainPage()
                .searchForRepository(REPOSITORY)
                .clickOnRepositoryLink(REPOSITORY)
                .clickOnIssueTab()
                .checkIssueName(ISSUE, ISSUE_NAME)
                .takeScreenshot();
    }
}
