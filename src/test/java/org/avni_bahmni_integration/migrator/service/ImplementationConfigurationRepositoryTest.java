package org.avni_bahmni_integration.migrator.service;

import org.avni_bahmni_integration.migrator.domain.OpenMRSForm;
import org.avni_bahmni_integration.migrator.service.ImplementationConfigurationRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ImplementationConfigurationRepositoryTest {
    @Test
    public void getForms() {
        ImplementationConfigurationRepository implementationConfigurationRepository = new ImplementationConfigurationRepository();
        List<OpenMRSForm> forms = implementationConfigurationRepository.getForms("bahmni-forms.json");
        assertNotNull(forms);
        assertEquals(6, forms.size());
    }
}