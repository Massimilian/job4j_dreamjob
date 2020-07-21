package ru.job4j.model;

import java.sql.*;

public class Example {
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
}
