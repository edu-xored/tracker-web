package edu.xored.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = "/api/issues")
public class IssueController {

    private IssueRepository issueRepository = new IssueRepositoryImpl();

    @PostMapping
    public Issue postIssue(@RequestBody Issue issue) {
        return issueRepository.save(issue);
    }

    @GetMapping(value = "/{hash}")
    public Issue getIssue(@PathVariable("hash") long hash) {
        throwIfIssueNotFound(hash);

        return issueRepository.findOne(hash);
    }

    @PutMapping(value = "/{hash}")
    public Issue putIssue(@PathVariable("hash") long hash,
                          @RequestBody Issue issue) {
        throwIfIssueNotFound(hash);

        return issueRepository.replace(hash, issue);
    }

    @DeleteMapping(value = "/{hash}")
    public void deleteIssue(@PathVariable("hash") long hash) {
        throwIfIssueNotFound(hash);

        issueRepository.delete(hash);
    }

    @PatchMapping(value = "/{hash}")
    public Issue patchIssue(@PathVariable("hash") long hash,
                            @RequestBody Issue patchedIssue) {
        throwIfIssueNotFound(hash);

        return issueRepository.replace(hash, issueRepository.findOne(hash).updateIssue(patchedIssue));
    }

    @GetMapping
    public Collection<Issue> getIssues(@RequestParam(value = "status", required = false) Issue.Status status) {
        return StreamSupport.stream(issueRepository.findAll(status).spliterator(), false)
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/{hash}/comments")
    public void postComment(@RequestBody Comment comment,
                            @PathVariable("hash") long hash) {
        throwIfIssueNotFound(hash);

        issueRepository.postComment(comment, hash);
    }

    private void throwIfIssueNotFound(long hash) {
        if (issueRepository.exists(hash)) {
            throw new IssueNotFoundException();
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Issue not found")
    private class IssueNotFoundException extends RuntimeException {
    }
}
