package database;
import city.AddCityRequest;
import city.CitySearchRequest;
import city.CitySearchResponse;

import java.sql.*;

public class DatabaseCity {
    public static void searchCities(CitySearchRequest request, StringBuilder searchSql, CitySearchResponse.Builder responseBuilder) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(searchSql.toString())) {
                int count = 1;
                if (request.isSearchByCountryID()) {
                    preparedStatement.setInt(count++, request.getCountryid());
                }
                if (request.isSearchByName()) {
                    preparedStatement.setString(count++, request.getCityName());
                }
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String cityID = resultSet.getString("cityid");
                    String countryID = resultSet.getString("countryid");
                    String name = resultSet.getString("name");
                    responseBuilder.addCountry(new AddCityRequest(cityID,countryID,name));
                }
            }
        }
    }
}
