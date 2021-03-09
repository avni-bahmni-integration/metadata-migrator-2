package org.avni_bahmni_integration.migrator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AvniConfig {
    @Value("${avni.postgres.user}")
    private String avniPosgresUser;

    @Value("${avni.postgres.password}")
    private String avniPostgresPassword;

    @Value("${avni.postgres.database}")
    private String avniPostgresDatabase;

    @Value("${avni.postgres.server.from.ssh_host}")
    private String avniPostgresServerFromSSHHost;

    @Value("${avni.postgres.port}")
    private int avniPostgresPort;

    @Value("${avni.server.ssh.local.port}")
    private int localPort;
    @Value("${avni.server.ssh.host}")
    private String sshHost;
    @Value("${avni.server.ssh.host.port}")
    private int sshPort;
    @Value("${avni.server.ssh.user}")
    private String sshUser;
    @Value("${avni.server.ssh.private.key}")
    private String sshPrivateKey;

    public String getAvniPosgresUser() {
        return avniPosgresUser;
    }

    public String getAvniPostgresPassword() {
        return avniPostgresPassword;
    }

    public String getAvniPostgresDatabase() {
        return avniPostgresDatabase;
    }

    public String getAvniPostgresServerFromSSHHost() {
        return avniPostgresServerFromSSHHost;
    }

    public int getLocalPort() {
        return localPort;
    }

    public String getSshHost() {
        return sshHost;
    }

    public int getSshPort() {
        return sshPort;
    }

    public String getSshUser() {
        return sshUser;
    }

    public int getAvniPostgresPort() {
        return avniPostgresPort;
    }

    public String getSshPrivateKey() {
        return sshPrivateKey;
    }
}