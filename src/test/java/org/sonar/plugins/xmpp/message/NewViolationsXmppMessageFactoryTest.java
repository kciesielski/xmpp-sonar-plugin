package org.sonar.plugins.xmpp.message;

import org.apache.commons.configuration.Configuration;
import org.junit.Test;
import org.sonar.api.notifications.Notification;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class NewViolationsXmppMessageFactoryTest {

    @Test
    public void shouldCreateMessageWithExpectedContent() {
        Configuration configuration = mock(Configuration.class);
        when(configuration.getString(anyString(), anyString())).thenReturn("http://localhost");
        NewViolationsXmppMessageFactory factory = new NewViolationsXmppMessageFactory(configuration);
        String expectedMessage = "Project: test-project\n3 new violations introduced since 2012-01-03\n\nSee it in Sonar: http://localhost/drilldown/measures/3737?metric=new_violations&period=1\n";

        Notification notification = mock(Notification.class);
        when(notification.getFieldValue("projectName")).thenReturn("test-project");
        when(notification.getFieldValue("count")).thenReturn("3");
        when(notification.getFieldValue("fromDate")).thenReturn("2012-01-03");
        when(notification.getFieldValue("projectKey")).thenReturn("3737");

        SimpleXmppMessage message = factory.create(notification);

        assertEquals(expectedMessage, message.getText());


    }


}
