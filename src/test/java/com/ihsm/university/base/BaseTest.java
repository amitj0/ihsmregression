package com.ihsm.university.base;

import org.testng.annotations.BeforeClass;
import io.restassured.RestAssured;
import config.ConfigReader;

public class BaseTest {

    @BeforeClass
    public void setup() {

        RestAssured.baseURI = ConfigReader.get("baseUrl");

    }
}