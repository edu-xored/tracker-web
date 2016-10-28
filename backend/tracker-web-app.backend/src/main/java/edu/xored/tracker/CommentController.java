package edu.xored.tracker;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(value = "/issues/{issueHash}/comments")
public class CommentController {
    @PostMapping
    public void postComment(@RequestBody Comment comment, @PathVariable("issueHash") long hash) {
        Issue issue = IssueController.getIssue(hash);
        issue.addComment(comment);
    }
}
