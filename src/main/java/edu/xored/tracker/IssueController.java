package edu.xored.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;

@RestController
@RequestMapping(value = "/issues")
public class IssueController {
    // TODO: temporary issues storage for testing purposes. Remove when implement access to real issues through CLI core.
    private static Map<Long, Issue> issueMap = new HashMap<Long, Issue>();

    @Autowired(required = false)
    private AttachmentService attachmentService;

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
        if (!issueMap.containsKey(hash)) {
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
        return issue.updateIssue(patchedIssue);
    }

    @GetMapping
    public Collection<Issue> getIssues(@RequestParam(value = "status", required = false) Issue.Status status) {
        if (status == null) {
            return issueMap.values();
        }
        return issueMap.values()
                .stream()
                .filter(entry -> entry.getStatus() == status)
                .collect(Collectors.toList());
    }

    @PostMapping(value = "{hash}/comments")
    public void postComment(@RequestBody Comment comment, @PathVariable("hash") long hash) {
        Issue issue = issueMap.get(hash);
        if (issue == null) {
            throw new IssueNotFoundException();
        }
        issue.addComment(comment);
    }

    @RequestMapping(value="/{hash}/upload", method=RequestMethod.POST)
    public String saveAttachment(@PathVariable("hash") long hash,
                                 @RequestParam("file") MultipartFile file) {
        if(!issueMap.containsKey(hash)) {
            throw new IssueNotFoundException();
        }
        attachmentService.saveAttachment(hash, file);
        return "Success!";
    }

    @RequestMapping(value="/{hash}/download", method=RequestMethod.GET)
    public ResponseEntity<byte[]> getAttachment(@PathVariable("hash") long hash,
                                                @RequestParam("name") String name) {
        if(!issueMap.containsKey(hash)) {
            throw new IssueNotFoundException();
        }
        byte[] bytes = attachmentService.getAttachment(hash, name).toByteArray();
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Issue not found")
    private class IssueNotFoundException extends RuntimeException {
    }
}
