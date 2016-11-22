package edu.xored.tracker;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class IssueRepositoryImpl implements IssueRepository {
    private static Map<Long, Issue> issuesMap = new HashMap<>();

    public <S extends Issue> S save(S issue) {
        issue.setHash(getIssueId());
        issuesMap.put(issue.getHash(), issue);

        return issue;
    }

    public <S extends Issue> Iterable<S> save(Iterable<S> issues) {
        if (issues == null) {
            return null;
        }

        return StreamSupport.stream(issues.spliterator(), false)
                .map(this::save)
                .collect(Collectors.toList());
    }

    public Issue findOne(Long issueId) {
        return issuesMap.getOrDefault(issueId, null);
    }

    public boolean exists(Long issueId) {
        return issuesMap.containsKey(issueId);
    }

    public Iterable<Issue> findAll() {
        return issuesMap.values();
    }

    public Iterable<Issue> findAll(Iterable<Long> issuesId) {
        List<Issue> result = new ArrayList<>();
        issuesId.forEach(issueId -> result.add(findOne(issueId)));

        return result;
    }

    public long count() {
        return (issuesMap == null)
                ? 0
                : issuesMap.size();
    }

    public void delete(Long issueId) {
        if (issueId == null) {
            return;
        }

        issuesMap.remove(issueId);
    }

    public void delete(Issue issue) {
        if (issue == null) {
            return;
        }

        delete(issue.getHash());
    }

    public void delete(Iterable<? extends Issue> issuesId) {
        issuesId.forEach(this::delete);
    }

    public void deleteAll() {
        issuesMap.clear();
    }

    public List<Issue> list() {
        return issuesMap.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public Issue replace(Long hash, Issue issue) {
        return issuesMap.replace(hash, issue);
    }

    public Iterable<Issue> findAll(Issue.Status status) {
        return issuesMap.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(entry -> entry.getStatus() == status)
                .collect(Collectors.toList());
    }

    public void postComment(Comment comment, long hash) {
        findOne(hash).addComment(comment);
    }

    private long getIssueId() {
        return issuesMap.size();
    }
}