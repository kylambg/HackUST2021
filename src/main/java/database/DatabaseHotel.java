package database;

import hotel.AddHotelRequest;
import hotel.HotelSearchRequest;
import hotel.HotelSearchResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHotel {
    public static void searchHotels(HotelSearchRequest request, StringBuilder searchSql, HotelSearchResponse.Builder responseBuilder) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(searchSql.toString())) {
                int count = 1;
                if (request.isSearchByCityID()) {
                    preparedStatement.setInt(count++, request.getCityID());
                }
                if (request.isSearchByName()) {
                    preparedStatement.setString(count++, request.getHotelName());
                }
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int hotelID = resultSet.getInt("hotelid");
                    int cityID = resultSet.getInt("cityid");
                    String name = resultSet.getString("name");
                    int price = resultSet.getInt("price");
                    int star = resultSet.getInt("star");
                    responseBuilder.addSpot(new AddHotelRequest(hotelID,cityID,name,price,star));
                }
            }
        }
    }
}
