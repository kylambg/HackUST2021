package database;

import covidinfo.AddCovidInfoRequest;
import covidinfo.CovidInfoSearchResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseInfo {
    public static void getInfo(CovidInfoSearchResponse.Builder responseBuilder) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM covidinfo")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int infoID = resultSet.getInt("infoid");
                    int countryID = resultSet.getInt("countryid");
                    String info = resultSet.getString("info");
                    responseBuilder.addInfo(new AddCovidInfoRequest(infoID,countryID,info));
                }
            }
        }
    }

}
