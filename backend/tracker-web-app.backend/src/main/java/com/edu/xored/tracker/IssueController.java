package com.edu.xored.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = {"/issue", "/issue/{hash}"})
public class IssueController {

    private static Map<Long, Issue> issueMap = new HashMap<Long, Issue>();
    private static long hashCounter;

    static {

        Issue firstIssue = new Issue(
                hashCounter++,
                "First issue",
                "First issue's description",
                Issue.Status.OPEN
        );

        Issue secondIssue = new Issue(
                hashCounter++,
                "Second issue",
                "Second issue's description",
                Issue.Status.CLOSED
        );


        issueMap.put(0L, firstIssue);
        issueMap.put(1L, secondIssue);

    }

    @GetMapping
    public ResponseEntity<Issue> get(@PathVariable("hash") long hash) {
        return ResponseEntity.status(HttpStatus.OK).body(issueMap.get(hash));
    }
}
