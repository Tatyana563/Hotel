package com.hotel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.config.properties.RegistrationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.configurationprocessor.json.JSONTokener;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
public class Utils {
    private final RegistrationProperties registrationProperties;
    private final Clock clock;

    public static Instant convertStringDateToInstant(String stringDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date checkInDate = null;
        try {
            checkInDate = dateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return checkInDate.toInstant();
    }

    public static void main(String[] args) throws JSONException, IOException {
        String filePath = "hotel/hotel/src/main/resources/countries_with_cities.json";
        String filePathWithoutDuplicates = "hotel/hotel/src/main/resources/countries_with_cities_duplicates_removed.json";
        String input = Files.readString(Paths.get(filePath));
        removeDuplicatesCities(input, filePathWithoutDuplicates);

        try {
            String jsonData = Files.readString(Paths.get(filePathWithoutDuplicates));
            convertJsonToXml(jsonData);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }


    public static void convertJsonToXml(String json) {
        StringBuilder xmlOutput = new StringBuilder();
        try {
            JSONObject root = new JSONObject(new JSONTokener(json));
            JSONArray countries = root.getJSONArray("countries");

            for (int i = 0; i < countries.length(); i++) {
                JSONObject country = countries.getJSONObject(i);
                String countryName = country.getString("country");
                JSONArray cities = country.getJSONArray("cities");

                for (int j = 0; j < cities.length(); j++) {
                    xmlOutput.append("<insert tableName=\"city\">\n");
                    xmlOutput.append("    <column name=\"name\" value=\"").append(cities.getString(j)).append("\"/>\n");
                    xmlOutput.append("    <column name=\"country\" value=\"").append(countryName).append("\"/>\n");
                    xmlOutput.append("</insert>\n");
                }
            }
        } catch (Exception e) {
            System.err.println("Error processing JSON: " + e.getMessage());
        }
        writeToFileLiquibaseChangeSet(xmlOutput.toString());
    }

    private static void writeToFileLiquibaseChangeSet(String data) {

        try {
            FileWriter writer = new FileWriter("hotel/hotel/src/main/resources/v1.0_01_city_values_schema.xml");
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "\n" +
                    "<databaseChangeLog\n" +
                    "        xmlns=\"http://www.liquibase.org/xml/ns/dbchangelog\"\n" +
                    "        xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                    "        xsi:schemaLocation=\"http://www.liquibase.org/xml/ns/dbchangelog\n" +
                    "         http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd\">\n" +
                    "\n" +
                    "    <changeSet id=\"01\" author=\"petrenko\">");
            writer.write(data);
            writer.write("    </changeSet>\n" +
                    "\n" +
                    "</databaseChangeLog>");
            writer.close();
            log.info("Successfully wrote to the file.");
        } catch (IOException e) {
            log.error("An error occurred while writing to the file", e);
        }
    }

    private static void writeToFile(String data, String outputFile) {

        try {
            FileWriter writer = new FileWriter(outputFile);

            writer.write(data);
            writer.close();

        } catch (IOException e) {
            log.error("An error occurred while writing to the file", e);

        }
    }

    // TODO: POJO for Json country, Set cities;
    private static void removeDuplicatesCities(String input, String outputFile) {
        try {
            if (input == null || input.isEmpty()) {
                log.error("Input JSON string is empty.");
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(input);

            JsonNode countriesNode = jsonNode.get("countries");
            if (countriesNode == null || !countriesNode.isArray()) {
                log.error("Invalid JSON structure: 'countries' node not found or not an array.");
                return;
            }

            for (JsonNode countryNode : countriesNode) {
                JsonNode citiesNode = countryNode.get("cities");
                if (citiesNode == null || !citiesNode.isArray()) {
                    log.error("Invalid JSON structure: 'cities' node not found or not an array for country: " + countryNode.get("country").asText());
                    continue;
                }

                Set<String> uniqueCities = new HashSet<>();
                for (JsonNode cityNode : citiesNode) {
                    uniqueCities.add(cityNode.asText());
                }
                ((com.fasterxml.jackson.databind.node.ArrayNode) citiesNode).removeAll();
                for (String city : uniqueCities) {
                    ((com.fasterxml.jackson.databind.node.ArrayNode) citiesNode).add(city);
                }
            }

            String updatedJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);

            writeToFile(updatedJsonString, outputFile);

        } catch (IOException e) {
            log.error("Error occurred removing duplicates", e);
        }
    }

}

