CREATE TABLE if NOT EXISTS actors_movies (
  	id BIGINT AUTO_INCREMENT,
  	actor_id BIGINT,
  	movie_id BIGINT,
  	CONSTRAINT pk_actors_movies PRIMARY KEY (id)
  );