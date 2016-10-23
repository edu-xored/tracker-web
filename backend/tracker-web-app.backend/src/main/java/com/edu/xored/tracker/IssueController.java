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

        Issue iss1 = new Issue();
        iss1.setHash(0);
        iss1.setSummary("first issue");
        iss1.setDescription("first issues description");
        iss1.setStatus(Status.OPEN);

        Issue iss2 = new Issue();
        iss2.setHash(1);
        iss2.setSummary("second issue");
        iss2.setDescription("second issues description");
        iss2.setStatus(Status.CLOSED);

        issueMap.put(0L, iss1);
        issueMap.put(1L, iss2);

    }

    @GetMapping
    public ResponseEntity<Issue> get(@PathVariable("hash") long hash) {
        return ResponseEntity.status(HttpStatus.OK).body(issueMap.get(hash));
    }
}
