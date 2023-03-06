INSERT INTO users (username,password)
VALUES ('admin','$2a$04$kP/9U4LAtfM.hXKSls/pZ.hpLs0WcqDqBlNIZuWcNsR95gIZagG4y'),
       ('alena','$2a$04$sQF30Kj2ThaKNKnOi0kKY.01PVoh9jGw.oP4EfikzGTed0iOX0V72');


INSERT INTO user_role (user_id,roles)
VALUES (1,'ROLE_ALLOW_EDIT');