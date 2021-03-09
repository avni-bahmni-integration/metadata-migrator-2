package org.avni_bahmni_integration.migrator;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionFactory {
    @Autowired
    private BahmniConfig bahmniConfig;

    public Connection createMySQLConnection() {
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
}