package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;

public class IssueTracker {

    private GHOrganization organization;

    private GHRepository repository;

    private static IssueTracker instance;

    private IssueTracker() throws IOException {
        GitHub github = GitHub.connect();
        System.out.println("Connected");
        organization = github.getOrganization("novapost");
        repository = organization.getRepository("ops_on_demand");
    }

    public static IssueTracker getInstance() throws IOException {
        if (instance == null) {
            instance = new IssueTracker();
        }
        return instance;
    }

    public List<GHIssue> getClosedIssues(Date fromDate) throws IOException {
        List<GHIssue> issueList = new ArrayList<>();
        for (Iterator<GHIssue> ite = getIssuesByState(GHIssueState.CLOSED).iterator(); ite.hasNext();) {
            GHIssue issue = ite.next();
            System.out.println(issue.getTitle());
            if (issue.getClosedAt().after(fromDate)) {
                issueList.add(ite.next());
            }
        }
        return issueList;
    }

    private PagedIterable<GHIssue> getIssuesByState(GHIssueState state) throws IOException {
        return repository.listIssues(state);
    }
}
