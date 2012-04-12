package org.sonar.plugins.xmpp.config;

import junit.framework.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UserAddressFactoryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private static final String USER_NAME = "user";

    @Test
    public void shouldThrowExceptionOnBlankList() throws IncompleteXmppConfigurationException {

        thrown.expect(IncompleteXmppConfigurationException.class);
        thrown.expectMessage(UserAddressFactory.MSG_MALFORMED_LIST_OF_RECEIVERS + ": empty field");

        UserAddressFactory addressFactory = new UserAddressFactory();
        addressFactory.extractUserAddress("", USER_NAME);

    }

    @Test
    public void shouldThrowExceptionOnNoAssignments() throws IncompleteXmppConfigurationException {

        thrown.expect(IncompleteXmppConfigurationException.class);
        thrown.expectMessage(UserAddressFactory.MSG_MALFORMED_LIST_OF_RECEIVERS + ": incorrect asignment: some text without assignments");

        UserAddressFactory addressFactory = new UserAddressFactory();
        addressFactory.extractUserAddress("some text without assignments", USER_NAME);

    }

    @Test
    public void shouldThrowExceptionOnDirtyListOfAssignments() throws IncompleteXmppConfigurationException {

        thrown.expect(IncompleteXmppConfigurationException.class);
        thrown.expectMessage(UserAddressFactory.MSG_MALFORMED_LIST_OF_RECEIVERS + ": incorrect asignment: dirt");

        UserAddressFactory addressFactory = new UserAddressFactory();
        addressFactory.extractUserAddress("user2=jabber@j.com,dirt", USER_NAME);
    }

    @Test
    public void shouldReturnAddressForUserPresentOnWellFormedList() throws IncompleteXmppConfigurationException {

        UserAddressFactory addressFactory = new UserAddressFactory();
        String address = addressFactory.extractUserAddress("bob=robert@s1.pl,user=jabber@j.com,paul=qwe@asd.zxc", USER_NAME);

        Assert.assertEquals("jabber@j.com", address);
    }

    @Test
    public void shouldThrowExceptionOnUserWithNoAddress() throws IncompleteXmppConfigurationException {
        thrown.expect(IncompleteXmppConfigurationException.class);
        thrown.expectMessage(UserAddressFactory.MSG_MALFORMED_LIST_OF_RECEIVERS);

        UserAddressFactory addressFactory = new UserAddressFactory();
        addressFactory.extractUserAddress("bob=,user=jabber@j.com,paul=qwe@asd.zxc", USER_NAME);
    }

    @Test
    public void shouldThrowExceptionOnUserWithNoName() throws IncompleteXmppConfigurationException {
        thrown.expect(IncompleteXmppConfigurationException.class);
        thrown.expectMessage(UserAddressFactory.MSG_MALFORMED_LIST_OF_RECEIVERS);

        UserAddressFactory addressFactory = new UserAddressFactory();
        addressFactory.extractUserAddress("=fg@qwe.eee,user=jabber@j.com,paul=qwe@asd.zxc", USER_NAME);
    }

    @Test
    public void shouldThrowExceptionWhenUserNotPresentOnList() throws IncompleteXmppConfigurationException {
        thrown.expect(IncompleteXmppConfigurationException.class);
        thrown.expectMessage(UserAddressFactory.MSG_MALFORMED_LIST_OF_RECEIVERS + ": missing address for user " + USER_NAME);

        UserAddressFactory addressFactory = new UserAddressFactory();
        addressFactory.extractUserAddress("bob=robert@s1.pl,user3=jabber@j.com,paul=qwe@asd.zxc", USER_NAME);
    }

}
