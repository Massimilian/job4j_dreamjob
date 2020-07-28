package ru.job4j.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class TestPsqlStore implements Store {
    String url = "jdbc:postgresql://localhost:5432/postgres";
    String username = "postgres";
    String password = "qetupoi";
    Connection connection = null;

    public TestPsqlStore() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.addPosts();
        this.addCandidates();
    }

    private static final class WorkPlace {
        private static final Store INST = new TestPsqlStore();
    }

    public static Store instOf() {
        return TestPsqlStore.WorkPlace.INST;
    }

    private void addPosts() {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("CREATE TABLE IF NOT EXISTS posts(id SERIAL PRIMARY KEY, name varchar);");
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void addCandidates() {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("CREATE TABLE IF NOT EXISTS candidates(id SERIAL PRIMARY KEY, name varchar);");
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @Override
    public Collection<Post> findAllPosts() {
        ArrayList<Post> posts = new ArrayList<>();
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM posts");
            while (rs.next()) {
                posts.add(new Post(rs.getInt("id"), rs.getString("name")));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return posts;
    }


    @Override
    public Collection<Candidate> findAllCandidates() {
        ArrayList<Candidate> cands = new ArrayList<>();
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM candidates");
            while (rs.next()) {
                cands.add(new Candidate(rs.getInt("id"), rs.getString("name")));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return cands;
    }

    @Override
    public void createPost(Post post) {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            PreparedStatement st = connection.prepareStatement("INSERT INTO posts (name) values (?)");
            st.setString(1, post.getName());
            st.execute();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @Override
    public void createCandidate(Candidate cand) {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            PreparedStatement st = connection.prepareStatement("INSERT INTO candidates (name) values (?)");
            st.setString(1, cand.getName());
            st.execute();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }


    @Override
    public void update(Post post) {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            PreparedStatement st = connection.prepareStatement("UPDATE posts SET name = ? WHERE id = ?");
            st.setString(1, post.getName());
            st.setInt(2, post.getId());
            st.execute();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }


    @Override
    public void update(Candidate cand) {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            PreparedStatement st = connection.prepareStatement("UPDATE candidates SET name = ? WHERE id = ?");
            st.setString(1, cand.getName());
            st.setInt(2, cand.getId());
            st.execute();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    @Override
    public void saveCandidate(Candidate candidate) {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            PreparedStatement st = connection.prepareStatement("INSERT INTO candidates (name) values (?)");
            st.setString(1, candidate.getName());
            st.execute();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }



    @Override
    public Post findPostById(int id) {
        Post post = null;
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT * FROM posts WHERE id = %d;", id));
            while (rs.next()) {
                post = new Post(rs.getInt("id"), rs.getString("name"));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return post;
    }

    @Override
    public Candidate findCandidateById(int id) {
        Candidate cand = null;
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(String.format("SELECT * FROM candidates WHERE id = %d;", id));
            while (rs.next()) {
                cand = new Candidate(rs.getInt("id"), rs.getString("name"));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return cand;
    }
}
