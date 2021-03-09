package org.avni_bahmni_integration.migrator.service;

import org.avni_bahmni_integration.migrator.domain.OpenMRSForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenMRSServiceTest {
    @Autowired
    private OpenMRSService openMRSService;
    @Autowired
    private ImplementationConfigurationRepository implementationConfigurationRepository;

    @Test
    public void addConceptsToForms() throws SQLException {
        List<OpenMRSForm> forms = implementationConfigurationRepository.getForms("bahmni-forms.json");
        openMRSService.addConceptsToForms(forms);
        assertNotEquals(0, forms.get(0).getConcepts().size());
    }
}