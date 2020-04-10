CREATE TABLE SystemUser (
                            IdUser int  NOT NULL,
                            UserName varchar(100)  NOT NULL,
                            UserPassword varchar(100)  NOT NULL,
                            FirstName varchar(100),
                            LastName varchar(100),
                            CONSTRAINT SystemUser_pk PRIMARY KEY  (IdUser)
);

CREATE TABLE Resource (
                          IdResource int  NOT NULL,
                          ResourceName varchar(100)  NOT NULL,
                          Content varchar(2048),
                          CONSTRAINT Resource_pk PRIMARY KEY  (IdResource)
);

CREATE TABLE UsersResources (
                                IdUsersResources int  NOT NULL,
                                IdUser int  NOT NULL,
                                IdResource int  NOT NULL,
                                CONSTRAINT UsersResources_pk PRIMARY KEY  (IdUsersResources)
);

ALTER TABLE UsersResources ADD CONSTRAINT UsersResources_SystemUser
    FOREIGN KEY (IdUser)
        REFERENCES SystemUser (IdUser);

ALTER TABLE UsersResources ADD CONSTRAINT UsersResources_Resource
    FOREIGN KEY (IdResource)
        REFERENCES Resource (IdResource);

insert into SystemUser(iduser, username, userpassword, firstname, lastname)
values (1, 'user1', 'pass1', 'name1', 'surname1'),
       (2, 'user2', 'pass2', 'name2', 'surname2'),
       (3, 'user3', 'pass3', 'name3', 'surname3');

insert into Resource(idresource, resourcename, content)
values (1, 'res1', 'content1'),
       (2, 'res2', 'content2'),
       (3, 'res3', 'content3');

insert into UsersResources(idusersresources, iduser, idresource)
values (1, 1, 1),
       (2, 1, 2),
       (3, 2, 1),
       (4, 2, 3),
       (5, 3, 2),
       (6, 3, 3);

select * from SystemUser;
select * from Resource;

select username, resourcename
from UsersResources ur
         join SystemUser su on ur.IdUser = su.IdUser
         join Resource r on ur.IdResource = r.IdResource;

