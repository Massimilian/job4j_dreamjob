package ru.job4j.model;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store {


    // Давайте создадим метод main.
    public static void main(String[] args) {
        // Скопируем куски кода, который нам предлагают.
        // Написали URL, по которому хотим коннектиться - фактически, адрес, по которому находится наша БД
        String url = "jdbc:postgresql://localhost:5432/postgres";
        // теперь мы должны использовать username
        String username = "postgres";
        // вводим пароль базы данных
        String password = "qetupoi";
        // создаём статическую фабрику, которая должна получить запрос
        Connection connection = null;
        // всё это иницилизируем в try-catch
        try {
            // у нас есть Драйвер-менеджер, которому мы создаём объект (connection), используя статическую фабрику
            // передаём все необходимые данные - URL, имя и пароль
            connection = DriverManager.getConnection(url, username, password);
            // Вариант №1 - стандартный. получаем запрос из объекта connection
            Statement st = connection.createStatement();
            // Вариант №2 - запросный. производим новый Statement для того, чтобы сделать запрос
            PreparedStatement st2 = connection.prepareStatement("Select * from family as f where f.years_old BETWEEN ? AND ?");
            // теперь заменяем первый знак вопроса на значение (порядковый номер в начале)
            st2.setInt(1, 4);
            // заменяем второй знак вопроса на значение (порядковый номер в начале)
            st2.setInt(2, 8);
            // Вариант №3
            PreparedStatement st3 = connection.prepareStatement("INSERT into family (name, second_name, surname, years_old, birthday, sex) values (?, ?, ?, ?, ?, ?)");
            st3.setString(1, "Unknown");
            st3.setString(2, "Vasilievna");
            st3.setString(3, "Maslova");
            st3.setInt(4, 0);
            st3.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            st3.setString(6, "m");
            // посылаем запрос на получение итератора ResultSet (для варианта №1)
            ResultSet rs = st.executeQuery("SELECT * FROM family");
            // внимание! здесь уже не надо передавать запрос - он уже создан! (для варианта №2)
            // ResultSet rs = st2.executeQuery();
            // внимание! здесь уже не надо передавать запрос - он уже создан! (для варианта №3)
            //ResultSet rs = st3.executeQuery();
            while (rs.next()) {
                // используем указание по имени колонки
                System.out.println(String.format("%s %s - %d", rs.getString("name"), rs.getString("second_name"), rs.getInt("years_old")));
            }
            // закрываем оба ресурса, которые ранее были открыты. Вообще закрывать надо в блоке finally
            rs.close();
            st.close();
        } catch (SQLException e) {
            // обязательно прописываем метод, который помогает понять в случае ошибки, что же произошло в коде
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                // если connection не равен нолю, его надо закрыть
                // ещё раз оборачиваем в try-catch
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public static PsqlStore store;

    String url = "jdbc:postgresql://localhost:5432/postgres";
    String username = "postgres";
    String password = "qetupoi";
    Connection connection = null;

    public static PsqlStore instOf() {
        if (store == null) {
            store = new PsqlStore();
            createPostsTable();
            createCandidateTable();
        }
        return store;
    }

    private static void createPostsTable() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "qetupoi");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("CREATE TABLE IF NOT EXISTS posts (id SERIAL PRIMARY KEY, name VARCHAR)");
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }


    private static void createCandidateTable() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "qetupoi");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("CREATE TABLE IF NOT EXISTS candidates (id SERIAL PRIMARY KEY, name VARCHAR)");
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void finish() {
        deleteCandidatesTable();
        deletePostsTable();
        store = null;
        System.out.println("PsqlStore finished his work.");
    }

    private static void deletePostsTable() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "qetupoi");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("DROP TABLE IF EXISTS posts");
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private static void deleteCandidatesTable() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "qetupoi");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("DROP TABLE IF EXISTS candidates");
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
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
            connection = DriverManager.getConnection(url, username, password);
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM posts");
            while (rs.next()) {
                Post post = new Post(rs.getInt("id"), rs.getString("name"));
                posts.add(post);
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
        ArrayList<Candidate> candidates = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(url, username, password);
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM candidates");
            while (rs.next()) {
                Candidate candidate = new Candidate(rs.getInt("id"), rs.getString("name"));
                candidates.add(candidate);
            }
            rs.close();
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
        return candidates;
    }

    @Override
    public void savePost(Post post) {
        try {
            connection = DriverManager.getConnection(url, username, password);
            PreparedStatement st = connection.prepareStatement("INSERT into posts (name) values (?)");
            st.setString(1, post.getName());
            ResultSet rs = st.executeQuery();
            rs.close();
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
    public void saveCandidate(Candidate candidate) {
        try {
            connection = DriverManager.getConnection(url, username, password);
            PreparedStatement st = connection.prepareStatement("INSERT into candidates (name) values (?)");
            st.setString(1, candidate.getName());
            ResultSet rs = st.executeQuery();
            rs.close();
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
    public Post findPostById(int id) {
        Post post = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            PreparedStatement st = connection.prepareStatement("SELECT *  FROM posts WHERE id = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                post = new Post(rs.getInt("id"), rs.getString("name"));
            }
            rs.close();
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
        return post;
    }

    @Override
    public Candidate findCandidateById(int id) {
        Candidate candidate = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            PreparedStatement st = connection.prepareStatement("SELECT *  FROM candidates WHERE id = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                candidate = new Candidate(rs.getInt("id"), rs.getString("name"));
            }
            rs.close();
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
        return candidate;
    }
}

