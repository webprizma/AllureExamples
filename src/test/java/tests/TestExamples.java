package tests;

import com.beust.jcommander.Parameter;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class TestExamples {
    @BeforeEach
    static void beforeEach() {
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
    }
    String doctor = "Балканова Вероника Сергеевна";
    String speciality = "Аритмолог";
    String metro = "Авиамоторная";

    @Test
    @Owner("kulaginyv")
    @Severity(SeverityLevel.BLOCKER)
    @Feature("Список врачей")
    @Story("Можно найти страницу врача через поиск")
    @DisplayName("Чистый Selenide (с Listener)")
    @Description("Функциональный тест")
    @Link(name = "docdoc.ru", url = "https://docdoc.ru/")
    void firstTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        Allure.parameter("Доктор", "Балканова Вероника Сергеевна");
        Allure.parameter("Метро", "Авиамоторная");
        Allure.parameter("Специальность", "Аритмолог");

        open("https://docdoc.ru/");
        $("div.search-form__input--specialities input").sendKeys(speciality);
        $$("div[data-test-id='search_items'] span").findBy(Condition.text(speciality)).click();
        $("div.search-form__input--geo input").sendKeys(metro);
        $$("div[data-test-id='search_geo_items'] span").findBy(Condition.text(metro)).click();
        $("button.search-form__button").click();
        $$("a[data-test-id='doctor-list-page-card-details__link']")
                    .findBy(Condition.text(doctor))
                    .click();
        $("h1[data-testid='doctor-card__name']")
                    .shouldHave(Condition.text(doctor));
    }

    @Test
    @Owner("kulaginyv")
    @Severity(SeverityLevel.MINOR)
    @Feature("Список врачей")
    @Story("Можно найти страницу врача через поиск только по специальности")
    @DisplayName("Лямбда шаги через step (name, () -> {})")
    @Description("Функциональный тест")
    @Link(name = "docdoc.ru", url = "https://docdoc.ru/")
    void secondTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        Allure.parameter("Доктор", "Балканова Вероника Сергеевна");
        Allure.parameter("Метро", "Авиамоторная");
        Allure.parameter("Специальность", "Аритмолог");

        step("Открываем сайт docdoc.ru", () -> {
            open("https://docdoc.ru/");
        });
        step("Вводим специальность " + speciality, () -> {
            $("div.search-form__input--specialities input").sendKeys(speciality);
            $$("div[data-test-id='search_items'] span").findBy(Condition.text(speciality)).click();
        });
        step("Нажимаем кнопку Искать", () -> {
            $("button.search-form__button").click();
        });
        step("Открываем страницу врача " + doctor, () -> {
            $$("a[data-test-id='doctor-list-page-card-details__link']")
                    .findBy(Condition.text(doctor))
                    .click();
        });
        step("Проверяем, что открылась верная страница врача", () -> {
            $("h1[data-testid='doctor-card__name']")
                    .shouldHave(Condition.text(doctor));
        });
    }

    @Test
    @Owner("kulaginyv")
    @Severity(SeverityLevel.TRIVIAL)
    @Feature("Список врачей")
    @Story("Можно найти страницу врача через поиск только по метро")
    @DisplayName("Шаги с аннотацией @Step")
    @Description("Функциональный тест")
    @Link(name = "docdoc.ru", url = "https://docdoc.ru/")
    void thirdTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        Allure.parameter("Доктор", "Балканова Вероника Сергеевна");
        Allure.parameter("Метро", "Авиамоторная");
        Allure.parameter("Специальность", "Аритмолог");
        WebSteps ws = new WebSteps();

        ws.openMainPage();
        ws.selectMetro(metro);
        ws.searchSubmit();
        ws.openDoctorPage(doctor);
        ws.checkDoctorPage(doctor);
    }

//    @Test
//    public void dynamicLabels() {
//        Allure.label("owner", "yuri_kulagin");
//        Allure.label("severity", SeverityLevel.CRITICAL.value());
//        Allure.feature("Динамические таблички");
//        Allure.story("Используем динамические таблички");
//        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Тест с использованием динамическим табличек"));
//        Allure.getLifecycle().updateTestCase(testResult -> testResult.setDescription("Как использовать диначеские таблички"));
//        Allure.link("Webprizma", "https://webprizma.ru");
//    }
}
