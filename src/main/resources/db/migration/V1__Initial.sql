CREATE TABLE players (
  player_uuid                  CHAR(36) PRIMARY KEY,
  next_teleport                LONG   NOT NULL,
  cooldown_multiplier          DOUBLE NOT NULL,
  cost_multiplier              DOUBLE NOT NULL,
  cooldown_multiplier_modifier DOUBLE NOT NULL,
  cost_multiplier_modifier     DOUBLE NOT NULL
);

CREATE TABLE player_homes (
  player_uuid CHAR(36),
  home_name   CHAR(36),
  world       CHAR(36),
  x           INT,
  y           INT,
  z           INT,
  yaw         DOUBLE,
  pitch       DOUBLE,
  CONSTRAINT pk_player_home PRIMARY KEY (player_uuid, home_name)
)