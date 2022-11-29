# HEB-Rest-Test

## Libraries:
* ZIO with ZIO Webservices: https://zio.dev/guides/quickstarts/restful-webservice/
* Doobie (Database): https://tpolecat.github.io/doobie/docs/01-Introduction.html
* Tranzactio (Doobie ZIO layer): https://github.com/gaelrenoux/tranzactio
* IO Circe (JSON): https://circe.github.io/circe/
* Google Cloud Vision: https://cloud.google.com/vision/docs/reference/rest
* Google Cloud Storage: https://cloud.google.com/storage/docs/apis

## Tasks
1) ~~Setup basic webservices endpoints.~~
    1) ~~Begin with just basic String Get endpoints.~~ 
2) ~~Create case class Models for data.~~
3) Implement JSON conversions for Models.
4) Ensure that endpoints work with the Models and the JSON they're based on.
5) ~~Create Postgresql Database for storage.~~
6) Create Repository to store and read the Data Models.
7) ~~Connect Endpoints to Repository.~~
8) Expand webservice endpoints.
    1) Add in Post endpoint to store data into database.
9) Implement Google Cloud Storage.
    1) This will be to store images.
    2) Database will store location of the images,
10) Implement Google Cloud Vision.
11) Use GCV to detect objects and store as JSON in Database.
12) Update Data Models with new information. 

## Current Progresss
Postgresql Database is setup and running the "test" endpoint will insert a test image into the database.
The major tasks to complete would be implementing the POST endpoint.

## Running the App
Requirements:
- AdoptOpenJDK 11
- Docker
- Docker Compose

1. Run docker-compose.yml to run docker image of Postgresql database.
2. Connect to Database.
3. Run the sql command: CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
   1. This will allow the use of the uuid creation function.
4. Run the sql inside the Create_image_table.sql file
   1. This will create the primary database table
5. Run Main app
   1. https://localhost:8080/imagecount - will display the number of rows in the database.
   2. https://localhost:8080/test - will insert the arrow.png image and a generated UUID into the database.