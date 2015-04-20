# structure of the database #

## Conceptual Data Model "CDM" ##

![http://leave-manager.googlecode.com/files/merise.jpg](http://leave-manager.googlecode.com/files/merise.jpg)

## Logical Data Model "LDM" ##

```
users (#user_id, user_name, surname, gender, email, login, password,
       taking_office, termination, group_sid, team_sid)

leaves (#leave_id, leave_name, leave_type)
leaves_nrml (#leave_sid) => inutile...
leaves_cstr (leave_beg, leave_end, worked, #leave_sid)
leaves_spcl (concern_gender, need_proof, #leave_sid)
leaves_svng (granted, #leave_sid)

teams ( #team_id, team_name )
groups ( #group_id, group_name )
rights ( #right_id, right_name )
worktimes ( #group_sid, #weekday, amPmOrBoth, work_duration )
requests ( #request_id, request_cre, comment, leave_sid, user_sid_ask, user_sid_enjoy )
dates ( #date_id, date_edit, user_sid_edit, date_beg, date_end, request_sid )
proofs (#proof_id, url_doc, request_sid)
replacements ( #replacement_id, date_from, date_to, user_sid_leave, user_sid_replace )

users_rights ( #user_sid, #right_sid )
manage ( #user_sid, #team_sid, validation )
decide ( status, justification, #user_sid, #request_sid )
dispose ( duration, #user_sid, #leave_sid )
earn ( worked_duration, earned_duration, #group_sid, #leave_sid )
deserve ( max_quantity, max_consecutive, #group_sid, #leave_sid )
```

## Physical Data Model "PDM" ##

```

// CREATE DATABASE leaveManagerDB
//  DEFAULT CHARACTER SET utf8
//  DEFAULT COLLATE utf8_general_ci; // doesn't work !!!

DROP DATABASE IF EXISTS leaveManagerDB;

CREATE DATABASE leaveManagerDB CHARACTER SET utf8 COLLATE utf8_general_ci;

grant all privileges on leaveManagerDB.* to [user]@localhost identified by "[password]";

USE leaveManagerDB;

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  user_id         INT(5) NOT NULL AUTO_INCREMENT,
  user_name       VARCHAR(40) NOT NULL,
  surname         VARCHAR(40) NOT NULL,
  gender          CHAR(1) NOT NULL,
  email           VARCHAR(40) NOT NULL,
  login           VARCHAR(10) NOT NULL,
  password        VARCHAR(40) NOT NULL,
  taking_office   DATETIME NOT NULL,
  termination     DATETIME,
  group_sid       INT(5) NOT NULL,
  team_sid        INT(5) NOT NULL,
  PRIMARY KEY (user_id),
  CONSTRAINT fk_group FOREIGN KEY (group_sid)
  REFERENCES groups(group_id),
  CONSTRAINT fk_team FOREIGN KEY (team_sid)
  REFERENCES teams(team_id)
);

DROP TABLE IF EXISTS leaves;
CREATE TABLE leaves (
  leave_id        INT(5) NOT NULL AUTO_INCREMENT,
  leave_type      CHAR(4) NOT NULL,
  leave_name      VARCHAR(40) NOT NULL,
  PRIMARY KEY (leave_id)
);

DROP TABLE IF EXISTS leaves_cstr;
CREATE TABLE leaves_cstr (
  leave_beg       DATETIME NOT NULL,
  leave_end       DATETIME NOT NULL,
  worked          CHAR(1) NOT NULL,
  leave_sid       INT(5) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (leave_sid),
  CONSTRAINT fk_leave FOREIGN KEY (leave_sid)
  REFERENCES leaves(leave_id)
);

DROP TABLE IF EXISTS leaves_spcl;
CREATE TABLE leaves_spcl (
  concern_gender  CHAR(1) NOT NULL,
  need_proof      CHAR(1) NOT NULL,
  leave_sid       INT(5) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (leave_sid),
  CONSTRAINT fk_leave FOREIGN KEY (leave_sid)
  REFERENCES leaves(leave_id)
);

DROP TABLE IF EXISTS leaves_svng;
CREATE TABLE leaves_svng (
  granted         CHAR(1) NOT NULL,
  leave_sid       INT(5) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (leave_sid),
  CONSTRAINT fk_leave FOREIGN KEY (leave_sid)
  REFERENCES leaves(leave_id)
);


DROP TABLE IF EXISTS teams;
CREATE TABLE teams (
  team_id         INT(5) NOT NULL AUTO_INCREMENT,
  team_name       VARCHAR(40) NOT NULL,
  PRIMARY KEY (team_id)
);

DROP TABLE IF EXISTS groups;
CREATE TABLE groups (
  group_id         INT(5) NOT NULL  AUTO_INCREMENT,
  group_name       VARCHAR(40) NOT NULL,
  PRIMARY KEY (group_id)
);

DROP TABLE IF EXISTS rights;
CREATE TABLE rights (
  right_id        INT(5) NOT NULL AUTO_INCREMENT,
  right_name      VARCHAR(20) NOT NULL,
  PRIMARY KEY (right_id)
);

DROP TABLE IF EXISTS users_rights;
CREATE TABLE users_rights (
  user_sid        INT(5) NOT NULL,
  right_sid       INT(5) NOT NULL,
  PRIMARY KEY (user_sid,right_sid),
  CONSTRAINT fk_user FOREIGN KEY (user_sid)
  REFERENCES users(user_id),
  CONSTRAINT fk_right FOREIGN KEY (right_sid)
  REFERENCES rights(right_id)
);

DROP TABLE IF EXISTS worktimes;
CREATE TABLE worktimes (
  group_sid       INT(5) NOT NULL,
  weekday         CHAR(2) NOT NULL,
  amPmOrBoth      CHAR(1) NOT NULL,
  work_duration   FLOAT NOT NULL,
  PRIMARY KEY (group_sid, weekday),
  CONSTRAINT fk_group FOREIGN KEY (group_sid)
  REFERENCES groups(group_id)
);

DROP TABLE IF EXISTS requests;
CREATE TABLE requests (
  request_id      INT(5) NOT NULL AUTO_INCREMENT,
  request_cre     DATETIME NOT NULL,
  comment         VARCHAR(100),
  leave_sid       INT(5) NOT NULL,
  user_sid_ask    INT(5) NOT NULL,
  user_sid_enjoy  INT(5) NOT NULL,
  PRIMARY KEY (request_id),
  CONSTRAINT fk_leave FOREIGN KEY (leave_sid)
  REFERENCES leaves(leave_id),
  CONSTRAINT fk_user_ask FOREIGN KEY (user_sid_ask)
  REFERENCES users(user_id),
  CONSTRAINT fk_user_enjoy FOREIGN KEY (user_sid_enjoy)
  REFERENCES users(user_id)
);

DROP TABLE IF EXISTS dates;
CREATE TABLE dates (
  date_id         INT(5) NOT NULL AUTO_INCREMENT,
  date_edit       DATETIME NOT NULL,
  user_sid_edit   INT(5) NOT NULL,
  comment         VARCHAR(100),
  date_beg        DATETIME NOT NULL,
  date_end        DATETIME NOT NULL,
  request_sid     INT(5) NOT NULL,
  PRIMARY KEY (date_id),
  CONSTRAINT fk_user_edit FOREIGN KEY (user_sid_edit)
  REFERENCES users(user_id),
  CONSTRAINT fk_request FOREIGN KEY (request_sid)
  REFERENCES requests(request_id)
);

DROP TABLE IF EXISTS proofs;
CREATE TABLE proofs (
  proof_id        INT(5) NOT NULL AUTO_INCREMENT,
  url_doc         VARCHAR(100),
  request_sid     INT(5) NOT NULL,
  PRIMARY KEY (proof_id),
  CONSTRAINT fk_request FOREIGN KEY (request_sid)
  REFERENCES requests(request_id)
);

DROP TABLE IF EXISTS replacements;
CREATE TABLE replacements (
  replacement_id  INT(5) NOT NULL AUTO_INCREMENT,
  date_from       DATETIME NOT NULL,
  date_to         DATETIME NOT NULL,
  user_sid_leave  INT(5) NOT NULL,
  user_sid_replace INT(5) NOT NULL,
  PRIMARY KEY (replacement_id),
  CONSTRAINT fk_user_leave FOREIGN KEY (user_sid_leave)
  REFERENCES users(user_id),
  CONSTRAINT fk_user_replace FOREIGN KEY (user_sid_replace)
  REFERENCES users(user_id)
);

DROP TABLE IF EXISTS manage;
CREATE TABLE manage (
  user_sid        INT(5) NOT NULL,
  team_sid        INT(5) NOT NULL,
  validation      CHAR(1) NOT NULL,
  PRIMARY KEY (user_sid,team_sid),
  CONSTRAINT fk_user FOREIGN KEY (user_sid)
  REFERENCES users(user_id),
  CONSTRAINT fk_team FOREIGN KEY (team_sid)
  REFERENCES teams(team_id)
);

DROP TABLE IF EXISTS decide;
CREATE TABLE decide (
  status          CHAR(1),
  justification   VARCHAR(100),
  user_sid        INT(5) NOT NULL,
  request_sid     INT(5) NOT NULL,
  PRIMARY KEY (user_sid,request_sid),
  CONSTRAINT fk_user FOREIGN KEY (user_sid)
  REFERENCES users(user_id),
  CONSTRAINT fk_request FOREIGN KEY (request_sid)
  REFERENCES requests(request_id)
);

DROP TABLE IF EXISTS dispose;
CREATE TABLE dispose (
  duration        FLOAT,
  user_sid        INT(5) NOT NULL,
  leave_sid       INT(5) NOT NULL,
  PRIMARY KEY (user_sid,leave_sid),
  CONSTRAINT fk_user FOREIGN KEY (user_sid)
  REFERENCES users(user_id),
  CONSTRAINT fk_request FOREIGN KEY (leave_sid)
  REFERENCES leaves(leave_id)
);

DROP TABLE IF EXISTS earn;
CREATE TABLE earn (
  worked_duration FLOAT,
  earned_duration FLOAT,
  user_sid        INT(5) NOT NULL,
  leave_sid       INT(5) NOT NULL,
  PRIMARY KEY (user_sid,leave_sid),
  CONSTRAINT fk_user FOREIGN KEY (user_sid)
  REFERENCES users(user_id),
  CONSTRAINT fk_request FOREIGN KEY (leave_sid)
  REFERENCES leaves(leave_id)
);

DROP TABLE IF EXISTS deserve;
CREATE TABLE deserve (
  max_quantity    FLOAT,
  max_consecutive FLOAT,
  group_sid       INT(5) NOT NULL,
  leave_sid       INT(5) NOT NULL,
  PRIMARY KEY (group_sid,leave_sid),
  CONSTRAINT fk_group FOREIGN KEY (group_sid)
  REFERENCES groups(group_id),
  CONSTRAINT fk_request FOREIGN KEY (leave_sid)
  REFERENCES leaves(leave_id)
);
```