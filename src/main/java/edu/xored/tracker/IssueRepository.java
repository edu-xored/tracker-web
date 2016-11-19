package edu.xored.tracker;

import org.springframework.data.repository.CrudRepository;

public interface IssueRepository extends CrudRepository<Issue, Long> {
    <S extends Issue> S replace(Long hash, S issue);

    Iterable<Issue> findAll(Issue.Status status);

    void postComment(Comment comment, long hash);
}