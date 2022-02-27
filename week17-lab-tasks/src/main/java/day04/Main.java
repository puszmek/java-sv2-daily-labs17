package day04;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

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
//        flyway.clean();
        flyway.migrate();

        ActorsRepository actorsRepository = new ActorsRepository(dataSource);

        MoviesRepository moviesRepository = new MoviesRepository(dataSource);
//        moviesRepository.saveMovie("Titanic", LocalDate.of(1997,12,11));
//        moviesRepository.saveMovie("Lord Of The Rings", LocalDate.of(2000,12,23));
//        System.out.println(moviesRepository.findAllMovies());

//        actorsRepository.saveActor("Jane Doe");
//        System.out.println(actorsRepository.findActorByName("Jane Doe"));

        ActorsMoviesRepository actorsMoviesRepository = new ActorsMoviesRepository(dataSource);

        ActorsMoviesService service = new ActorsMoviesService(actorsRepository, moviesRepository, actorsMoviesRepository);
        service.insertMovieWithActors("Titanic", LocalDate.of(1997, 11, 13), List.of("Leonardo DiCaprio", "Kate Winslet"));
        service.insertMovieWithActors("Great Gatsby", LocalDate.of(2013, 12, 11), List.of("Leonardo DiCaprio", "Carey Mulligan", "Tobey Maguire"));
    }
}
