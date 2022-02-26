package day02;

import day01.ActorsRepository;
import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.time.LocalDate;

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

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        ActorsRepository actorsRepository = new ActorsRepository(dataSource);

        MoviesRepository moviesRepository = new MoviesRepository(dataSource);
        moviesRepository.saveMovie("Titanic", LocalDate.of(1997,12,11));
        moviesRepository.saveMovie("Lord Of The Rings", LocalDate.of(200,12,23));

        System.out.println(moviesRepository.findAllMovies());
    }
}
