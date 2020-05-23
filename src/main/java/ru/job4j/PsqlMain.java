package ru.job4j;

import ru.job4j.model.Post;
import ru.job4j.model.PsqlStore;
import ru.job4j.model.Store;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.savePost(new Post(0, "Java Job"));
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
    }
}
