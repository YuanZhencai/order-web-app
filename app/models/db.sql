create table MENU_INFO
(
   ID                   bigint                         not null,
   FOOD_ID             varchar(20)                    null,
   FOOD_NAME           varchar(64)                    null,
   SOURCE              varchar(64)                    null,
   PRICE               NUMERIC (18,2)                 null,
   COUNT               NUMERIC (18,2)                 null,
   PICTURE             varchar(256)                    null,
   DEL_FLG             varchar(2)                    null,
   CREATE_TIME        timestamp                   null,
   CREATE_BY          varchar(32)                    null,
   UPDATE_TIME        timestamp                    null,
   UPDATE_BY          varchar(32)                    null
   constraint PK_MENU_INFO primary key (ID)
);

create table MENU_INFO
(
   ID                   bigint                         not null,
   USER_ID             varchar(20)                    null,
   USER_NAME           varchar(20)                    null,
   EMAIL               varchar(32)                    null,
   PASSWORD            varchar(20)              null,
   DEL_FLG             varchar(2)                    null,
   CREATE_TIME        timestamp                   null,
   CREATE_BY          varchar(32)                    null,
   UPDATE_TIME        timestamp                    null,
   UPDATE_BY          varchar(32)                    null
   constraint PK_MENU_INFO primary key (ID)
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
   UPDATE_BY          varchar(32)                    null
   constraint PK_ORDER_INFO primary key (ID)
);