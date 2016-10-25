package edu.xored.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/issues")
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

    @GetMapping(value = "/{hash}")
    public Issue getIssue(@PathVariable("hash") long hash) {
        Issue issue = issueMap.get(hash);
        if (issue == null) {
            throw new IssueNotFoundException();
        }
        return issue;
    }

    @GetMapping(params = {"status"})
    public List<Issue> getIssuesByStatus(@RequestParam("status") Issue.Status status){
        List<Issue> statusIssueList = new ArrayList<Issue>();
        for(Map.Entry<Long, Issue> entry : issueMap.entrySet()){
            if(status==entry.getValue().getStatus()){
                statusIssueList.add(entry.getValue());
            }
        }
        if(statusIssueList.isEmpty()){
            throw new IssueNotFoundStatusException();
        }
        return statusIssueList;
    }

    @GetMapping(params = {"description"})
    public List<Issue> getIssuesByDescription(@RequestParam("description") String description){
        List<Issue> descriptionIssueList = new ArrayList<Issue>();
        for(Map.Entry<Long, Issue> entry : issueMap.entrySet()){
            if(entry.getValue().getDescription().contains(description)){
                descriptionIssueList.add(entry.getValue());
            }
        }
        if(descriptionIssueList.isEmpty()){
            throw new IssueNotFoundDescriptionException();
        }
        return descriptionIssueList;
    }

    @GetMapping(params = {"summary"})
    public List<Issue> getIssuesBySummary(@RequestParam("summary") String summary){
        List<Issue> summaryIssueList = new ArrayList<Issue>();
        for(Map.Entry<Long, Issue> entry : issueMap.entrySet()){
            if(entry.getValue().getSummary().contains(summary)){
                summaryIssueList.add(entry.getValue());
            }
        }
        if(summaryIssueList.isEmpty()){
            throw new IssueNotFoundSummaryException();
        }
        return summaryIssueList;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Issue not found")
    private class IssueNotFoundException extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Status not found")
    private class IssueNotFoundStatusException extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Description not found")
    private class IssueNotFoundDescriptionException extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Summary not found")
    private class IssueNotFoundSummaryException extends RuntimeException {
    }

}
