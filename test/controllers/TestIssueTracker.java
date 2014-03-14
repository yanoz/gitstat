package controllers;

import java.io.IOException;

import org.joda.time.DateTime;
import org.junit.Test;

public class TestIssueTracker {

    @Test
    public void shouldGetIssues() throws IOException {
        IssueTracker.getInstance().getClosedIssues(new DateTime(2013, 12, 9, 0, 0).toDate());
    }
}
