package org.avni_bahmni_integration.migrator.service;

import org.avni_bahmni_integration.migrator.domain.OpenMRSForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BahmniToAvniService {
    @Autowired
    private OpenMRSService openMRSService;
    @Autowired
    private ImplementationConfigurationRepository implementationConfigurationRepository;

    public void migrateForms() {
        List<OpenMRSForm> forms = implementationConfigurationRepository.getForms("bahmni-forms.json");
        openMRSService.addConceptsToForms(forms);
    }
}