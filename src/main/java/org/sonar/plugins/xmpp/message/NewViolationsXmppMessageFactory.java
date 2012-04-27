package org.sonar.plugins.xmpp.message;


import org.apache.commons.configuration.Configuration;
import org.sonar.api.CoreProperties;
import org.sonar.api.notifications.Notification;

public class NewViolationsXmppMessageFactory implements XmppMessageFactory {

    private Configuration configuration;

    public NewViolationsXmppMessageFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SimpleXmppMessage create(Notification notification) {

        StringBuilder stringBuilder = new StringBuilder();

        String projectName = notification.getFieldValue("projectName");
        String violationsCount = notification.getFieldValue("count");
        String fromDate = notification.getFieldValue("fromDate");

        stringBuilder.append("Project: ").append(projectName).append('\n');
        stringBuilder.append(violationsCount).append(" new violations introduced since ").append(fromDate).append('\n');
        appendFooter(stringBuilder, notification);

        return new SimpleXmppMessage(stringBuilder.toString());
    }

    private void appendFooter(StringBuilder sb, Notification notification) {
        String projectKey = notification.getFieldValue("projectKey");
        sb.append("\n")
                .append("See it in Sonar: ").append(getServerBaseURL()).append("/drilldown/measures/").append(projectKey)
                .append("?metric=new_violations&period=1\n");
    }

    public boolean matches(Notification notification) {
        return ("new-violations".equals(notification.getType()));
    }

    public String getServerBaseURL() {
        return configuration.getString(CoreProperties.SERVER_BASE_URL, CoreProperties.SERVER_BASE_URL_DEFAULT_VALUE);
    }
}
