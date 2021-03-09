package org.avni_bahmni_integration.migrator.service;

import org.apache.log4j.Logger;
import org.avni_bahmni_integration.migrator.ConnectionFactory;
import org.avni_bahmni_integration.migrator.domain.OpenMRSConcept;
import org.avni_bahmni_integration.migrator.domain.OpenMRSForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class AvniRepository {
    @Autowired
    private ConnectionFactory connectionFactory;
    private static Logger logger = Logger.getLogger(AvniRepository.class);

    public void createForms(List<OpenMRSForm> forms) throws SQLException {
        Connection connection = connectionFactory.getAvniConnection();
        connection.setAutoCommit(false);
        try {
            String encounterTypeInsert = "insert into encounter_type (name, uuid, version, audit_id, organisation_id) values (?, uuid_generate_v4(), 0, create_audit(), (select id from organisation))";
            String formInsert = "insert into form (name, form_type, uuid, version, audit_id, organisation_id) values (?, ?, uuid_generate_v4(), 0, create_audit(), (select id from organisation))";
            String formElementGroupInsert = "insert into form_element_group (name, form_id, uuid, version, audit_id, organisation_id) values (?, (select id from form where name = ?), uuid_generate_v4(), 0, create_audit(), (select id from organisation))";
            String formElementInsert = "insert into form_element (name, display_order, concept_id, form_element_group_id, uuid, version, audit_id, organisation_id)  values (?, ?, (select id from concept where name = ?), (select id from form_element_group where name = ?), uuid_generate_v4(), 0, create_audit(), (select id from organisation))";
            String formMappingInsert = "insert into form_mapping (form_id, uuid, version, observations_type_entity_id, subject_type_id, audit_id, organisation_id) values ((select id from form where name = ?), uuid_generate_v4(), 0, (select id from encounter_type where name = ?), (select id from subject_type where name = 'Individual'), create_audit(), (select id from organisation))";

            PreparedStatement encounterTypePS = connection.prepareStatement(encounterTypeInsert);
            PreparedStatement formInsertPS = connection.prepareStatement(formInsert);
            PreparedStatement formElementGroupPS = connection.prepareStatement(formElementGroupInsert);
            PreparedStatement formElementPS = connection.prepareStatement(formElementInsert);
            PreparedStatement formMappingPS = connection.prepareStatement(formMappingInsert);

            for (OpenMRSForm form : forms) {
                encounterTypePS.setString(1, form.getFormName());
                encounterTypePS.executeUpdate();
                logger.info("Created encounter type: " + form.getFormName());

                formInsertPS.setString(1, form.getFormName());
                formInsertPS.setString(2, form.getType());
                formInsertPS.executeUpdate();
                logger.info("Created form: " + form.getFormName());

                formElementGroupPS.setString(1, form.getFormName());
                formElementGroupPS.setString(2, form.getFormName());
                formElementGroupPS.executeUpdate();
                logger.info("Created form element group: " + form.getFormName());

                int i = 1;
                for (OpenMRSConcept openMRSConcept : form.getConcepts()) {
                    formElementPS.setString(1, openMRSConcept.getName());
                    formElementPS.setInt(2, i++);
                    formElementPS.setString(3, openMRSConcept.getAvniConceptName());
                    formElementPS.setString(4, form.getFormName());

                    formElementPS.executeUpdate();
                }
                logger.info("Created form elements for form: " + form.getFormName());

                formMappingPS.setString(1, form.getFormName());
                formMappingPS.setString(2, form.getFormName());
                formMappingPS.executeUpdate();
                logger.info("Created form mapping for form: " + form.getFormName());
            }
            encounterTypePS.close();
            formInsertPS.close();
            formElementGroupPS.close();
            formElementPS.close();
            formMappingPS.close();

            connection.rollback();
        } catch (Exception e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }
}