package org.avni_bahmni_integration.migrator;

import com.jcraft.jsch.JSchException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class ConnectionFactoryExternalTest {
    @Autowired
    private ConnectionFactory connectionFactory;

    @Test
    public void connect() throws JSchException, SQLException, ClassNotFoundException {
        Connection mySQLConnection = connectionFactory.createMySQLConnection();
        mySQLConnection.close();
    }
}