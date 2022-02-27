package day05;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MoviesRepository {

    private DataSource dataSource;

    public MoviesRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Long saveMovie(String title, LocalDate releaseDate) {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into movies (title, release_date) values (?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, title);
            stmt.setDate(2, Date.valueOf(releaseDate));
            stmt.executeUpdate();

            try(ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
                throw new IllegalStateException("Insert failed to movies!");
            }

        } catch (SQLException sqle) {
            throw new IllegalStateException("Cannot connect", sqle);
        }
    }

    public List<Movie> findAllMovies() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM movies");
             ResultSet rs = stmt.executeQuery()) {
            return processResultSet(rs);
        } catch (SQLException sqle) {
            throw new IllegalStateException("Cannot query!", sqle);
        }
    }

    public Optional<Movie> findMovieByTitle(String title) {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM movies WHERE title = ?")) {
            stmt.setString(1, title);
            return processSelectStatementMovie(stmt);
        } catch (SQLException sqle) {
            throw new IllegalStateException("Cannot connect to select by title");
        }
    }

    private Optional<Movie> processSelectStatementMovie(PreparedStatement stmt) throws SQLException {
        try(ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return Optional.of(new Movie(rs.getLong("id"), rs.getString("title"), rs.getDate("release_date").toLocalDate()));
            }
            return Optional.empty();
        }
    }

    private List<Movie> processResultSet(ResultSet rs) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        while (rs.next()) {
            long id = rs.getLong("id");
            String title = rs.getString("title");
            LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
            movies.add(new Movie(id, title, releaseDate));
        }
        return movies;
    }
}
