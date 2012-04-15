package org.sonar.plugins.xmpp.message;

import org.sonar.api.ServerExtension;
import org.sonar.api.notifications.Notification;

public interface XmppMessageFactory extends ServerExtension {
    XmppMessage create(Notification notification);

    boolean matches(Notification notification);
}
