package org.sonar.plugins.xmpp.config;

import org.apache.commons.lang.StringUtils;

/**
 * Utility class for extracting user address from global configuration.
 */
class UserAddressFactory {

    public static final String MSG_MALFORMED_LIST_OF_RECEIVERS = "Invalid XMPP configuration, please check receivers address assignment format.";

    private static final String SEPARATOR = ";";
    private static final String USER_ADDRESS_SEPARATOR = "=";

    /**
     * Parses global configuration to extract XMPP address for given user name.
     *
     * @param allAdresses list of all addresses in format: "user=address@server.domain;user2=adress2@server2.domain2"
     * @param userName    name of user whose address should be extracted.
     * @return address of the user.
     * @throws IncompleteXmppConfigurationException
     *          on problems with malformed list of receivers.
     */
    String extractUserAddress(String allAdresses, String userName) throws IncompleteXmppConfigurationException {
        checkEmptyFullList(allAdresses);
        String[] assignments = allAdresses.split(SEPARATOR);

        for (String assignment : assignments) {
            String[] userValues = assignment.split(USER_ADDRESS_SEPARATOR);
            checkAssignmentFormat(assignment, userValues);
            String configuredName = userValues[0];
            ensureNotBlank(configuredName);
            String address = userValues[1];
            ensureNotBlank(address);
            if (configuredName.equals(userName)) {
                return address;
            }
        }
        throw new IncompleteXmppConfigurationException(MSG_MALFORMED_LIST_OF_RECEIVERS + ": missing address for user " + userName);
    }

    private void checkAssignmentFormat(String assignment, String[] userValues) throws IncompleteXmppConfigurationException {
        if (userValues.length != 2) {
            throw new IncompleteXmppConfigurationException(MSG_MALFORMED_LIST_OF_RECEIVERS + ": incorrect asignment: " + assignment);
        }
    }

    private void checkEmptyFullList(String allAdresses) throws IncompleteXmppConfigurationException {
        if (StringUtils.isBlank(allAdresses)) {
            throw new IncompleteXmppConfigurationException(MSG_MALFORMED_LIST_OF_RECEIVERS + ": empty field");
        }
    }

    private void ensureNotBlank(String text) throws IncompleteXmppConfigurationException {
        if (StringUtils.isBlank(text)) {
            throw new IncompleteXmppConfigurationException(MSG_MALFORMED_LIST_OF_RECEIVERS);
        }
    }

}