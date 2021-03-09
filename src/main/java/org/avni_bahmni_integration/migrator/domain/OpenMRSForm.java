package org.avni_bahmni_integration.migrator.domain;

import java.util.ArrayList;
import java.util.List;

public class OpenMRSForm {
    private int formId;
    private String formName;
    private String type;

    private List<OpenMRSConcept> concepts = new ArrayList<>();

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public void addConcept(String uuid, String name) {
        OpenMRSConcept openMRSConcept = new OpenMRSConcept(uuid, name);
        concepts.add(openMRSConcept);
    }

    public List<OpenMRSConcept> getConcepts() {
        return concepts;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
