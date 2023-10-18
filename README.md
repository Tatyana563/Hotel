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

http://localhost:8080/user/register?name=Tom&surname=Smith&username=tom_smith&login=Tomas&password=123&phone=+380682674545&email=nikolaev.english@gmail.com

Liquibase:
mvn liquibase:rollback -Dliquibase.rollbackCount=1
mvn liquibase:update

REGISTRATION CONFIRMATION:
http://localhost:8080/user/registration/confirmation?token=74df4d14-885d-46b6-90e0-ab305aa8d754


