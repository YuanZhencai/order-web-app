create table MENU_INFO
(
  ID                   bigint                         not null,
  FOOD_ID             varchar(20)                    null,
  FOOD_NAME           varchar(64)                    null,
  FOOD_TYPE           varchar(32)                    null,
  SOURCE              varchar(64)                    null,
  PRICE               NUMERIC (18,2)                 null,
  COUNT               NUMERIC (18,2)                 null,
  PICTURE             varchar(256)                    null,
  DEL_FLG             varchar(2)                    null,
  CREATE_TIME        timestamp                   null,
  CREATE_BY          varchar(32)                    null,
  UPDATE_TIME        timestamp                    null,
  UPDATE_BY          varchar(32)                    null,
  constraint PK_MENU_INFO primary key (ID)
);

create table RESTAURANT
(
  ID                   bigint                         not null,
  RESTAURANT_ID       varchar(20)                    null,
  RESTAURANT_NAME     varchar(64)                    null,
  DEL_FLG             varchar(2)                     null,
  CREATE_TIME        timestamp                      null,
  CREATE_BY          varchar(32)                    null,
  UPDATE_TIME        timestamp                      null,
  UPDATE_BY          varchar(32)                    null,
  constraint PK_RESTAURANT primary key (ID)
);


create table USER_INFO
(
  ID                   bigint                         not null,
  USER_ID             varchar(20)                    null,
  USER_NAME           varchar(32)                    null,
  EMAIL               varchar(64)                    null,
  PASSWORD            varchar(32)                   null,
  DEL_FLG             varchar(2)                    null,
  CREATE_TIME        timestamp                     null,
  CREATE_BY          varchar(32)                    null,
  UPDATE_TIME        timestamp                     null,
  UPDATE_BY          varchar(32)                    null,
  constraint PK_USER_INFO primary key (ID)
);


create table ORDER_INFO
(
  ID                   bigint                         not null,
  USER_ID             varchar(20)                    null,
  USER_NAME           varchar(20)                    null,
  FOOD_ID             varchar(20)                    null,
  FOOD_NAME           varchar(64)                    null,
  SOURCE              varchar(64)                    null,
  PRICE               NUMERIC (18,2)                 null,
  DEL_FLG             varchar(2)                    null,
  CREATE_TIME        timestamp                   null,
  CREATE_BY          varchar(32)                    null,
  UPDATE_TIME        timestamp                    null,
  UPDATE_BY          varchar(32)                    null,
  constraint PK_ORDER_INFO primary key (ID)
);

create table RULE
(
  ID                   bigint                         not null,
  RULE_TYPE           varchar(20)                    null,
  RULE_KEY            varchar(20)                    null,
  RULE_VALUE          varchar(20)                    null,
  DEL_FLG             varchar(2)                    null,
  CREATE_TIME        timestamp                   null,
  CREATE_BY          varchar(32)                    null,
  UPDATE_TIME        timestamp                    null,
  UPDATE_BY          varchar(32)                    null,
  constraint PK_RULE primary key (ID)
);