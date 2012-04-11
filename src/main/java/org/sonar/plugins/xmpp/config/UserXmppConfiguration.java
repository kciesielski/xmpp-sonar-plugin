package org.sonar.plugins.xmpp.config;

/**
 * Represents XMPP configuration of a single user.
 */
public class UserXmppConfiguration {

    private String address;

    public UserXmppConfiguration(String address) {
        this.address = address;
    }

    public String getAddress() {

        return address;
    }
}
