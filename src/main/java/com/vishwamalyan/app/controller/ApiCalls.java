package com.vishwamalyan.app.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;

public class ApiCalls {

    //API Call to get list of flights
    public String apiExample() throws IOException, InterruptedException {
        // Create the HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://timetable-lookup.p.rapidapi.com/airports/"))
                .header("X-RapidAPI-Key", "{removed}")
                .header("X-RapidAPI-Host", "timetable-lookup.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        // Send the request and get the response
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        // Print the response body
        System.out.println(response.body());
        return response.body();
    }

    public String countryListAPI() throws IOException, InterruptedException {
        // Create the HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://country-list5.p.rapidapi.com/countrylist/"))
                .header("X-RapidAPI-Key", "{removed}")
                .header("X-RapidAPI-Host", "country-list5.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        // Send the request and get the response
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        // Print the response body
        System.out.println(response.body());
        return response.body();
    }

    public String currenciesListAPI() throws IOException, InterruptedException {
        // Create the HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://currencyscoop.p.rapidapi.com/currencies"))
                .header("X-RapidAPI-Key", "{removed}")
                .header("X-RapidAPI-Host", "currencyscoop.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        // Send the request and get the response
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        // Print the response body
        System.out.println(response.body());
        return response.body();
    }

    public String flightSchedules(String departure, String arrival, String date) throws IOException, InterruptedException, ParseException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://timetable-lookup.p.rapidapi.com/TimeTable/" + departure + "/" + arrival + "/" + date + "/"))
                .header("X-RapidAPI-Key", "{removed}")
                .header("X-RapidAPI-Host", "timetable-lookup.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        return response.body();

    }

    public String bestFlight(String departure, String arrival, String date, String no) throws IOException, InterruptedException, ParseException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://skyscanner44.p.rapidapi.com/search?adults=" + no + "&origin=" + departure + "&destination=" + arrival + "&departureDate=" + date + "&currency=EUR"))
                .header("X-RapidAPI-Key", "{removed}")
                .header("X-RapidAPI-Host", "skyscanner44.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        return response.body();

    }
}