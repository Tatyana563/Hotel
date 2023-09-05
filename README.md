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


Liquibase:
mvn liquibase:rollback -Dliquibase.rollbackCount=1
mvn liquibase:update