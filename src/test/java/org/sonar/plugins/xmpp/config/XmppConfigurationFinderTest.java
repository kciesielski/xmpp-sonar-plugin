package org.sonar.plugins.xmpp.config;

import junit.framework.Assert;
import org.apache.commons.configuration.Configuration;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.*;

public class XmppConfigurationFinderTest {

    public static final String RECEIVER = "receiver@jabber.com";
    public static final String USER_NAME = "user";
    public static final String SERVER = "jabber.com";
    public static final String SONAR = "sonar";
    public static final String PASSWORD = "123";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test(expected = IncompleteXmppConfigurationException.class)
    public void shouldThrowExceptionOnInvalidUserAdressesFormat() throws IncompleteXmppConfigurationException {
        Configuration configuration = mock(Configuration.class);
        UserAddressFactory addressFactory = mock(UserAddressFactory.class);
        when(addressFactory.extractUserAddress(anyString(), anyString())).thenThrow(new IncompleteXmppConfigurationException("msg"));

        XmppConfigurationFinder finder = new XmppConfigurationFinder(configuration, addressFactory);
        finder.getUserConfiguration(USER_NAME);
    }

    @Test
    public void shouldCreateProperUserConfigForCorrectState() throws IncompleteXmppConfigurationException {
        Configuration configuration = mock(Configuration.class);
        UserAddressFactory addressFactory = mock(UserAddressFactory.class);
        when(addressFactory.extractUserAddress(anyString(), eq(USER_NAME))).thenReturn(RECEIVER);

        XmppConfigurationFinder finder = new XmppConfigurationFinder(configuration, addressFactory);
        UserXmppConfiguration config = finder.getUserConfiguration(USER_NAME);

        Assert.assertEquals(config.getAddress(), RECEIVER);
    }

    @Test
    public void shouldThrowExceptionOnMissingServerAddress() throws IncompleteXmppConfigurationException {
        Configuration configuration = mock(Configuration.class);
        when(configuration.getString(anyString())).thenReturn("");
        UserAddressFactory addressFactory = mock(UserAddressFactory.class);
        when(addressFactory.extractUserAddress(anyString(), anyString())).thenReturn(RECEIVER);
        thrown.expect(IncompleteXmppConfigurationException.class);
        thrown.expectMessage("XMPP Server address is blank. Check global properties.");

        XmppConfigurationFinder finder = new XmppConfigurationFinder(configuration, addressFactory);
        finder.getServerConfiguration();
    }

    @Test
    public void shouldThrowExceptionOnMissingServerUsername() throws IncompleteXmppConfigurationException {
        Configuration configuration = mock(Configuration.class);
        when(configuration.getString(eq(XmppConstants.XMPP_SERVER_PROPERTY))).thenReturn(SERVER);
        when(configuration.getString(eq(XmppConstants.XMPP_PASSWORD_PROPERTY))).thenReturn(PASSWORD);
        UserAddressFactory addressFactory = mock(UserAddressFactory.class);
        when(addressFactory.extractUserAddress(anyString(), anyString())).thenReturn(RECEIVER);
        thrown.expect(IncompleteXmppConfigurationException.class);
        thrown.expectMessage("XMPP Sonar server username is blank. Check global properties.");

        XmppConfigurationFinder finder = new XmppConfigurationFinder(configuration, addressFactory);
        finder.getServerConfiguration();
    }

    @Test
    public void shouldThrowExceptionOnMissingServerPassword() throws IncompleteXmppConfigurationException {
        Configuration configuration = mock(Configuration.class);
        when(configuration.getString(eq(XmppConstants.XMPP_SERVER_PROPERTY))).thenReturn(SERVER);
        when(configuration.getString(eq(XmppConstants.XMPP_SONAR_USER_PROPERTY))).thenReturn(SONAR);
        UserAddressFactory addressFactory = mock(UserAddressFactory.class);
        when(addressFactory.extractUserAddress(anyString(), anyString())).thenReturn(RECEIVER);
        thrown.expect(IncompleteXmppConfigurationException.class);
        thrown.expectMessage("XMPP Server password is blank. Check global properties.");

        XmppConfigurationFinder finder = new XmppConfigurationFinder(configuration, addressFactory);
        finder.getServerConfiguration();
    }

    @Test
    public void shouldCreateServerConfigObjectForCorrectGlobalConfigData() throws IncompleteXmppConfigurationException {
        Configuration configuration = mock(Configuration.class);
        when(configuration.getString(eq(XmppConstants.XMPP_SERVER_PROPERTY))).thenReturn(SERVER);
        when(configuration.getString(eq(XmppConstants.XMPP_SONAR_USER_PROPERTY))).thenReturn(SONAR);
        when(configuration.getString(eq(XmppConstants.XMPP_PASSWORD_PROPERTY))).thenReturn(PASSWORD);
        UserAddressFactory addressFactory = mock(UserAddressFactory.class);
        when(addressFactory.extractUserAddress(anyString(), anyString())).thenReturn(RECEIVER);

        XmppConfigurationFinder finder = new XmppConfigurationFinder(configuration, addressFactory);
        ServerXmppConfiguration serverConfiguration = finder.getServerConfiguration();

        Assert.assertEquals(SERVER, serverConfiguration.getTargetAddress());
        Assert.assertEquals(SONAR, serverConfiguration.getUserName());
        Assert.assertEquals(PASSWORD, serverConfiguration.getPassword());
    }
}
