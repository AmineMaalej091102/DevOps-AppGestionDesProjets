package tn.esprit.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

class BackendApplicationTests {

    @Test
    void applicationIsConfiguredAsSpringBootApplication() {
        org.junit.jupiter.api.Assertions.assertTrue(
                BackendApplication.class.isAnnotationPresent(SpringBootApplication.class));
    }
}
