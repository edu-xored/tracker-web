package edu.xored.tracker;

import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    @PostMapping(value = "/issues/{hash}/comments")
    public void post(@RequestBody Comment comment, @PathVariable("hash") long hash) {
        IssueController issueController = new IssueController();
        Issue issue = issueController.getIssue(hash);
        comment.setCurrentTime();
        issue.addComment(comment);
    }
}
