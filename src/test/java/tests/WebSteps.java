package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class WebSteps {
     @Step("Открываем сайт docdoc.ru")
     public void openMainPage() {
          open("https://docdoc.ru/");
     }
     @Step("Вводим метро {metro}")
     public void selectMetro(String metro) {
          $("div.search-form__input--geo input").sendKeys(metro);
          $$("div[data-test-id='search_geo_items'] span").findBy(Condition.text(metro)).shouldBe(Condition.visible).click();
     }
     @Step("Нажимаем кнопку Искать")
     public void searchSubmit() {
          $("button.search-form__button").click();
     }
     @Step("Открываем страницу врача {doctor}")
     public void openDoctorPage(String doctor) {
          $$("a[data-test-id='doctor-list-page-card-details__link']")
                  .findBy(Condition.text(doctor))
                  .click();
     }
     @Step("Проверяем, что открылась верная страница врача")
     public void checkDoctorPage(String doctor) {
          $("h1[data-testid='doctor-card__name']")
                  .shouldHave(Condition.text(doctor));
     }
}
