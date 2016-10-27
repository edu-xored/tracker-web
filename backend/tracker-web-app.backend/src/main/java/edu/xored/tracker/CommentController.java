package edu.xored.tracker;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/issues/{issueHash}/comments")
public class CommentController {
    @PostMapping
    public void post(@RequestBody Comment comment, @PathVariable("issueHash") long hash) {
        IssueController issueController = new IssueController();
        Issue issue = issueController.getIssue(hash);
        issue.addComment(comment);
    }
}
