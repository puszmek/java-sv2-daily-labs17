package day01;

import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/movies_actors?useUnicode=true");
            dataSource.setUserName("root");
//            dataSource.setPassword("root");
        } catch (SQLException sqle) {
            throw new IllegalStateException("Cannot reach DataBase!", sqle);
        }

        ActorsRepository actorsRepository = new ActorsRepository(dataSource);
        actorsRepository.saveActor("Jack Doe");

//        try (Connection connection = dataSource.getConnection();
//             Statement stmt = connection.createStatement()) {
//            stmt.executeUpdate("insert into actors (actor_name) values ('John Doe')");
//        } catch (SQLException sqle) {
//            throw new IllegalStateException("Cannot connect!", sqle);
//        }

        System.out.println(actorsRepository.findActorsWithPrefix("J"));

//        try (Connection conn = dataSource.getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery("SELECT actor_name FROM actors WHERE actor_name LIKE 'J%'")) {
//            List<String> result = new ArrayList<>();
//            while (rs.next()) {
//                String actorName = rs.getString("actor_name");
//                result.add(actorName);
//            }
//            System.out.println(result);
//        } catch (SQLException sqle) {
//            throw new IllegalStateException("Cannot select actor_name", sqle);
//        }
    }
}
