package day05;

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
        ActorsMoviesRepository actorsMoviesRepository = new ActorsMoviesRepository(dataSource);
        RatingsRepository ratingsRepository = new RatingsRepository(dataSource);

        ActorsMoviesService actorsMoviesService = new ActorsMoviesService(actorsRepository, moviesRepository, actorsMoviesRepository);
        MoviesRatingsService moviesRatingsService = new MoviesRatingsService(moviesRepository, ratingsRepository);

//        actorsMoviesService.insertMovieWithActors("Titanic", LocalDate.of(1997, 11, 13), List.of("Leonardo DiCaprio", "Kate Winslet"));
//        actorsMoviesService.insertMovieWithActors("Great Gatsby", LocalDate.of(2013, 12, 11), List.of("Leonardo DiCaprio", "Carey Mulligan", "Tobey Maguire"));

//        moviesRatingsService.addRatings("Titanic", 5, 3, 2);
//        moviesRatingsService.addRatings("Great Gatsby", 1, 3, 6, 5);
        moviesRatingsService.addRatings("Great Gatsby", 1, 3, 2, 5);
    }
}
