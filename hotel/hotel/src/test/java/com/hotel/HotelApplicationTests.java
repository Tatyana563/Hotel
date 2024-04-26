package com.hotel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.model.entity.*;
import com.hotel.model.enumeration.Meals;
import com.hotel.model.enumeration.RoomType;
import com.hotel.model.enumeration.Sleeps;
import com.hotel.model.enumeration.StarRating;
import com.hotel.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootTest
class HotelApplicationTests {
    private static final int NUMBER_OF_USERS = 10;
    private static final Random random = new Random();
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookRequestRepository bookRequestRepository;

    @Test
    void contextLoads() throws IOException {

        generateRandomUsers();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            generateTestDataForHotels(user);
        }
        List<BookRequest> requestsInThePast = IntStream.range(0, 5)
                .mapToObj(i -> generateRandomBookRequest(true)).toList();
        List<BookRequest> requestsInTheFuture = IntStream.range(0, 5)
                .mapToObj(i -> generateRandomBookRequest(false)).toList();

        List<BookRequest> requests = Stream.concat(requestsInThePast.stream(), requestsInTheFuture.stream())
                .collect(Collectors.toList());
        bookRequestRepository.saveAll(requests);

    }

    @Transactional
    public void generateRandomUsers() throws IOException {
        List<User> users = new ArrayList<>();
        List<Role> roles = roleRepository.findAll();
        List<String> namesFromFile = readNamesFromFile("C:\\Users\\tpetrenko\\IdeaProjects\\Hotel\\hotel\\hotel\\src\\main\\resources\\names.json");
        List<String> surnamesFromFile = readNamesFromFile("C:\\Users\\tpetrenko\\IdeaProjects\\Hotel\\hotel\\hotel\\src\\main\\resources\\surnames.json");
        List<String> emailsFromFile = readNamesFromFile("C:\\Users\\tpetrenko\\IdeaProjects\\Hotel\\hotel\\hotel\\src\\main\\resources\\emails.json");

        for (int i = 1; i < NUMBER_OF_USERS; i++) {
            User user = new User();

            user.setName(namesFromFile.get(i));
            user.setSurname(surnamesFromFile.get(i));
            user.setUsername(namesFromFile.get(i) + "_" + surnamesFromFile.get(i));

            user.setPassword(generateRandomPassword(8));

            // Generate random phone number (assuming 10 digits)
            user.setPhone(generateRandomPhoneNumber());

            // Generate random email (assuming a simple format)
            user.setEmail(emailsFromFile.get(i));

            // Set enabled status randomly
            user.setEnabled(random.nextBoolean());

            // Assign a random role from the provided list

            user.setRole(roles.get(random.nextInt(roles.size())));

            users.add(user);
        }

        userRepository.saveAll(users);
    }

    public void generateTestDataForHotels(User user) throws IOException {
        List<String> hotelNamesFromFile = readNamesFromFile("C:\\Users\\tpetrenko\\IdeaProjects\\Hotel\\hotel\\hotel\\src\\main\\resources\\hotel_names.json");
        List<Hotel> hotels = new ArrayList<>();

        // Generate random number of hotels for the user (between 1 and 3)
        int numHotels = random.nextInt(3) + 1;

        for (int i = 0; i < numHotels; i++) {
            Hotel hotel = new Hotel();

            hotel.setName(getRandomHotelName(hotelNamesFromFile));
            hotel.setStarRating(StarRating.values()[random.nextInt(StarRating.values().length)]);
            hotel.setMeals(Meals.values()[random.nextInt(Meals.values().length)]);
            hotel.setDistance(random.nextInt(100)); // Assuming distance is in kilometers

            List<City> cities = cityRepository.findAll();
            int randomIndex = random.nextInt(cities.size());

            City randomCity = cities.get(randomIndex);

            hotel.setCity(randomCity);

            // Generate random number of rooms for the hotel (between 3 and 5)
            int numRooms = random.nextInt(3) + 3;

            List<Room> rooms = generateTestDataForRooms(numRooms, hotel);
            hotel.setRoomList(rooms);
            hotel.setOwnerId(user.getId());
            hotels.add(hotel);
        }

        hotelRepository.saveAll(hotels);
    }

    private List<Room> generateTestDataForRooms(int numRooms, Hotel hotel) {
        List<Room> rooms = new ArrayList<>();

        for (int i = 0; i < numRooms; i++) {
            Room room = new Room();

            room.setNumber(random.nextInt(100) + 1); // Assuming rooms are numbered from 1 to 100

            room.setType(RoomType.values()[random.nextInt(RoomType.values().length)]);

            room.setSleeps(Sleeps.values()[random.nextInt(Sleeps.values().length)]);

            // Generate random price (assuming price in USD and ranging from 50 to 500 USD)
            room.setPrice(random.nextInt(451) + 50);

            // Generate random square value (assuming square in square meters and ranging from 10 to 50 square meters)
            room.setSquare(random.nextDouble() * 40 + 10);


            room.setParking(random.nextBoolean());


            room.setPets(random.nextBoolean());
            room.setHotel(hotel);
            rooms.add(room);
        }

        return rooms;
    }

    private static List<String> readNamesFromFile(String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> names = new ArrayList<>();
        File file = new File(fileName);
        if (file.exists()) {
            names = objectMapper.readValue(file, new TypeReference<List<String>>() {
            });
        }
        return names;
    }

    private static String getRandomHotelName(List<String> hotelNames) {
        return hotelNames.get(random.nextInt(hotelNames.size()));
    }

    public String generateRandomPassword(int length) {

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }
        return passwordEncoder.encode(password.toString());
    }

    public static String generateRandomPhoneNumber() {
        StringBuilder phoneNumber = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int digit = random.nextInt(10);
            phoneNumber.append(digit);
        }

        return phoneNumber.toString();
    }

    public BookRequest generateRandomBookRequest(boolean isPast) {
        List<Room> rooms = roomRepository.findAll();
        List<User> users = userRepository.findAll();
        Room room = getRandomEntity(rooms);
        User user = getRandomEntity(users);
        BookRequest bookRequest = new BookRequest();
        bookRequest.setUserId(user.getId());
        bookRequest.setRoom(room);
        return setCheckInCheckOut(bookRequest, isPast);

    }

    public <T> T getRandomEntity(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List cannot be null or empty");
        }
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }

    public static BookRequest setCheckInCheckOut(BookRequest bookRequest, boolean past) {
        Instant currentTime = Instant.now();
        Instant firstPoint;
        Instant secondPoint;

        if (past) {
            secondPoint = currentTime.minus(30, ChronoUnit.DAYS);
            firstPoint = currentTime.minus(15, ChronoUnit.DAYS);
        } else {
            secondPoint = currentTime.plus(30, ChronoUnit.DAYS);
            firstPoint = currentTime.plus(15, ChronoUnit.DAYS);
        }
        Instant start = generateRandomInstantBetween(secondPoint, firstPoint);
        Instant end = generateRandomInstantBetween(firstPoint, currentTime);
        bookRequest.setStart(start);
        bookRequest.setEnd(end);
        return bookRequest;
    }

    public static Instant generateRandomInstantBetween(Instant startInstant, Instant endInstant) {
        // Convert start and end instants to milliseconds since the epoch
        long startMillis = startInstant.toEpochMilli();
        long endMillis = endInstant.toEpochMilli();

        // Generate a random number of milliseconds between the start and end millis
        long randomMillis = startMillis + (long) (Math.random() * (endMillis - startMillis));

        // Convert the random milliseconds back to Instant
        return Instant.ofEpochMilli(randomMillis);
    }
}


