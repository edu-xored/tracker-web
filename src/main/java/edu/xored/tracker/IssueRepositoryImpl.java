package edu.xored.tracker;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class IssueRepositoryImpl implements IssueRepository {

    private static final String GIT_BUG = "git bug ";
    private static final String GIT_BUG_NEW = "git bug new ";
    private static final String GIT_BUG_RESOLVE = "git bug resolve ";
    private static final int GIT_BUG_HASH_STATUS = 8;

    // TODO: remove when git bug would implement all needed commands.
    private final Map<String, Issue> issuesMap = new ConcurrentHashMap<>();

    @Override
    public Issue save(Issue issue) {
        Process theProcess;
        String commands = GIT_BUG_NEW + "-m " + '\"' + issue.getSummary()
                + "\\n" + issue.getDescription() + '\"' + "\n";
        try {
            theProcess = Runtime.getRuntime().exec("/bin/bash");
        } catch (IOException e) {
            throw new ExecutionFailedException();
        }
        try (BufferedWriter outStream = new BufferedWriter(
                new OutputStreamWriter(theProcess.getOutputStream()))) {
            outStream.write(commands);
        } catch (IOException e) {
            throw new ExecutionFailedException();
        }
        String info;
        try (BufferedReader inStream = new BufferedReader(
                new InputStreamReader(theProcess.getInputStream()))) {
            info = inStream.readLine();
            issue.setHash(info);
        } catch (IOException e) {
            throw new ExecutionFailedException();
        }
        issuesMap.put(issue.getHash(), issue);
        return issue;
    }

    @Override
    public List<Issue> save(Collection<Issue> issues) {
        if (CollectionUtils.isEmpty(issues)) {
            return Collections.emptyList();
        }
        return issues
                .stream()
                .map(this::save)
                .collect(Collectors.toList());
    }

    @Override
    public Issue findOne(String issueId) {
        Process theProcess;
        try {
            theProcess = Runtime.getRuntime().exec(GIT_BUG + issueId);
        } catch (IOException e) {
            throw new ExecutionFailedException();
        }
        String info;
        Issue issue = new Issue();
        issue.setHash(issueId);
        try (BufferedReader inStream = new BufferedReader(
                new InputStreamReader(theProcess.getInputStream()))) {
            inStream.readLine(); //issue <hash>
            info = inStream.readLine(); //Author: <author>
            issue.setAuthor(new User(info.substring(8), ""));
            info = inStream.readLine(); //Date: <date>
            info = info.substring(6);
            DateTimeFormatter dTF = DateTimeFormatter.RFC_1123_DATE_TIME;
            issue.setCreatedDateTime(LocalDateTime.parse(info, dTF));
            info = inStream.readLine();
            if (info.startsWith("open", GIT_BUG_HASH_STATUS)) {
                issue.setStatus(Issue.Status.OPEN);
            } else {
                issue.setStatus(Issue.Status.RESOLVED);
            }
            inStream.readLine(); //Empty space
            info = inStream.readLine();
            issue.setSummary(info);
            int descriptionData;
            StringBuilder description = new StringBuilder();
            while ((descriptionData = inStream.read()) != -1) {
                description.append(descriptionData);
            }
            issue.setDescription(description.toString());
            return issue;
        } catch (IOException e) {
            throw new ExecutionFailedException();
        }
    }

    @Override
    public boolean exists(String issueId) {
        Process theProcess;
        try {
            theProcess = Runtime.getRuntime().exec(GIT_BUG + issueId);
        } catch (IOException e) {
            throw new ExecutionFailedException();
        }
        String info;
        try (BufferedReader inStream = new BufferedReader(
                new InputStreamReader(theProcess.getInputStream()))) {
            info = inStream.readLine();
            return !info.startsWith("usage");
        } catch (IOException e) {
            throw new ExecutionFailedException();
        }
    }

    @Override
    public List<Issue> findAll() {
        return findAll((Issue.Status) null);
    }

    @Override
    public List<Issue> findAll(Collection<String> issuesId) {
        return issuesId.stream().map(this::findOne).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return issuesMap.size();
    }

    @Override
    public void delete(String issueId) {
        throw new IssueNotImplementedException();
    }

    @Override
    public void delete(Issue issue) {
        throw new IssueNotImplementedException();
    }

    @Override
    public void delete(Collection<? extends Issue> issues) {
        throw new IssueNotImplementedException();
    }

    @Override
    public void deleteAll() {
        throw new IssueNotImplementedException();
    }

    @Override
    public Issue replace(String hash, Issue issue) {
        Process theProcess;
        Issue oldIssue = findOne(hash);
        try {
            theProcess = Runtime.getRuntime().exec(GIT_BUG_RESOLVE + hash);
        } catch (IOException e) {
            throw new ExecutionFailedException();
        }
        String info;
        try (BufferedReader inStream = new BufferedReader(
                new InputStreamReader(theProcess.getInputStream()))) {
            info = inStream.readLine();
            if (info != null && info.startsWith("Error")) {
                throw new IssueNotFoundException(hash);
            }
        } catch (IOException e) {
            throw new ExecutionFailedException();
        }
        Issue newIssue = findOne(hash);
        issuesMap.replace(hash, oldIssue, newIssue);
        return newIssue;
    }

    @Override
    public void replaceAll() {
        findAll().forEach(issue -> replace(issue.getHash(), issue));
    }

    @Override
    public List<Issue> findAll(Issue.Status status) {
        Process theProcess;
        try {
            theProcess = Runtime.getRuntime().exec(GIT_BUG + "-a");
        } catch (IOException e) {
            throw new ExecutionFailedException();
        }
        List<Issue> result = new ArrayList<>();
        String info;
        Issue issue;
        try (BufferedReader inStream = new BufferedReader(
                new InputStreamReader(theProcess.getInputStream()))) {
            while ((info = inStream.readLine()) != null) {
                issue = findOne(info.substring(0, 40));
                if (issue.getStatus() == status || status == null) {
                    result.add(issue);
                }
            }
        } catch (IOException e) {
            throw new ExecutionFailedException();
        }
        return result;
    }

    @Override
    public void postComment(Comment comment, String hash) {
        Issue oldIssue = findOne(hash);
        Issue newIssue = new Issue(
                oldIssue.getHash(),
                oldIssue.getSummary(),
                oldIssue.getDescription(),
                oldIssue.getStatus()
        );
        newIssue.addComment(comment);
        issuesMap.replace(hash, oldIssue, newIssue);
    }

    private static class ExecutionFailedException extends RuntimeException {

    }
}
