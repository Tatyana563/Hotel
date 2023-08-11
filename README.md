Flow:
The user looks for hotels between 2 dates and of particular level (stars)
http://localhost:8080/hotel/search?city=Barcelona&starRating=THREE&roomType=DOUBLE&checkIn=05_05_2023&checkOut=08_05_2023

User chooses one hotel from the list and gets list of rooms in this hotel available between dates. (QUESTION: shall we have StarRating? )
http://localhost:8080/hotel/1/filter?starRating=THREE&roomType=DOUBLE&checkIn=05_05_2023&checkOut=08_05_2023

User chooses one room and books it.
http://localhost:8080/rooms/1?checkIn=05_05_2023&checkOut=08_05_2023


Liquibase:
mvn liquibase:rollback -Dliquibase.rollbackCount=1
mvn liquibase:update