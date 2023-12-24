package ru.netology.login.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.login.data.DataGenerator;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.login.data.DataGenerator.Registration.genUser;
import static ru.netology.login.data.DataGenerator.Registration.registerUser;


public class LoginTest {
    @BeforeEach
    void openForm() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginActive() {
        var registeredUser = registerUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет")).shouldBe(Condition.visible);
    }
    @Test
    void shouldNotLoginBlocked() {
        var registeredUser = registerUser("blocked");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id=error-notification] .notification__title").shouldHave(Condition.exactText("Ошибка")).shouldBe(Condition.visible);
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Пользователь заблокирован")).shouldBe(Condition.visible);
    }
    @Test
    void shouldNotLoginNotRegistered() {
        var notRegisteredUser = genUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("button.button").click();
        $("[data-test-id=error-notification] .notification__title").shouldHave(Condition.exactText("Ошибка")).shouldBe(Condition.visible);
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }
    @Test
    void shouldNotLoginWrongUsername() {
        var registeredUser = registerUser("active");
        $("[data-test-id='login'] input").setValue(DataGenerator.generateUsername());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id=error-notification] .notification__title").shouldHave(Condition.exactText("Ошибка")).shouldBe(Condition.visible);
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }
    @Test
    void shouldNotLoginWrongPassword() {
        var registeredUser = registerUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(DataGenerator.generatePassword());
        $("button.button").click();
        $("[data-test-id=error-notification] .notification__title").shouldHave(Condition.exactText("Ошибка")).shouldBe(Condition.visible);
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }
}