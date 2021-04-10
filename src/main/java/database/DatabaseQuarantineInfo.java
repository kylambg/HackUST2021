package database;

import city.AddCityRequest;

import quarantineinfo.AddQuarantineRequest;
import quarantineinfo.QuarantineSearchRequest;
import quarantineinfo.QuarantineSearchResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseQuarantineInfo {
    public static void searchInfo(QuarantineSearchRequest request, StringBuilder searchSql, QuarantineSearchResponse.Builder responseBuilder) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(searchSql.toString())) {
                int count = 1;
                if (request.isSearchByCountryID()) {
                    preparedStatement.setInt(count++, request.getCountryid());
                }
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int quarantineID = resultSet.getInt("quarantineinfoid");
                    int countryID =resultSet.getInt("countryid");
                    String mask = resultSet.getString("mask");
                    int test = resultSet.getInt("test");
                    int quarantine = resultSet.getInt("quarantine");
                    String locatorForm = resultSet.getString("locator_form");
                    String healthForm = resultSet.getString("health_form");
                    String authorisation = resultSet.getString("authorisation");
                    String vaccination = resultSet.getString("vaccination");
                    String insurance = resultSet.getString("insurance");
                    responseBuilder.addInfo(new AddQuarantineRequest(quarantineID,countryID,mask,test,quarantine,locatorForm,healthForm,authorisation,vaccination,insurance));
                }
            }
        }
    }
}
