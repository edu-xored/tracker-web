package com.edu.xored.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = {"/issue", "/issue/{hash}"})
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

    @GetMapping
    public ResponseEntity<Issue> get(@PathVariable("hash") long hash) {
        if (!issueMap.containsKey(hash)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(issueMap.get(hash));
    }
}
