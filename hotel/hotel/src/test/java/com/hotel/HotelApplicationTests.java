package com.hotel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.model.entity.*;
import com.hotel.model.enumeration.Meals;
import com.hotel.model.enumeration.RoomType;
import com.hotel.model.enumeration.Sleeps;
import com.hotel.model.enumeration.StarRating;
import com.hotel.repository.CityRepository;
import com.hotel.repository.HotelRepository;
import com.hotel.repository.RoleRepository;
import com.hotel.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
class HotelApplicationTests {
    private static final Random random = new Random();
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Test
    void contextLoads() throws IOException {

        generateRandomUsers();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            generateTestDataForHotels(user);
        }
    }

    @Transactional
    public void generateRandomUsers() throws IOException {
        List<User> users = new ArrayList<>();
        List<Role> roles = roleRepository.findAll();
        for (int i = 0; i < 9; i++) {
            User user = new User();

            List<String> namesFromFile = readNamesFromFile("C:\\Users\\tpetrenko\\IdeaProjects\\Hotel\\hotel\\hotel\\src\\main\\resources\\names.json");
            user.setName(namesFromFile.get(i));
            user.setSurname(readNamesFromFile("C:\\Users\\tpetrenko\\IdeaProjects\\Hotel\\hotel\\hotel\\src\\main\\resources\\surnames.json").get(i));
            user.setUsername(readNamesFromFile("C:\\Users\\tpetrenko\\IdeaProjects\\Hotel\\hotel\\hotel\\src\\main\\resources\\names.json").get(i) + "_" + (readNamesFromFile("C:\\Users\\tpetrenko\\IdeaProjects\\Hotel\\hotel\\hotel\\src\\main\\resources\\surnames.json")).get(i));

            user.setPassword(generateRandomPassword(8));

            // Generate random phone number (assuming 10 digits)
            user.setPhone(generateRandomPhoneNumber());

            // Generate random email (assuming a simple format)
            user.setEmail(readNamesFromFile("C:\\Users\\tpetrenko\\IdeaProjects\\Hotel\\hotel\\hotel\\src\\main\\resources\\emails.json").get(i));

            // Set enabled status randomly
            user.setEnabled(random.nextBoolean());

            // Assign a random role from the provided list

            user.setRole(roles.get(random.nextInt(roles.size())));

            users.add(user);
        }

        userRepository.saveAll(users);
    }

    public void generateTestDataForHotels(User user) throws IOException {

        List<Hotel> hotels = new ArrayList<>();

        // Generate random number of hotels for the user (between 1 and 3)
        int numHotels = random.nextInt(3) + 1;

        for (int i = 0; i < numHotels; i++) {
            Hotel hotel = new Hotel();

            hotel.setName(getRandomHotelName(readNamesFromFile("C:\\Users\\tpetrenko\\IdeaProjects\\Hotel\\hotel\\hotel\\src\\main\\resources\\hotel_names.json")));
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

    public static String generateRandomPassword(int length) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
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

}


