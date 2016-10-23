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

    Issue issue1 = new Issue(0, "first issue", "description of first issue", "Open");
    Issue issue2 = new Issue(1, "second issue", "description of second issue", "Closed");
    Issue issue3 = new Issue(2, "third issue", "description of third issue", "Open");

    @GetMapping
    public ResponseEntity<Issue> get(@PathVariable("hash") long hash) {
        return ResponseEntity.status(HttpStatus.OK).body(issueMap.get(hash));
    }
}
