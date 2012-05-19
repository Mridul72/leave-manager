CREATE DATABASE leaveManagerDB;
grant all privileges on leaveManagerDB.* to alinehuf@localhost identified by "secret";

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  user_id         INT(5) NOT NULL AUTO_INCREMENT,
  name            CHAR (100) NOT NULL,
  surname         CHAR(100) NOT NULL,
  gender          CHAR(1) NOT NULL,
  email           CHAR(100) NOT NULL,
  login           CHAR(10) NOT NULL,
  password        CHAR(100) NOT NULL,
  taking_office   DATETIME NOT NULL,
  termination     DATETIME NOT NULL,
  replacement_sid INT(5),
  group_sid       INT(5) NOT NULL,
  team_sid        INT(5) NOT NULL,
  PRIMARY KEY  (user_id),
  CONSTRAINT fk_replacement FOREIGN KEY (replacement_sid)
  REFERENCES Replacement(replacement_id),
  CONSTRAINT fk_group FOREIGN KEY (group_sid)
  REFERENCES Replacement(group_id),
  CONSTRAINT fk_team FOREIGN KEY (team_sid)
  REFERENCES Replacement(team_id)
);

INSERT INTO users VALUES (null,'admin','admin','m','@','admin',
  '21232f297a57a5a743894a0e4a801fc3','1000-01-01 00:00:00',
  '1000-01-01 00:00:00',null,1,1);
