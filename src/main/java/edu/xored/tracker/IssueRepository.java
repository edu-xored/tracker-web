package edu.xored.tracker;

import java.util.Collection;
import java.util.List;

public interface IssueRepository {

    Issue replace(String hash, Issue issue);

    List<Issue> findAll(Issue.Status status);

    void replaceAll();

    void postComment(Comment comment, String hash);

    Issue save(Issue issue);

    List<Issue> save(Collection<Issue> issues);

    Issue findOne(String issueId);

    boolean exists(String issueId);

    List<Issue> findAll();

    List<Issue> findAll(Collection<String> issuesId);

    long count();

    void delete(String issueId);

    void delete(Issue issue);

    void delete(Collection<? extends Issue> issues);

    void deleteAll();

    default List<Issue> list() {
        return findAll();
    }

    class IssueNotImplementedException extends RuntimeException {

    }

    class IssueNotFoundException extends RuntimeException {

        public IssueNotFoundException(String message) {
            super("Issue <" + message + "> not found");
        }
    }

}
