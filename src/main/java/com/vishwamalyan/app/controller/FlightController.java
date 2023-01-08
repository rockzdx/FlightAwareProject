package com.vishwamalyan.app.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vishwamalyan.app.model.User;
import com.vishwamalyan.app.model.UserIp;
import com.vishwamalyan.app.repository.FlightRepo;
import com.vishwamalyan.app.repository.userRepo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Controller
public class FlightController {

	@Autowired
	FlightRepo frepo;

	@Autowired
	userRepo urepo;

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	cityCode cc = new cityCode();
	long loggedInUser;
	ApiCalls ap = new ApiCalls();

	@GetMapping("/")
	public ModelAndView homeGet(ModelAndView modelAndView, UserIp userip) {
		modelAndView.setViewName("home");
		return modelAndView;
	}

	@GetMapping("/Register")
	public ModelAndView RegGet(ModelAndView modelAndView, User user) {
		modelAndView.addObject("user", user);
		modelAndView.setViewName("register");
		return modelAndView;
	}

	@PostMapping("/Register")
	public String registerUser(ModelAndView modelAndView, User user) {
		String path = null;
		String email = user.getEmailId();
		System.out.println(email);
		User existingUser = urepo.findByEmailIdIgnoreCase(email);
		if (existingUser != null) {
			modelAndView.addObject("msg", "This email already exists!");
			modelAndView.setViewName("register");
		} else {
			System.out.println(user.getPass());
			user.setPass(encoder.encode(user.getPass()));
			urepo.save(user);
			//sendEmail(user.getEmailId());
			modelAndView.addObject("emailId", user.getEmailId());
			path = "redirect:/Login";
		}
		return path;
	}

	@GetMapping("/Login")
	public ModelAndView LogGet(ModelAndView modelAndView, User user) {
		modelAndView.addObject("user", user);
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@PostMapping("/Login")
	public ModelAndView loginUser(ModelAndView modelAndView, User user) {
		String email = user.getEmailId();
		User existingUser = urepo.findByEmailIdIgnoreCase(email);
		System.out.println(existingUser);
		if (existingUser != null) {
			if (encoder.matches(user.getPass(), existingUser.getPass())) {
				// successfully logged in
				modelAndView.addObject("msg", "You Have Successfully Logged in");
				modelAndView.setViewName("loginHome");
			} else {
				// wrong password
				modelAndView.addObject("msg", "Incorrect password. Try again.");
				modelAndView.setViewName("login");
			}
		} else {
			modelAndView.addObject("msg", "The email provided does not exist!");
			modelAndView.setViewName("login");
		}
		return modelAndView;
	}

	@GetMapping("/logHome")
	public ModelAndView loghomeGet(ModelAndView modelAndView, UserIp userip) {
		modelAndView.setViewName("loginHome");
		return modelAndView;
	}


	@GetMapping("/uguides")
	public ModelAndView uguidet(ModelAndView modelAndView, UserIp userip) {
		modelAndView.addObject("userip", userip);
		modelAndView.setViewName("uguide");
		return modelAndView;
	}

	@GetMapping("/countryList")
	public ModelAndView CountryListMVC(ModelAndView modelAndView, UserIp userip) {
		modelAndView.addObject("userip", userip);
		modelAndView.setViewName("countryList");
		return modelAndView;
	}

	@GetMapping("/currencyList")
	public ModelAndView currencyListMVC(ModelAndView modelAndView, UserIp userip) {
		modelAndView.addObject("userip", userip);
		modelAndView.setViewName("currencyList");
		return modelAndView;
	}

	//GetMapping for fetching IATA,Country and Name of the Airport
	@GetMapping("/apiExample")
	public ModelAndView apiExample(ModelAndView modelAndView) throws IOException, InterruptedException, ParserConfigurationException, SAXException {

		// Make the API call and get the response as a string
		String str = ap.apiExample();

		//Converting XML data to JSON object
		JSONObject xmlJSONObj = XML.toJSONObject(str);
		System.out.println(xmlJSONObj.toString());

		// Convert the JSON object to a map
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> jsonMap = mapper.readValue(xmlJSONObj.toString(), Map.class);

		Map<String, Object> airports = (Map<String, Object>) jsonMap.get("Airports");
		List<Map<String, Object>> airportList = (List<Map<String, Object>>) airports.get("Airport");

		// Iterate over the list of airports to get the names
		List<Map<String, String>> airportDetails = new ArrayList<>();
		for (Map<String, Object> airport : airportList) {
			Map<String, String> detail = new HashMap<>();
			detail.put("name", (String) airport.get("Name"));
			detail.put("code", (String) airport.get("IATACode"));
			detail.put("country", (String) airport.get("Country"));
			airportDetails.add(detail);
		}
		modelAndView.addObject("airportDetails", airportDetails);

		// Set the view name
		modelAndView.setViewName("uguide");

		// Return the ModelAndView object
		return modelAndView;

	}

	@GetMapping("/countryListHelper")
	public ModelAndView CountryListHelperMVC(ModelAndView modelAndView) throws IOException, InterruptedException, ParserConfigurationException, SAXException {

		// Make the API call and get the response as a string
		String str = ap.countryListAPI();

		//Converting XML data to JSON object
		JSONObject json = new JSONObject(str);
		JSONArray countryArray = json.getJSONArray("country");
		List<Map<String, String>> CountryDetails = new ArrayList<>();
		JSONObject eachCountryObj123 = countryArray.getJSONObject(1);
		System.out.println(eachCountryObj123.get("nicename"));

		for (int i = 0; i < countryArray.length(); i++) {
			JSONObject eachCountryObj = countryArray.getJSONObject(i);
			Map<String, String> detail = new HashMap<>();
			detail.put("name", eachCountryObj.get("id").toString());
			detail.put("code", eachCountryObj.get("iso3").toString());
			detail.put("country", eachCountryObj.get("nicename").toString());
			CountryDetails.add(detail);
		}

		modelAndView.addObject("CountryDetails", CountryDetails);

		// Set the view name
		modelAndView.setViewName("countryList");

		// Return the ModelAndView object
		return modelAndView;
	}

	@GetMapping("/currencyListHelper")
	public ModelAndView CurrencyListHelperMVC(ModelAndView modelAndView) throws IOException, InterruptedException, ParserConfigurationException, SAXException {

		// Make the API call and get the response as a string
		String str = ap.currenciesListAPI();

		//Converting XML data to JSON object
		JSONObject json = new JSONObject(str);
		JSONObject jsonResponse = json.getJSONObject("response");
		JSONObject jsonWithCurrencies = jsonResponse.getJSONObject("fiats");
		Iterator<String> json3 = jsonWithCurrencies.keys();
		List<Map<String, String>> currencyDetails = new ArrayList<>();

		while (json3.hasNext()) {
			JSONObject eachCountryObj = jsonWithCurrencies.getJSONObject(json3.next());
			Map<String, String> detail = new HashMap<>();
			detail.put("name", eachCountryObj.get("currency_name").toString());
			detail.put("code", eachCountryObj.get("currency_code").toString());
			currencyDetails.add(detail);
		}

		modelAndView.addObject("currencyDetails", currencyDetails);

		// Set the view name
		modelAndView.setViewName("currencyList");

		// Return the ModelAndView object
		return modelAndView;
	}

	@GetMapping("/cities")
	public ModelAndView citiesMVC(ModelAndView modelAndView) throws IOException, InterruptedException, ParserConfigurationException, SAXException {

		List<String> detail = new ArrayList<>();


		detail.add("London");
		detail.add("Bengaluru");
		detail.add("Chennai");
		detail.add("Moscow");
		detail.add("New York City");
		detail.add("Florida");
		detail.add("Tokyo");
		detail.add("Dublin");
		detail.add("Helsinki");
		detail.add("Melbourne");
		detail.add("Sydney");
		detail.add("Dubai");


		modelAndView.addObject("CitiesDetails", detail);

		// Set the view name
		modelAndView.setViewName("City");

		// Return the ModelAndView object
		return modelAndView;
	}

	//GetMapping for fetching Flight details
	@GetMapping("/flightSchedules")
	public ModelAndView flightSchedulesGet(ModelAndView modelAndView, UserIp userip) {
		modelAndView.addObject("userip", userip);
		modelAndView.setViewName("dprice");
		return modelAndView;
	}

	//Post Mapping for the form for searching flight api
	@PostMapping(value = "/flightSchedules")
	public ModelAndView flightSchedulesForm(ModelAndView modelAndView, UserIp userip) throws InterruptedException, IOException, ParseException {

		//Using Model to fetch data
		String depature, arrival, date;

		depature = userip.getDepature();
		arrival = userip.getArrival();
		date = userip.getDate();

		// Make the API call and get the response as a string

		String str = ap.flightSchedules(depature, arrival, date);
		System.out.println("data is" + depature + " " + arrival + " " + date);

		// Convert the XML document to a JSON object
		JSONObject xmlJSONObj = XML.toJSONObject(str);

		// Convert the JSON object to a map
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(xmlJSONObj.toString());

		// Get the "FlightDetails" array
		JsonNode flightDetails = root.get("OTA_AirDetailsRS").get("FlightDetails");
		if (flightDetails == null) {
			modelAndView.addObject("flightDetails", null);
			modelAndView.addObject("userip", userip);
			modelAndView.setViewName("dprice");
			return modelAndView;
		} else {

			//Iterating "FlightDetail" json
			List<Map<String, String>> flightDetailsSet = new ArrayList<>();
			for (JsonNode flightDetail : flightDetails) {
				Map<String, String> detail = new HashMap<>();

				//fetching required data from "FlightDetail" api
				String FLSDepartureName = flightDetail.get("FLSDepartureName").toString();
				String FLSArrivalName = flightDetail.get("FLSArrivalName").toString();
				String FLSDepartureDateTime = flightDetail.get("FLSDepartureDateTime").toString();
				String FLSArrivalDateTime = flightDetail.get("FLSArrivalDateTime").toString();


				detail.put("startDate", FLSDepartureDateTime);
				detail.put("endDate", FLSArrivalDateTime);


				// Get the "FlightLegDetails" array inside "FlightDetail"
				JsonNode flightLegDetails = flightDetail.get("FlightLegDetails");

//			 Iterate over the "FlightLegDetails" array
				for (JsonNode flightLegDetail : flightLegDetails) {

					//Fetch flight number details
					String flightNumber = flightLegDetail.get("FlightNumber") == null ? "NA" : flightLegDetail.get("FlightNumber").toString();
					detail.put("flightNumber", flightNumber);


					//Fetching Depature Airport Name
					JsonNode flightLegDetailsDepatureMap = flightLegDetail.get("DepartureAirport");

					if (flightLegDetailsDepatureMap == null || flightLegDetailsDepatureMap.get("FLSLocationName") == null) {
						detail.put("depatureAirport", "NA");
					} else {

						String depatureAirport = flightLegDetailsDepatureMap.get("FLSLocationName").toString();
						detail.put("depatureAirport", depatureAirport + "," + FLSDepartureName);

					}

					//Fetching Arrival Airport Name

					JsonNode flightLegDetailsArrivalMap = flightLegDetail.get("ArrivalAirport");
					if (flightLegDetailsArrivalMap == null || flightLegDetailsArrivalMap.get("FLSLocationName") == null) {
						detail.put("arrivalAirport", "NA");
					} else {

						String arrivalAirport = flightLegDetailsArrivalMap.get("FLSLocationName").toString();
						detail.put("arrivalAirport", arrivalAirport + "," + FLSArrivalName);

					}


					//Fetching Airlines Name
					JsonNode flightLegDetailsAirlinesMap = flightLegDetail.get("MarketingAirline");
					if (flightLegDetailsAirlinesMap == null || flightLegDetailsAirlinesMap.get("CompanyShortName") == null) {
						detail.put("airlinesName", "NA");
					} else {
						String airlinesName = flightLegDetailsAirlinesMap.get("CompanyShortName").toString();
						detail.put("airlinesName", airlinesName);
					}

				}

				flightDetailsSet.add(detail);
			}


			System.out.println("answer" + flightDetails.get(0).get("FLSArrivalName"));
			modelAndView.addObject("flightDetails", flightDetailsSet);
			modelAndView.addObject("userip", userip);
			modelAndView.setViewName("dprice");
			return modelAndView;
		}
	}

	@GetMapping("/bestFlight")
	public ModelAndView bestFlightGet(ModelAndView modelAndView, UserIp userip) {
		modelAndView.addObject("userip", userip);
		modelAndView.setViewName("bestFlight");
		return modelAndView;
	}

	//Post Mapping for the form for searching flight api
	@PostMapping(value = "/bestFlight")
	public ModelAndView bestFlightForm(ModelAndView modelAndView, UserIp userip) throws InterruptedException, IOException, ParseException {

		//Using Model to fetch data
		String depature, arrival, date, no;


		depature = userip.getDepature();
		arrival = userip.getArrival();
		date = userip.getDate();
		no = userip.getNo();

		// Make the API call and get the response as a string

		String str = ap.bestFlight(depature, arrival, date, no);


		System.out.println("data is" + depature + " " + arrival + " " + date + " " + no);

		JSONObject json = new JSONObject(str);
		JSONObject jsonItineraries = json.getJSONObject("itineraries");
		JSONArray typesArray = jsonItineraries.getJSONArray("buckets");

		JSONObject bestTypeObj = typesArray.getJSONObject(0);
		JSONArray jsonItems = bestTypeObj.getJSONArray("items");

		JSONObject zeroThObj = jsonItems.getJSONObject(0);
		JSONObject priceObj = zeroThObj.getJSONObject("price");

		JSONArray legsObj = zeroThObj.getJSONArray("legs");

		JSONObject valiObj = legsObj.getJSONObject((0));

		JSONArray segObj = valiObj.getJSONArray("segments");
		JSONObject segObjZero = segObj.getJSONObject(0);

		JSONObject market = segObjZero.getJSONObject("marketingCarrier");


		Map<String, String> detail = new HashMap<>();
		detail.put("price", priceObj.get("raw").toString());
		detail.put("departure", valiObj.get("departure").toString());
		detail.put("arrival", valiObj.get("arrival").toString());
		detail.put("flightNo", segObjZero.get("flightNumber").toString());
		detail.put("flightName", market.get("name").toString());

		List<Map<String, String>> flightDetailsSet = new ArrayList<>();

		modelAndView.addObject("flightDetails", detail);
		modelAndView.addObject("userip", userip);
		modelAndView.setViewName("bestFlight");
		return modelAndView;

	}
}





