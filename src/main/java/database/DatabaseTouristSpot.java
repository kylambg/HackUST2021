package database;

import city.AddCityRequest;
import city.CitySearchRequest;
import city.CitySearchResponse;
import country.AddCountryRequest;
import country.CountrySearchResponse;
import touristSpot.AddTouristSpotRequest;
import touristSpot.TouristSpotSearchRequest;
import touristSpot.TouristSpotSearchResponse;

import java.sql.*;

public class DatabaseTouristSpot {
    public static void searchSpots(TouristSpotSearchRequest request, StringBuilder searchSql, TouristSpotSearchResponse.Builder responseBuilder) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(searchSql.toString())) {
                int count = 1;
                if (request.isSearchByCityID()) {
                    preparedStatement.setInt(count++, request.getCityID());
                }
                if (request.isSearchByName()) {
                    preparedStatement.setString(count++, request.getSpotName());
                }
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String spotID = resultSet.getString("touristspotid");
                    String cityID = resultSet.getString("cityid");
                    String name = resultSet.getString("name");
                    int charge = resultSet.getInt("charge");
                    responseBuilder.addSpot(new AddTouristSpotRequest(spotID,cityID,name,charge));
                }
            }
        }
    }
}
