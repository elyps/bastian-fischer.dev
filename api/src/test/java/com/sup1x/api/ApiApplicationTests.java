package com.sup1x.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
class ApiApplicationTests {

    /*@Test
    void contextLoads() {
    }*/

   /* @Test
    public void whenDecryptedPasswordNeeded_GetFromService() {
        System.setProperty("jasypt.encryptor.password", "password");
        PropertyServiceForJasyptStarter service = appCtx
                .getBean(PropertyServiceForJasyptStarter.class);

        assertEquals("Password@1", service.getProperty());

        Environment environment = appCtx.getBean(Environment.class);

        assertEquals(
                "Password@1",
                service.getPasswordUsingEnvironment(environment));
    }*/


}
