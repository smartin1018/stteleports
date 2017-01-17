CREATE TABLE players (
  player_uuid                  CHAR(36) PRIMARY KEY,
  max_homes                    INT    NOT NULL,
  cooldown                     LONG   NOT NULL,
  cooldown_multiplier          DOUBLE NOT NULL,
  cost_multiplier              DOUBLE NOT NULL,
  cooldown_multiplier_modifier DOUBLE NOT NULL,
  cost_multiplier_modifier     DOUBLE NOT NULL
);

CREATE TABLE player_homes (
  player_uuid CHAR(36),
  name        VARCHAR(16),
  world       CHAR(36),
  x           INT,
  y           INT,
  z           INT,
  yaw         FLOAT,
  pitch       FLOAT,
  CONSTRAINT pk_player_home PRIMARY KEY (player_uuid, name)
)