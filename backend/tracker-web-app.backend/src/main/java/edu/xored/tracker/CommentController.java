package edu.xored.tracker;

import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    @RequestMapping(value = "/postcomment", method = RequestMethod.POST)
    public void post(@RequestBody Comment comment, @RequestParam("issueHash") long issueHash) {
        IssueController issueController = new IssueController();
        Issue issue = issueController.getIssue(issueHash);
        comment.setCreatedDateTime();
        issue.addComment(comment);
    }
}
