package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideDriver;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Owner("kulaginyv")
@Feature("Список врачей")
public class TestExamples {
    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void beforeEach() {
        Allure.parameter("Доктор", "Балканова Вероника Сергеевна");
        Allure.parameter("Метро", "Авиамоторная");
        Allure.parameter("Специальность", "Аритмолог");
    }

    String doctor = "Балканова Вероника Сергеевна";
    String speciality = "Аритмолог";
    String metro = "Авиамоторная";

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Можно найти страницу врача через поиск")
    @DisplayName("Чистый Selenide (с Listener)")
    @Description("Функциональный тест")
    @Link(name = "docdoc.ru", url = "https://docdoc.ru/")
    void firstTest() {
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
    @Severity(SeverityLevel.MINOR)
    @Story("Можно найти страницу врача через поиск только по специальности")
    @DisplayName("Лямбда шаги через step (name, () -> {})")
    @Description("Функциональный тест")
    @Link(name = "docdoc.ru", url = "https://docdoc.ru/")
    void secondTest() {
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
    @Severity(SeverityLevel.TRIVIAL)
    @Story("Можно найти страницу врача через поиск только по метро")
    @DisplayName("Шаги с аннотацией @Step")
    @Description("Функциональный тест")
    @Link(name = "docdoc.ru", url = "https://docdoc.ru/")
    void thirdTest() {
        WebSteps ws = new WebSteps();

        ws.openMainPage();
        ws.selectMetro(metro);
        ws.searchSubmit();
        ws.openDoctorPage(doctor);
        ws.checkDoctorPage(doctor);
    }
}
