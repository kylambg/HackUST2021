package database;

import country.AddCountryRequest;
import country.CountrySearchResponse;

import java.sql.*;


public class DatabaseCountry {
    public static void getCountries(CountrySearchResponse.Builder responseBuilder) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM country")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String id = resultSet.getString("countryid");
                    String name = resultSet.getString("name");
                    responseBuilder.addCountry(new AddCountryRequest(id, name));
                }
            }
        }
    }

}
