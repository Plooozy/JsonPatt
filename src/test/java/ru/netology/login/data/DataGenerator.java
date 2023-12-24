package ru.netology.login.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private DataGenerator() {
    }

    private static Info sendRequest(Info user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
        return user;
    }

    public static String generateUsername() {
        var username = new Faker(new Locale("en"));
        return username.name().username();
    }

    public static String generatePassword() {
        var password = new Faker(new Locale("en"));
        return password.internet().password();
    }

    public static class Registration {
        private Registration() {
        }

        public static Info genUser(String status) {
            return new Info(generateUsername(), generatePassword(), status);
        }

        public static Info registerUser(String status) {
            return sendRequest(genUser(status));
        }
    }

    @Value
    public static class Info {
        String login;
        String password;
        String status;
    }
}