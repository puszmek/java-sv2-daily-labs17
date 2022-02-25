package day01;

import org.mariadb.jdbc.MariaDbDataSource;

//import java.sql.Connection;
import java.sql.SQLException;
//import java.sql.Statement;

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
    }
}
