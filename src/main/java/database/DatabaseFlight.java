package database;

import flight.AddFlightRequest;
import flight.FlightSearchRequest;
import flight.FlightSearchResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseFlight {
    public static void searchFlights(FlightSearchRequest request, StringBuilder searchSql, FlightSearchResponse.Builder responseBuilder) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(searchSql.toString())) {
                int count = 1;
                if (request.isSearchByDepartureDate()) {
                    preparedStatement.setString(count++, request.getDepartureDate());
                }
                if (request.isSearchByDeparture()) {
                    preparedStatement.setInt(count++, request.getDeparture());
                }
                if (request.isSearchByDestination()) {
                    preparedStatement.setInt(count++, request.getDestination());
                }
                if (request.isSearchByCompany()) {
                    preparedStatement.setString(count++, request.getCompany());
                }
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int flightID = resultSet.getInt("flightid");
                    int destination = resultSet.getInt("destination");
                    int departure = resultSet.getInt("departure");
                    String company = resultSet.getString("company");
                    int code = resultSet.getInt("code");
                    String departureDate = resultSet.getString("departure_date");
                    String departureTime = resultSet.getString("departure_time");
                    String arrivalDate = resultSet.getString("arrival_date");
                    String arrivalTime = resultSet.getString("arrival_time");
                    int price = resultSet.getInt("price");
                    responseBuilder.addFlight(new AddFlightRequest(flightID,destination,departure,company,code,departureDate,departureTime,arrivalDate,arrivalTime,price));
                }
            }
        }
    }
}
