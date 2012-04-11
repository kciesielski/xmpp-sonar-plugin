package org.sonar.plugins.xmpp.message;


import org.sonar.api.ServerExtension;
import org.sonar.api.notifications.Notification;

public class XmppMessageFactory implements ServerExtension {

    public XmppMessageContent create(Notification notification) {
        return new XmppMessageContent("Hello from plugin! Notification: " + notification.getType());
    }
}
