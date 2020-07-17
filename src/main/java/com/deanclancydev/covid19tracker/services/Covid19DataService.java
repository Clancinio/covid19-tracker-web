package com.deanclancydev.covid19tracker.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// Make this class a Spring Service with @Service
@Service
public class Covid19DataService {

    public static final String VIRUS_DATA_URL = "https://raw.githubusercontent.com/owid/covid-19-data/master/public/data/owid-covid-data.csv";

    // Run this method when the application starts up with @PostConstruct
    @PostConstruct
    public void fetchVirusData() throws IOException, InterruptedException {
        // Create new Http client
        HttpClient client = HttpClient.newHttpClient();

        // Make a request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();

        // Send request
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(httpResponse.body());

        Reader in = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
        for (CSVRecord record : records) {
            String continent = record.get("continent");
            System.out.println(continent);
           // String location = record.get("location");
//            String date = record.get("date");
//            String total_cases = record.get("total_cases");
//            String new_cases = record.get("new_cases");
//            String total_deaths = record.get("total_deaths");
        }
    }
}
