package org.avni_bahmni_integration.migrator.service;

import org.avni_bahmni_integration.migrator.domain.OpenMRSForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class BahmniToAvniService {
    @Autowired
    private OpenMRSService openMRSService;
    @Autowired
    private ImplementationConfigurationRepository implementationConfigurationRepository;
    @Autowired
    private AvniRepository avniRepository;

    public void migrateForms() throws SQLException {
        List<OpenMRSForm> forms = implementationConfigurationRepository.getForms("bahmni-forms.json");
        openMRSService.addConceptsToForms(forms);
        avniRepository.createForms(forms);
    }
}