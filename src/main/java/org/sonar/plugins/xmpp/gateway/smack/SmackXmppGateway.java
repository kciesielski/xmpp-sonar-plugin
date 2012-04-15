package org.sonar.plugins.xmpp.gateway.smack;


import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.plugins.xmpp.config.ServerXmppConfiguration;
import org.sonar.plugins.xmpp.config.UserXmppConfiguration;
import org.sonar.plugins.xmpp.config.XmppConstants;
import org.sonar.plugins.xmpp.gateway.XmppConnectionException;
import org.sonar.plugins.xmpp.message.XmppMessage;


/**
 * XMPP Gateway implementation using the Smack library.
 */
class SmackXmppGateway implements XmppGateway {

    private static final Logger LOG = LoggerFactory.getLogger(SmackXmppGateway.class);

    private Connection connection;
    private ServerXmppConfiguration serverConfiguration;

    public SmackXmppGateway(ServerXmppConfiguration serverConfiguration) {
        this(new XMPPConnection(serverConfiguration.getTargetAddress()), serverConfiguration);

    }

    SmackXmppGateway(Connection connection, ServerXmppConfiguration serverConfiguration) {
        this.connection = connection;
        this.serverConfiguration = serverConfiguration;
    }


    public void send(UserXmppConfiguration userConfiguration, XmppMessage notification) {
        String receiverAddress = userConfiguration.getAddress();
        try {
            login();
            sendMessage(notification, receiverAddress);
        } catch (XMPPException e) {
            throw new XmppConnectionException(e);
            // No need to disconnect on exception. Smack handles this internally.
        }

        LOG.debug("Sent XMPP message: " + notification.getText() + " to " + receiverAddress);
        connection.disconnect();
    }

    private void sendMessage(XmppMessage notification, String receiverAddress) throws XMPPException {
        ChatManager chatmanager = connection.getChatManager();
        Chat newChat = chatmanager.createChat(receiverAddress, new MessageListener() {
            public void processMessage(Chat chat, Message message) {
            }
        });

        newChat.sendMessage(notification.getText());
    }

    private void login() throws XMPPException {
        String userName = serverConfiguration.getUserName();
        String password = serverConfiguration.getPassword();
        connection.connect();
        connection.login(userName, password, XmppConstants.XMPP_RESOURCE);
    }
}
