package edu.xored.tracker;

import org.springframework.data.repository.CrudRepository;

public interface IssueRepository extends CrudRepository<Issue,String> {
    <S extends Issue> S replace(String hash, S issue);

    public Iterable<Issue> findAll(Issue.Status status);

    void postComment(Comment comment, String hash);
}