package org.sonar.plugins.xmpp.config;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.sonar.api.ServerExtension;

/**
 * Class resonsible for retrieving global and user Xmpp configuration.
 */
public class XmppConfigurationFinder implements ServerExtension {

    private Configuration globalSonarConfiguration;
    private UserAddressFactory userAddressFactory;

    XmppConfigurationFinder(Configuration configuration, UserAddressFactory userAddressFactory) {
        this.globalSonarConfiguration = configuration;
        this.userAddressFactory = userAddressFactory;
    }

    public XmppConfigurationFinder(Configuration globalSonarConfiguration) {
        this(globalSonarConfiguration, new UserAddressFactory());
    }

    public UserXmppConfiguration getUserConfiguration(String userName)
            throws IncompleteXmppConfigurationException {
        String allAdresses = globalSonarConfiguration.getString(XmppConstants.XMPP_USER_ADDRESSES);

        String address = userAddressFactory.extractUserAddress(allAdresses, userName);
        return new UserXmppConfiguration(address);
    }

    public ServerXmppConfiguration getServerConfiguration()
            throws IncompleteXmppConfigurationException {
        String serverAddress = globalSonarConfiguration.getString(XmppConstants.XMPP_SERVER_PROPERTY);

        if (StringUtils.isBlank(serverAddress)) {
            throw new IncompleteXmppConfigurationException("XMPP Server address is blank. Check global properties.");
        }
        String password = globalSonarConfiguration.getString(XmppConstants.XMPP_PASSWORD_PROPERTY);
        if (StringUtils.isBlank(password)) {
            throw new IncompleteXmppConfigurationException("XMPP Server password is blank. Check global properties.");
        }

        String sonarUser = globalSonarConfiguration.getString(XmppConstants.XMPP_SONAR_USER_PROPERTY);
        if (StringUtils.isBlank(sonarUser)) {
            throw new IncompleteXmppConfigurationException("XMPP Sonar server username is blank. Check global properties.");
        }
        return new ServerXmppConfiguration(sonarUser, password, serverAddress);

    }

}
