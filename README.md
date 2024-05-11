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
"name": "Tomas",
"surname": "Green",
"username": "tomas",
"password": "123",
"phone": "1234567859023",
"email": "nikolaev.english@gmail.com",
"role": "OWNER"
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

************************************************************************************
FLOW:
1) mvn liquibase:update
2) Create user:
   POST http://localhost:8080/registration
   {
   "name": "Tomas",
   "surname": "Green",
   "username": "tomas",
   "password": "123",
   "login": "tomas",
   "phone": "1234567859023",
   "email": "tomase.doe@exa2mple.com",
   "role": "OWNER"
   }
3) Activate user:
   POST http://localhost:8080/registration/8aeed4f5-5253-418b-a949-601823702615/confirm
4) Login
   POST http://localhost:8080/login
   {
   "username": "tomas",
   "password":  "123"
   }

5) add hotel
   http://{{host}}/hotels
   POST
   {
   "name": "Example Hotel_3",
   "starRating": "FIVE",
   "meals": "ALL_INCLUSIVE",
   "distance": 1.8,
   "cityId": 1

   }
6) get all hotels with brief info (user should have role 'USER')
   GET
   http://{{host}}/hotels/all_hotels_brief

7) From the previous request we can get id and add rooms
   POST
   http://localhost:8080/hotels/1/room
   {"number":35,
   "type":"DOUBLE",
   "sleeps":"TWO",
   "price":10,
   "square":25,
   "parking":true,
   "pets":true}
8) We can delete a room
   DELETE
   http://{{host}}/hotels/1/room/1
9) The user looks for hotels between 2 dates and of particular level (stars), city
   http://{{host}}/hotel/search?city=Barcelona&starRating=THREE&roomType=DOUBLE&checkIn=2024-05-15&checkOut=2024-05-16




OpenApi definition http://localhost:8080/swagger-ui/index.html

http://localhost:8080/swagger-ui/index.html

