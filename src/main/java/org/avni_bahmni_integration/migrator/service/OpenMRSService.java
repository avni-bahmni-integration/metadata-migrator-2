package org.avni_bahmni_integration.migrator.service;

import org.avni_bahmni_integration.migrator.ConnectionFactory;
import org.avni_bahmni_integration.migrator.domain.OpenMRSConcept;
import org.avni_bahmni_integration.migrator.domain.OpenMRSForm;
import org.avni_bahmni_integration.migrator.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class OpenMRSService {
    @Autowired
    private ConnectionFactory connectionFactory;

    public void addConceptsToForms(List<OpenMRSForm> formList) {
        Connection mySQLConnection = null;
        try {
            mySQLConnection = connectionFactory.createMySQLConnection();
            PreparedStatement preparedStatement = mySQLConnection.prepareStatement(FileUtil.readFile("form-elements.sql"));
            for (OpenMRSForm form : formList) {
                preparedStatement.setInt(1, form.getFormId());
                preparedStatement.setInt(2, form.getFormId());
                preparedStatement.setInt(3, form.getFormId());
                preparedStatement.setInt(4, form.getFormId());

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    form.addConcept(resultSet.getString(1), resultSet.getString(2));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                mySQLConnection.close();
            } catch (SQLException ee) {
                throw new RuntimeException(ee);
            }
        }
    }
}