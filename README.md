# The Versus API

<img align="left" src="../gh-pages/images/Sound-Wave-Headphones.svg?raw=true" width="100px">

The versus API provides a collaborative community for music lovers. Users can
share their musical interests with the world and find other users who might
share those interests.

Users can enter musical artists into the database and provide a list of that
artist's albums. Users can list which albums they own, and anyone can view the
full list of albums owned by others.

<br clear="left"/>

## Documentation
The API uses the REST interface style. Complete documentation of its operations
and objects is available
[here](https://220620-java.github.io/p1-web-teamversus/).

## Technical Details

Although an API user need not be concerned with the inner workings of the
application, they are included here for reference.

#### The Flow of Information in the API

The API is constructed with a familiar separation of operations into a front
facing layer, a service layer, and a data layer.

![The flow diagram for the application.](../media/AppDiagram.svg?raw=true)

Since the API sends and receives requests over HTTP, the front-facing part of
the application consists of several servlets. Requests are
directed to the appropriate servlet by a tomcat server.

The servlets then extract the information from JSON into Java objects. Next,
these objects are passed to the service layer. The service layer examines these
objects to determine what information is being requested and makes appropriate
calls to the data layer.

The data layer then responds to these calls by making database queries.
Once the database has replied, the information follows the reverse path out.
The data layer passes the information to the service layer who, in turn,
passes it back to the servlet. The servlet takes the information and decides
the appropriate HTTP response c

#### The Versus ORM

In the workflow described above, the database operations are greatly simplified
in our application by the
[the Versus ORM](https://github.com/220620-java/p1-orm-teamversus). Instead
of having to write complex SQL statements to store or retrieve information
for users, we can simply call Java methods. The ORM then does the necessary
database operations for us.

#### Database

The Versus ORM internally calls into a PostgreSQL database using Amazon RDS to
store and retrieve data requested by users. The following diagram shows the
table structure of this database.

![ER diagram for the database tables](../media/versus-app2.png?raw=true)

## Acknowledgments

- The API documentation was created with the
  [Spectacle](https://github.com/sourcey/spectacle) documentation generator.
- The clipart for the documentation was provided by
  [openclipart.org](https://openclipart.org/detail/266646/sound-wave-headphones).
