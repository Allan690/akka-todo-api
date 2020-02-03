# TODO API
A simple todo api that illustrates how to build an API using akka http and scala

### Getting started
Ensure that your docker daemon is running then:
 - Run `sbt docker:publishLocal` to publish the app
 - `docker run -it -p 9000:9000 akkahttp-quickstart` to start the app
 - You can then consume the endpoints(GET `/todos`, POST `/todos/` and PUT `/todos/:id` using a REST client)