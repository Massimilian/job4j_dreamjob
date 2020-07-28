package ru.job4j;

import ru.job4j.model.Candidate;
import ru.job4j.model.Post;
import ru.job4j.model.PsqlStore;

public class PsqlMain {
    public static void main(String[] args) {
        PsqlStore store = (PsqlStore) PsqlStore.instOf();
        store.createPost(new Post( "Java Job new"));
        System.out.println("Posts:");
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + ". " + post.getName());
        }
        Post post = store.findPostById(1);
        System.out.println("Special post:");
        System.out.println(post.getId() + " " + post.getName());
        store.saveCandidate(new Candidate("Somebody Else"));
        store.saveCandidate(new Candidate("Vasily Maslov"));
        System.out.println("Candidates:");
        for (Candidate candidate : store.findAllCandidates()) {
            System.out.println(candidate.getId() + ". " + candidate.getName());
        }
        Candidate candidate = store.findCandidateById(2);
        System.out.println("Special candidate:");
        System.out.println(candidate.getId() + ". " + candidate.getName());
    }
}
