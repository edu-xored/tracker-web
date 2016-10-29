package edu.xored.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/issues")
public class IssueController {
    // TODO: temporary issues storage for testing purposes. Remove when implement access to real issues through CLI core.
    private static Map<Long, Issue> issueMap = new HashMap<Long, Issue>();

    static {
        Issue firstIssue = new Issue(
                0L,
                "First issue",
                "First issue's description",
                Issue.Status.OPEN
        );
        firstIssue.addComment(new Comment("some author", "some content"));

        Issue secondIssue = new Issue(
                1L,
                "Second issue",
                "Second issue's description",
                Issue.Status.CLOSED
        );
        secondIssue.addComment(new Comment("another author", "another content"));

        Issue thirdIssue = new Issue(
                2L,
                "Third issue",
                "Third issue's description",
                Issue.Status.OPEN
        );

        Issue fourthIssue = new Issue(
                3L,
                "Fourth issue",
                "Fourth issue's description",
                Issue.Status.CLOSED
        );

        issueMap.put(firstIssue.getHash(), firstIssue);
        issueMap.put(secondIssue.getHash(), secondIssue);
        issueMap.put(thirdIssue.getHash(), thirdIssue);
        issueMap.put(fourthIssue.getHash(), fourthIssue);
    }

    @PostMapping
    public Issue postIssue(@RequestBody Issue issue) {
        issue.setHash(issueMap.size());
        issueMap.put(issue.getHash(), issue);
        return issue;
    }

    @GetMapping(value = "/{hash}")
    public Issue getIssue(@PathVariable("hash") long hash) {
        Issue issue = issueMap.get(hash);
        if (issue == null) {
            throw new IssueNotFoundException();
        }
        return issue;
    }

    @PutMapping(value = "/{hash}")
    public Issue putIssue(@PathVariable("hash") long hash,
                          @RequestBody Issue issue) {
        if (!issueMap.containsKey(hash)) {
            throw new IssueNotFoundException();
        }
        List<Comment> comments = issueMap.get(hash).getComments();
        issue.setHash(hash);
        issueMap.put(hash, issue);
        issue.addComments(comments);
        return issue;
    }

    @DeleteMapping(value = "/{hash}")
    public void deleteIssue(@PathVariable("hash") long hash) {
        if (issueMap.get(hash) == null) {
            throw new IssueNotFoundException();
        }
        issueMap.remove(hash);
    }

    @PatchMapping(value = "/{hash}")
    public Issue patchIssue(@PathVariable("hash") long hash,
                            @RequestBody Issue patchedIssue) {
        Issue issue = issueMap.get(hash);
        if (issue == null) {
            throw new IssueNotFoundException();
        }
        if (patchedIssue.getSummary() != null) {
            issue.setSummary(patchedIssue.getSummary());
        }
        if (patchedIssue.getDescription() != null) {
            issue.setDescription(patchedIssue.getDescription());
        }
        if (patchedIssue.getStatus() != null) {
            issue.setStatus(patchedIssue.getStatus());
        }
        if (patchedIssue.getComments() != null) {
            issue.addComments(patchedIssue.getComments());
        }
        return issue;
    }

    @GetMapping(params = {"status"})
    public List<Issue> getIssuesByStatus(@RequestParam("status") Issue.Status status) {
        List<Issue> statusIssueList = new ArrayList<Issue>();
        for(Map.Entry<Long, Issue> entry : issueMap.entrySet()) {
            if(status==entry.getValue().getStatus()) {
                statusIssueList.add(entry.getValue());
            }
        }
        return statusIssueList;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Issue not found")
    private class IssueNotFoundException extends RuntimeException {
    }
}
