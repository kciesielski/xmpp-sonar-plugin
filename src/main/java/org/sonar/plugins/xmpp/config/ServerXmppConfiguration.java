package org.sonar.plugins.xmpp.config;

public class ServerXmppConfiguration {

    private String userName;
    private String password;
    private String targetAddress;

    public ServerXmppConfiguration(String userName, String password, String targetAddress) {
        this.userName = userName;
        this.password = password;
        this.targetAddress = targetAddress;
    }

    public String getTargetAddress() {

        return targetAddress;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }
}
