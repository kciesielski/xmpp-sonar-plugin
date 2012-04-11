package org.sonar.plugins.xmpp.config;

import org.sonar.api.ServerExtension;

public class XmppConfigurationFinder implements ServerExtension {
    public UserXmppConfiguration getConfiguration(String userName) {
        // TODO
        return new UserXmppConfiguration("hoborg@grolsh.pl");
    }
}
