Flow:
The user looks for hotels between 2 dates and of particular level (stars), city
http://localhost:8080/hotel/search?city=Barcelona&starRating=THREE&roomType=DOUBLE&checkIn=05_05_2023&checkOut=08_05_2023

User chooses one hotel from the list and gets list of rooms in this hotel available between dates. (QUESTION: shall we have StarRating? )
http://localhost:8080/hotel/1/filter?checkIn=05_05_2023&checkOut=08_05_2023

User chooses one room and books it.
http://localhost:8080/rooms/2?username=Gabriella&email=nikolaev.english@gmail.com&checkIn=05_05_2023&checkOut=08_05_2023

//TODO: make post not get request as we have many parameters
Filter hotels with specification:
http://localhost:8080/hotel/filter?distance=5&starRating=THREE&meal=BREAKFAST

http://localhost:8080/rooms/filter?distance=5&starRating=THREE&meal=BREAKFAST&price=45


REGISTER NEW USER:

POST http://localhost:8080/registration

{
"name": "Tom",
"surname": "Smith",
"username": "tomas",
"password": "mysecretpasswordtomaspassword",
"login": "tomas",
"phone": "1234567890",
"email": "tomase.doe@example.com",
"role": "USER"
}



Liquibase:
mvn liquibase:rollback -Dliquibase.rollbackCount=1
mvn liquibase:update

REGISTRATION CONFIRMATION:
http://localhost:8080/user/registration/confirmation?token=74df4d14-885d-46b6-90e0-ab305aa8d754

ADD NEW HOTEL:
http://localhost:8080/hotel/add
    {
        "name": "Example Hotel4",
           "starRating": "FOUR",
           "meals": "ALL_INCLUSIVE",
            "distance": 1.8,
            "cityId": 1
    }


