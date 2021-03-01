# Exercises

These exercises belong to the Lunatech Beginner Quarkus Course.


## Exercise 1: Hello World

In this exercise, we will be exploring Quarkus, and create a Hello World endpoint.

* This repository contains the start of the student app.
* Check out the 'start' tag if you haven't yet: `git checkout start`
* Import the application in your IDE
* Run the application
* Browse to http://localhost:8080/. What do you see?
* Take a look at the config in http://localhost:8080/q/dev/io.quarkus.quarkus-vertx-http/config
* Change the value of `quarkus.log.level` to `DEBUG`. What happens in the config file `src/main/resources/application.properties`?
* Create a new `hello` endpoint on the existing `GreetingResource`, with path parameter `subject`, and make it return `Hello` plus the subject. So that you can go to http://localhost:8080/hello/world to see `Hello World`


## Exercise 2: A Qute Hello World

In this exercise, we will use the Qute template engine to make our Hello World endpoint a tiny bit nicer.

* Create an HTML file that shows `Hello World!`. You can create one yourself, or copy the example from `materials/exercise-2/greet.html`. Save it as `src/main/resources/templates/greet.html`.
* Inject a `io.quarkus.qute.Template` field with name `greet` using a `javax.inject.Inject` annotation. Quarkus will look for a template with that name, and automatically generate the `Template` object for you!
* Make your hello endpoint return `greet.instance()`
* Check http://localhost:8080/hello/world to see if it works :)
* Now, change your template to use an expression `{subject}` instead of the hardcoded `World`, and change your resource to supply the subject parameter to the template.
* Check http://localhost:8080/hello/quarkus to see if it works!


## Exercise 3: Qute Products

In this exercise, we will start on the HIQUEA catalogue. We will make two pages, a page that lists all products, and a page that shows the details of a product.

* Create a class `Product`, with the following *public final* fields, and *a suitable constructor*:
    - Long id
    - String name
    - String description
    - BigDecimal price
* Copy the file `materials/exercise-3/Products.java` into `src/main/java/com/lunatech/training/quarkus/`.
* Create a new `ProductsResource`
* Create a `products` endpoint, that shows an HTML page with all products (use the products from the `all()` method on the `Products` class). You can use the HTML from the file `materials/exercise-3/catalogue.html`. Make sure to replace the following with Qute expressions:
    - Product names
    - Path parameters in URLs
    - Total number of products
* Create a `products/{productId}` endpoint, that lists the details of a product (use the `getById` method on the `Products` class).  You can use the HTML from the file `materials/exercise-3/details.html`. Make sure to replace the following with Qute expressions:
    - Product name (twice)
    - Product ID
    - Description
    - Price
* Extra: How would you deal with products that can’t be found?
* Extra: Write a test for both endpoints, testing that they give a `200` response, and contain some strings that should be there.


## Exercise 4: Even Quter Products

In this exercise, we will use some more Qute features to make some parts of our templates reusable. You will probably need the Qute Reference Documentation to figure out how to do these things.

* Create a file `layout.html`, that contains the `<html>`, `<head>` and `<body>` tags, and which can be used by other templates as a layout, using `{#include}`.
* Let the templates `catalogue.html` and `details.html` make use of this `layout.html`. Make sure that both the body content and the content of the `<title>` tag can be overridden by a template that includes the layout.
* Write an extension method `monetary`, such that `BigDecimal` values can be printed as money, with always two decimal places. So “40” should be printed as “€ 40.00” and “39.95” as “€ 39.95”. Use it in the `details` template where we display the price of a product.
    
  Tip: You may need to use the `RawString` feature to avoid escaping.
* Write a _user-defined tag_ `productListItem` that displays a single list item of the products list page. So essentially the `<li>` tag.


## Exercise 5: Products from the database, using Hibernate + Panache

In this exercise, we will start reading products from the database, rather than from the hardcoded `Products` class. We will use Hibernate + Panache as the ORM, with a Postgres database that we run on Docker using Docker Compose.

* In the root of the student app project, there is a `docker-compose.yml`, which contains a single service; a postgres database.
* Start it up using: `docker-compose up --detach`
* Next, we need to add some extensions. Add the following to your `pom.xml` in the dependencies section:

    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-hibernate-orm-panache</artifactId>
    </dependency>
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-jdbc-postgresql</artifactId>
    </dependency>

* Now we need to tell Quarkus where our database lives. Add the following to your `application.properties’:

        quarkus.datasource.db-kind=postgresql
        quarkus.datasource.username=postgres
        quarkus.datasource.password=postgres
        quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:8765/postgres
        quarkus.hibernate-orm.database.generation = drop-and-create
  
* Next, make your existing `Product` class extend from `PanacheEntity`, and add an `@Entity` annotation. This makes your `Product` class suitable for _Active Record_-style persistence, where the class you persist has static methods to interact with the storage.
* Add a default constructor, and make the fields non-final. Also, remove the `id` field from Product, since that field is already defined on `PanacheEntity`.
* Delete your old `Products` class, and update your `ProductsResource` to use the static methods on `Product` instead.
* Which methods did you pick?
* Copy the file `materials/exercise-5/import.sql` to `src/main/resources/import.sql`. Hibernate will automatically pick up this file, and execute its contents after creating the database. The file will populate your database with the HIQUEA products we love so much.
* Run your app and check if everything still works :)


## Exercise 6: CDI & ArC

In this exercise we won’t be doing much for HIQUEA, but we’ll practice a little bit with some ArC features!

* Create a class `SubjectBean`, with a public constructor that prints `SubjectBean constructed` and a method `String() subject()` that returns "everyone" (You can also copy this class from `/materials/exercise-6/SubjectBean.java`). Then, add the following to your `GreetingResource` class:

        @Inject SubjectBean subjectBean;

* Run the app. What happens?
* Add an `@Singleton` annotation to your SubjectBean class. What gets printed on the console, if you refresh http://localhost:8080/greet several times?
* Change the annotation on `MyBean` from `@Singleton` to `@RequestScoped`. If you refresh several times now, what gets printed now? Why?
* Now, let’s start actually using the bean. Change the `greet` method on `GreetingResource` to:
        
      @GET
      @Path("greet")
      public String greet() {
        return "Hello, " + subjectBean.subject();
      }

  And refresh several times. What happens now? Why is it different from the previous question?
* Make the `GreetingResource` print "GreetingResource Ready" on application startup!
* Add a configuration property `greeting` with value "Howdy" to your configuration file, inject it into your `GreetingResource`, and use it instead of the hardcoded "Hello" in the `greet()` method.
* Don’t forget to update the test `GreetingResourceTest` as well!
* Extra: Constructor injection is typically preferable over field injection. Change `GreetingResource` to use constructor injection instead.


## Exercise 7: Convert endpoints to JSON

In this exercise, we will abandon our Qute templates, and convert our endpoints to returning JSON instead of HTML. Later, we will hook up a React frontend application to these endpoints.

* Remove the `@Injected` templates from the `ProductsResource`
* Make the `products` and `details` method return a JSON representation of a list of products or a single product, respectively, instead. For this you will need to add an `@Produces` annotation with the right `MediaType` either on the class, or on each of the methods.

The following three steps are only useful if you wrote the tests for these endpoints in Exercise #3: 

* Extra: Update the tests for the list and details endpoint and make them check for the right content-type.
* Extra: Update the test for the details endpoint, and use the Json-path expression `name` to test that the value for the url `/products/1` equals "Chair".
* Extra extra: Change the test to be independent from the database that's started manually, by making use of the Testcontainers project.

Now, we will be adding OpenAPI support and Swagger UI to our application, so we have better visibility into our REST endpoint. 

* Add the `quarkus-smallrye-openapi` extension to your application:
    
      <dependency>
        <groupId>io.quarkus</groupId>
        <artifactId>quarkus-smallrye-openapi</artifactId>
      </dependency>

* Browse to http://localhost:8080/ and observe under _Additional endpoints_, that two new endpoints emerged: `/q/openapi` and `/q/swagger-ui/`
* Browse to http://localhost:8080/q/swagger-ui/. You will see our four endpoints, and you can try them out in the UI. Try sending some requests to them!


## Exercise 8: Adding REST data Panache

In this exercise, we will see how we can create close to no-code CRUD endpoints with the _hibernate-orm-rest-data-panache_ extension.

* Add the following extension to your dependencies:
      
      <dependency>
        <groupId>io.quarkus</groupId>
        <artifactId>quarkus-hibernate-orm-rest-data-panache</artifactId>
      </dependency>

* Create a new *interface* `PanacheProductsResource` that extends `PanacheEntityResource<Product, Long>`
* Browse to the Swagger UI endpoint at http://localhost:8080/q/swagger-ui/ and observe the new endpoints that Panache created.
* Create a new product using Swagger UI, by posting the following JSON to the `POST panache-products` endpoint:

      {
        "name": "Couch",
        "description": "A leather couch",
        "price": 399
      }

* Check the `/panache-products` (or your own `/products`) endpoint to see if you find your newly created couch back.


## Exercise 9: Hook up the React app

In this exercise we will add a (premade) React frontend to our application. This frontend application understands some _feature flags_ to enable or disable certain functionality. So first, we will add a backend resource to serve these feature flags to the frontend.

* Create a `FeatureFlagsResource` with a  `/feature-flags` endpoint that serves the following JSON structure. Make it such that the flags can be configured in the `application.properties` configuration file:

      {
        "productDetails": true,
        "productSearch": false,
        "productUpdate": false,
        "reactivePrices": false
      }

* Next, copy the files `index.html` and `bundle.min.js` from `/materials/exercise-9` into a new directory `src/main/resources/META-INF/resources`
* Now, browse to http://localhost:8080/ and you should see the HIQUEA frontend application!


## Exercise 10: Validation & PUT

In this exercise, we will add a `PUT` endpoint to the backend, so that products can also be updated. We will use the `quarkus-hibernate-validator` extension to validate input. We will also *remove* the `quarkus-hibernate-orm-rest-data-panache` extension, and make our own endpoint instead.

* Remove the `quarkus-hibernate-orm-rest-data-panache` extension and the `PanacheProductsResource`.
* In the `ProductsResource`, create a new `update()` method that takes a `PUT` request, with the product id as a path parameter, and the updated product as a body parameter (you can just add a parameter `Product product` to the method, and it will happen automatically).
* Add an `@Consumes` with the right media type.  
* In the method, lookup the existing product by the id path parameter, and throw a `NotFoundException` if it doesn’t exist. If it does exist, update the `name`, `description` and `price` and persist (and flush) it again. Then return the updated `Product`.
* Check if it works with the Swagger UI. For example, try this input to `PUT /products/1`:

      {
        "description": "A Very Comfy Chair",
        "name": "Comfy, expensive, but worth it!",
        "price": 999.99
      }
  Probably you ran into an error - what was missing from the instructions?


Now, some validation.

* Add the `quarkus-hibernate-validator` extension, and mark the `Product` parameter with the `@Valid` annotation.
Use `@Length`, `@DecimalMin` and `@Digits` annotations to achieve the following validation rules:
    - Name must be set and have a length of at least 3 characters
    - Description must be set and have a length of at least 10 character
    - Price must be set and not be negative
    - Price must not have more than two fractional digits

* Try your new endpoint with the Swagger UI (http://localhost:8080/q/swagger-ui)
  This input to `PUT /products/1` should still work:
    
      {
        "description": "A Very Comfy Chair",
        "name": "Comfy, expensive, but worth it!",
        "price": 999.99
      }

  But this should return an error response;

      {
        "name": "Chair",
        "description": "Comfy",
        "price": 12.345
      }

* Finally, enable the `productUpdate` feature flag to make it also possible from the React app to edit products! You should see an 'edit' button appear in each product card in the catalogue. Give it a spin :)


## Exercise 11: Going Reactive

In this exercise, we will migrate our Hiquea app to the Reactive programming model. For this, we will use RESTeasy Reactive and Hibernate Reactive.

* Replace in your `pom.xml` the various `quarkus-resteasy` extensions with `quarkus-resteasy-reactive` variants.
* Replace `quarkus-jdbc-postgresql` with `quarkus-reactive-pg-client`.
* Replace `quarkus-hibernate-orm-panache` with `quarkus-hibernate-reactive-panache`
  
Note: one of the reasons we removed the `quarkus-hibernate-orm-rest-data-panache` extension in the previous exercise is that there is no reactive replacement for this extension at the time of writing.

* Remove the setting `quarkus.datasource.jdbc.url`, and replace it with this setting:
` quarkus.datasource.reactive.url=postgresql://localhost:8765/postgres`
* Go to your `Product` class. Delete the old `PanacheEntity` import, and find the proper import to use now.
* Now, go to `ProductsResource`, and make it work again. Note that you can return `Uni` or `Multi` reponses from your resource methods now that you have RESTeasy reactive.
  Try two options: returning a `Multi<Product>` from the `products()` method, or returning a `Uni<List<Product>>`. What’s the conceptual difference between these?
* For the `PUT` endpoint, do the following:
  - Start with `Product.<Product>findById(id)`, and `flatMap` the resulting `Uni`.
  - Within the `flatMap`, update the product, and invoke `persistAndFlush`
   
    … this will properly ‘chain’ the operations.
* Check if the frontend still works :)


## Exercise 12: Reactive search endpoint

In this exercise we will add a search endpoint to the Hiquea backend, using the low-level SQL client.

* Create a method `public static Product from(Row row)` on the `Product` class, that creates a `Product` from an `io.vertx.mutiny.sqlclient.Row`.
* `@Inject` a `PgPool` instance into your `ProductsResource` class. (Note: There are two `PgPool` types in two packages; which one do you need?)
* Now make the following resource method into your `ProductsResource` class:

      @GET
      @Path("search/{term}")
      public Multi<Product> search(@PathParam("term") String term) {
        return client
          .preparedQuery("SELECT id, name, description, price FROM product WHERE name ILIKE $1 OR description ILIKE $1")
          .execute(Tuple.of("%" + term + "%"))
            <fill in this part yourself!!!>
          .map(Product::from);
      }

    Fill in the missing part! You need to transform from a `Uni<RowSet<Row>>` to a `Multi<Row>`.
* Try out your new endpoint by searching for all products that have 'oak' in their name or description: http://localhost:8080/products/search/oak
* Enable the feature flag `productSearch`, and notice a search field appearing at the top right of the Hiquea frontend!


## Exercise 13: Listen & Notify

In this exercise, we will play a bit with Postgres’ `LISTEN / NOTIFY` feature, to get a better grip on reactive streams, and we will also use Server Sent Events.

The `LISTEN / NOTIFY` feature of Postgres allows you to setup a connection to Postgres, and listen for evens that pass by on a channel, as well as notifying such channels. With the reactive sql clients, we can connect to these channels as a `Multi` in Quarkus.

* Copy the class in `/materials/exercise-13/ListenNotifyResource.java` into `src/main/java/com/lunatech/training/quarkus/`
* Connect to the channel `milkshakes` using the following cURL command.

      curl localhost:8080/channel/milkshakes

  Note that it’s expected to ‘hang’; because it’s connecting to a chunked HTTP endpoint and waiting for chunks. 
  
* In a different terminal window, run

		curl -X POST --data "strawberry" localhost:8080/channel/milkshakes 

	(Or use other flavours of milkshake if you don’t like strawberry). You can run it multiple times.
* You should see the chunks with the Postgres notifications in JSON format flow by in the first terminal window.
* Inspect the code. Can you describe what happens?


## Exercise 14: Internal Channels

In this exercise we will start using the Microprofile Reactive Messaging specification to build some streaming stuff!

In an ever-demanding market, we just can’t have a furniture store with fixed prices for furniture. Instead, we want new prices for everything, every five seconds!

We will create a _Generator_ that generates new prices for all our products every five seconds, and connect this stream to other components.

* Add the `quarkus-smallrye-reactive-messaging` extension to your `pom.xml`
* Copy the two files in `materials/exercise-14` into `src/main/java/com/lunatech/training/quarkus/`. The `PriceUpdate` class represents an updated price for the product with the product id in the class. The `PriceUpdateStream` class is where we will be doing stream generation and processing.
* Implement the method `public Multi<PriceUpdate> generate()` on the `PriceUpdateStream` class, and make it return a `Multi` that emits a `PriceUpdate` item for each of the products in our database (You can hardcode it to use product ids 1 to 7) *every five seconds*, using a random price between 0 and 100.
  
  Tip, look at the `Multi.createFrom().ticks()` method!
  Note that the `print` method has an `@Incoming` annotation that matches the `@Outgoing` from the `generate` method. Running the application should print seven lines to the console every five seconds, each line being a price update for a product. Run the app to try this :)

This demonstrates the _internal channels_ feature of the Reactive Messaging spec. Quarkus will feed the items coming from the outgoing stream from the `generate` method into the `print` method, because they have the same channel name.

* What happens if you change the channel name on the `@Incoming` annotation?

Now, we will create a _processor_; a method that has both an `@Incoming` and an `@Outgoing` annotation.

* Create a method `process` that takes a PriceUpdate and returns a PriceUpdate. Add the following annotations:

      @Incoming("raw-price-updates")
      @Outgoing("price-updates")

  And change the channel name on the `print` method `@Incoming` annotation to `price-updates`
* Implement the method such that if the `price` field on the `PriceUpdate` is less than 30, 30 is added to it. (We neeeeever want to sell anything for less than 30 euro!)
* Run the app again, and check if you still see price updates. Notice that you shouldn’t see any more updates with a price less than 30.

Finally, we will create a `PriceUpdatesResource` class, so we can expose the price updates as Server Sent Events.

* Remove the `print` method from the `PriceUpdateStreams` class
* Create a class `PriceUpdatesResource`
* Annotate it with `@Path("/prices")`
* You can inject a `Multi<PriceUpdate>` that’s connected to a stream into the `PriceUpdatesResource` as follows:

      @Channel("price-updates")
      Multi<PriceUpdate> priceUpdates;
  
* `@Channel` is also a Reactive Messaging annotation, and Quarkus will connect this `Multi` to the 'price-updates' channel. This is an alternative method to receive the items in that channel (different from how we did it with an `@Incoming` annotation on the `print` method!)
* Next, add this method

      @GET
      @Produces(MediaType.SERVER_SENT_EVENTS)
      @RestSseElementType(MediaType.APPLICATION_JSON)
      public Multi<PriceUpdate> prices() {
        return priceUpdates;
      }

* Now, connect to this endpoint using Curl:

      curl localhost:8080/prices

  You should see prices streaming by.


## Exercise 15: Kafka

In this exercise, we will connect our price processing components to Kafka. We will add Kafka to our `docker-compose` setup, and connect the reactive messaging components to Kafka using the `smallrye-reactive-messaging-kafka` extension.

Tip: If something fails, you can use [Conductor](https://conduktor.io) to check what’s going on in Kafka.

* Uncomment the 'zookeeper' and 'kafka' services in the `docker-compose.yml`
* Run `docker-compose up -d`. This will now start Zookeeper and Kafka (next to the still-running Postgres)
* Add the `quarkus-smallrye-reactive-messaging-kafka` extension to your `pom.xml`
* Copy the class `PriceUpdateDeserializer.java` from `/materials/exercise-15` into `src/main/java/com/lunatech/training/quarkus`
* On the class `PriceUpdateStreams`:
    - On the `generate` method, change the `@Outgoing` channel name to `raw-price-updates-out`
    - On the `process` method, change the `@Incoming` channel name to `raw-price-updates-in`
    - On the `process` method, change the `@Outoing` channel name to `price-updates-out`
* On the class PriceUpdatesResource:
    - Change the channel name in the `@Channel` annotation to `price-updates-in`
* Add the following config:

      kafka.bootstrap.servers=127.0.0.1:9092
      mp.messaging.outgoing.raw-price-updates-out.connector=smallrye-kafka
      mp.messaging.outgoing.raw-price-updates-out.topic=raw-prices
      mp.messaging.outgoing.raw-price-updates-out.value.serializer=io.quarkus.kafka.client.serialization.ObjectMapperSerializer
      mp.messaging.incoming.raw-price-updates-in.connector=smallrye-kafka
      mp.messaging.incoming.raw-price-updates-in.topic=raw-prices
      mp.messaging.incoming.raw-price-updates-in.value.deserializer=com.lunatech.training.quarkus.PriceUpdateDeserializer
      mp.messaging.outgoing.price-updates-out.connector=smallrye-kafka
      mp.messaging.outgoing.price-updates-out.topic=prices
      mp.messaging.outgoing.price-updates-out.value.serializer=io.quarkus.kafka.client.serialization.ObjectMapperSerializer
      mp.messaging.incoming.price-updates-in.connector=smallrye-kafka
      mp.messaging.incoming.price-updates-in.topic=prices
      mp.messaging.incoming.price-updates-in.value.deserializer=com.lunatech.training.quarkus.PriceUpdateDeserializer

* Execute the cURL command again from the previous exercise:

      curl http://localhost:8080/prices

* You should see price updates streaming by again.
* Check what’s going on in Kafka with Conduktor if you haven’t yet.


## Exercise 16: Dead Letter Queue & Stream filtering

In this exercise we will see a method to deal with ‘broken’ messages.

* Go to the `PriceUpdateStreams`, and change the `process` method so that it no longer changes the `PriceUpdate` when the price is below 30, but rather throws a runtime exception.
* What happens if you run the application now, and connect a consumer (`curl http://localhost:8080/prices`)?
* Answer: the stream stops after the failure. This is sometimes the right behaviour (maybe we need to update our application to deal with the messages properly), but sometimes wrong. We want to use the _dead letter_ functionality instead.
* Add the following config:

      mp.messaging.incoming.raw-price-updates-in.failure-strategy=dead-letter-queue
      mp.messaging.incoming.raw-price-updates-in.dead-letter-queue.value.serializer=io.quarkus.kafka.client.serialization.ObjectMapperSerializer

* Restart the app, and observe that the stream works again (curl http://localhost:8080/prices), although now most of the times you end up with less than 7 updates per 5 seconds. The failures end up in the topic `dead-letter-topic-raw-price-updates-in`. You can easily inspect it with Conduktor.

Finally, we want to connect our React frontend to the cool new price-streaming feature. But before we do so, we have to make one more endpoint; that only streams prices for an individual product.

* Create an endpoint `/prices/{productId}` that returns only the prices for the product with that id. Thinks about the methods you have on `Multi` to achieve this given the `@Channel`-injected `Multi` in the class.

Next, we need to update the last remaining feature flag (`reactivePrices`), and take one more look at our Hiquea app. The prices are now updated every five seconds!

Congratulations, you have finished all exercises :)
