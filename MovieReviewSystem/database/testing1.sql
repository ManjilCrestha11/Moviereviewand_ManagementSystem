CREATE DATABASE MovieSystem;
USE MovieSystem;

CREATE TABLE Movies (
    movie_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    genre VARCHAR(100),
    release_year INT
);

CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    role VARCHAR(20) DEFAULT 'User' 
);

CREATE TABLE reviews (
    review_id INT PRIMARY KEY AUTO_INCREMENT,
    movie_id INT,
    user_name VARCHAR(100),
    rating INT,
    comment TEXT,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id)
);
INSERT INTO users (username, password, role) VALUES 
('admin', 'admin123', 'Admin'),
('user', 'user123', 'User');

INSERT INTO Movies (title, genre, release_year) VALUES 
('Inception', 'Sci-Fi', 2010),
('The Dark Knight', 'Action', 2008),
('Interstellar', 'Sci-Fi', 2014),
('The Godfather', 'Crime', 1972),
('Pulp Fiction', 'Crime', 1994);

INSERT INTO reviews (movie_id, user_name, rating, comment) VALUES 
(1, 'Hari', 5, 'Masterpiece of cinema!'),
(2, 'Shyam', 5, 'Best superhero movie ever.'),
(3, 'Ram', 4, 'Visuals were amazing.');

ALTER TABLE reviews 
ADD CONSTRAINT fk_movie 
FOREIGN KEY (movie_id) 
REFERENCES movies(movie_id) 
ON DELETE CASCADE;

UPDATE Movies SET title = 'Inception (Extended Edition)' WHERE movie_id = 1;

SELECT m.title AS Movie, u.username AS Reviewer, r.rating, r.comment 
FROM reviews r
JOIN Movies m ON r.movie_id = m.movie_id
JOIN users u ON r.user_id = u.user_id;

SELECT * FROM users;
SELECT * FROM Movies;
SELECT * FROM users;
SELECT * FROM reviews;
SELECT username, role FROM users;


