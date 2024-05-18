//package com.hotel;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.hotel.model.pojo.Countries;
//import com.hotel.model.pojo.Country;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.List;
//import java.util.Set;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class UtilsFleProcessing implements ApplicationRunner {
//
//    public static void convertPOJOIntoXml(Countries countries) {
//        StringBuilder xmlOutput = new StringBuilder();
//        List<Country> countryList = countries.getCountries();
//        for (int i = 0; i < countryList.size(); i++) {
//            Country country = countryList.get(i);
//            String countryName = country.getCountry();
//            Set<String> cities = country.getCities();
//
//            for (String city : cities) {
//
//                xmlOutput.append("<insert tableName=\"city\">\n");
//                xmlOutput.append("    <column name=\"name\" value=\"").append(city).append("\"/>\n");
//                xmlOutput.append("    <column name=\"country\" value=\"").append(countryName).append("\"/>\n");
//                xmlOutput.append("</insert>\n");
//            }
//        }
//
//        writeToFileLiquibaseChangeSet(xmlOutput.toString());
//    }
//
//    private static void writeToFileLiquibaseChangeSet(String data) {
//
//        try {
//            FileWriter writer = new FileWriter("hotel/hotel/src/main/resources/v1.0_01_city_values_schema.xml");
//            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                    "\n" +
//                    "<databaseChangeLog\n" +
//                    "        xmlns=\"http://www.liquibase.org/xml/ns/dbchangelog\"\n" +
//                    "        xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
//                    "        xsi:schemaLocation=\"http://www.liquibase.org/xml/ns/dbchangelog\n" +
//                    "         http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd\">\n" +
//                    "\n" +
//                    "    <changeSet id=\"01\" author=\"petrenko\">");
//            writer.write(data);
//            writer.write("    </changeSet>\n" +
//                    "\n" +
//                    "</databaseChangeLog>");
//            writer.close();
//            log.info("Successfully wrote to the file.");
//        } catch (IOException e) {
//            log.error("An error occurred while writing to the file", e);
//        }
//    }
//
//    private static Countries removeDuplicatesCities(String input) {
//        try {
//            if (input == null || input.isEmpty()) {
//                throw new IllegalArgumentException("Input JSON string is empty.");
//            }
//            ObjectMapper mapper = new ObjectMapper();
//            return mapper.readValue(input, Countries.class);
//
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//
//        Path filePath = Path.of("hotel/hotel/src/main/resources/countries_with_cities.json");
//
//        String input = Files.readString(filePath);
//        Countries countries = removeDuplicatesCities(input);
//
//        convertPOJOIntoXml(countries);
//    }
//}
//
