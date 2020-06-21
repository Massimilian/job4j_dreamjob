package ru.job4j.model;

import java.util.Collection;

public interface Store {
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void savePost(Post post);

    Post findPostById(int id);

    void saveCandidate(Candidate candidate);

    Candidate findCandidateById(int id);
}
