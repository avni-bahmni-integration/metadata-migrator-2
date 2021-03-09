package org.avni_bahmni_integration.migrator;

import org.avni_bahmni_integration.migrator.config.BahmniConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConfigTest {
    @Autowired
    private BahmniConfig config;

    @Test
    public void readCustomProperties() {
        assertNotNull(config.getOpenMrsMySqlUser());
    }
}