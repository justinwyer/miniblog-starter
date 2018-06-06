CREATE TABLE users (
  id       INT AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
);

CREATE INDEX username
  ON users (username);

CREATE TABLE posts (
  id   INT AUTO_INCREMENT,
  body TEXT NOT NULL,
  user_id INT,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX user_id
  ON posts (user_id);

