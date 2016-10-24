package com.edu.xored.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

        Issue secondIssue = new Issue(
                1L,
                "Second issue",
                "Second issue's description",
                Issue.Status.CLOSED
        );

        issueMap.put(firstIssue.getHash(), firstIssue);
        issueMap.put(secondIssue.getHash(), secondIssue);
    }

    @GetMapping(value = "/{hash}")
    public Issue getIssue(@PathVariable("hash") long hash) {
        Issue issue = issueMap.get(hash);
        if (issue == null) {
            throw new IssueNotFoundException();
        }
        return issue;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Issue not found")
    private class IssueNotFoundException extends RuntimeException {
    }

}
