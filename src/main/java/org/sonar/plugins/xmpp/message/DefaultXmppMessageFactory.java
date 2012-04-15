package org.sonar.plugins.xmpp.message;

import org.sonar.api.notifications.Notification;

public class DefaultXmppMessageFactory implements XmppMessageFactory {
    public XmppMessage create(Notification notification) {
        return new SimpleXmppMessage("You have one new notification of type: " + notification.getType());
    }

    public boolean matches(Notification notification) {
        return false;
    }
}
