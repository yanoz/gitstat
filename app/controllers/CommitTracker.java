package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

public class CommitTracker {

    private GHOrganization organization;

    private static CommitTracker instance;

    private CommitTracker() throws IOException {
        GitHub github = GitHub.connect();
        organization = github.getOrganization("novapost");
    }

    public static CommitTracker getInstance() throws IOException {
        if (instance == null) {
            instance = new CommitTracker();
        }
        return instance;
    }

    public Map<String, List<GHCommit>> getBackCommits(Date fromDate, Date toDate) throws IOException {
        Map<String, List<GHCommit>> commitsByRepo = new HashMap<>();
        commitsByRepo.put("flow", getCommitsByRepoAndPeriod("flow", fromDate, toDate));
        commitsByRepo.put("sae", getCommitsByRepoAndPeriod("sae", fromDate, toDate));
        commitsByRepo.put("delivery", getCommitsByRepoAndPeriod("delivery", fromDate, toDate));
        commitsByRepo.put("sign", getCommitsByRepoAndPeriod("sign", fromDate, toDate));
        commitsByRepo.put("supernova", getCommitsByRepoAndPeriod("supernova", fromDate, toDate));
        commitsByRepo.put("reckon", getCommitsByRepoAndPeriod("reckon", fromDate, toDate));
        commitsByRepo.put("audit", getCommitsByRepoAndPeriod("audit", fromDate, toDate));
        return commitsByRepo;

    }

    private List<GHCommit> getCommitsByRepoAndPeriod(String repo, Date fromDate, Date toDate) throws IOException {
        List<GHCommit> commits = new ArrayList<>();
        GHRepository repository = organization.getRepository(repo);
        int nbCommit = 0;
        for (Iterator<GHCommit> ite = repository.listCommits().iterator(); ite.hasNext();) {
            // GHCommit commit = ite.next();
            commits.add(ite.next());
            // if (commit.getLastStatus().getCreatedAt().after(fromDate) &&
            // commit.getLastStatus().getCreatedAt().before(toDate)) {
            // nbCommit++;
            // }
        }
        return commits;
    }
}
