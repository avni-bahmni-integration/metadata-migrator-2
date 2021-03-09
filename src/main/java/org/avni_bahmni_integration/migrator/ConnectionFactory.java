package org.avni_bahmni_integration.migrator;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.avni_bahmni_integration.migrator.config.AvniConfig;
import org.avni_bahmni_integration.migrator.config.BahmniConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Component
public class ConnectionFactory {
    @Autowired
    private BahmniConfig bahmniConfig;
    @Autowired
    private AvniConfig avniConfig;

    public Connection getMySqlConnection() {
        try {
            JSch jsch = new JSch();
            jsch.addIdentity(bahmniConfig.getSshPrivateKey());
            Session session = jsch.getSession(bahmniConfig.getSshUser(), bahmniConfig.getSshHost(), bahmniConfig.getSshPort());
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            session.setPortForwardingL(bahmniConfig.getLocalPort(), bahmniConfig.getOpenMrsMySqlServerFromSSHHost(), bahmniConfig.getOpenMrsMySqlPort());

            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://" + bahmniConfig.getOpenMrsMySqlServerFromSSHHost() + ":" + bahmniConfig.getLocalPort() + "/";

            Class.forName(driver);
            return DriverManager.getConnection(url + bahmniConfig.getOpenMrsMySqlDatabase(), bahmniConfig.getOpenMrsMySqlUser(), bahmniConfig.getOpenMrsMySqlPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getAvniConnection() {
        try {
//            JSch jsch = new JSch();
//            jsch.addIdentity(avniConfig.getSshPrivateKey());
//            Session session = jsch.getSession(avniConfig.getSshUser(), avniConfig.getSshHost(), avniConfig.getSshPort());
//            session.setConfig("StrictHostKeyChecking", "no");
//            session.connect();
//            session.setPortForwardingL(avniConfig.getLocalPort(), "10.20.2.65", avniConfig.getAvniPostgresPort());
//
            String driver = "org.postgresql.Driver";
            String url = "jdbc:postgresql://localhost:" + avniConfig.getLocalPort() + "/";

            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url + avniConfig.getAvniPostgresDatabase(), avniConfig.getAvniPosgresUser(), avniConfig.getAvniPostgresPassword());
            Statement statement = connection.createStatement();
            statement.execute("set role bahmni_ashwini_integration");
            statement.close();
            return connection;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}