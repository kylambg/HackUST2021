package database;

import com.mysql.jdbc.exceptions.MySQLTimeoutException;
import org.jetbrains.annotations.NotNull;
import proposal.AddProposalRequest;
import proposal.ProposalSearchRequest;
import proposal.ProposalSearchResponse;
import touristSpot.AddTouristSpotRequest;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseProposal {
    public static int TIMEOUT_VALUE = 5;

    public static int isProposalExist(Connection connection, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT proposalid FROM proposal WHERE submitid = ?")) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }
        return -1;
    }
    public static boolean addProposal(Connection connection, int submitID, int destination, String arrivalDate, String leaveDate, int depatureFlightID, int returnFlightID, int hotelID, int budget, ArrayList<AddTouristSpotRequest> spots) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO proposal (submitid, destination, arrival_date, leave_date, departure_flightid, return_flightid, hotelid, budget) VALUES (?,?,?,?,?,?,?,?)")) {
            statement.setInt(1, submitID);
            statement.setInt(2, destination);
            statement.setString(3, arrivalDate);
            statement.setString(4, leaveDate);
            statement.setInt(5, depatureFlightID);
            statement.setInt(6, returnFlightID);
            statement.setInt(7,hotelID);
            statement.setInt(8, budget);

            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int proposalID = generatedKeys.getInt(1);
                    try (PreparedStatement statement1 = connection.prepareStatement("INSERT INTO proposal_spot(proposalid,tourist_spotid) VALUES (?,?)")){
                        for(int i = 0; i < spots.size(); ++i){
                            statement1.setInt(1,proposalID);
                            statement1.setString(2,spots.get(i).getTouristSpotID());
                        }
                    }
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            return false;
        } catch (SQLException e) {
            if (e.getSQLState().startsWith("23")) {
                // duplicate book found, violates integrity constraint
                System.out.println("duplicate proposal");
                return true;
            }
            throw e;
        }
    }
    public static void reviewProposal(@NotNull Connection connection, int proposalID,int userID) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE proposal SET reviewid = ? WHERE proposalid = ?")) {
            statement.setInt(1, userID);
            statement.setInt(2, proposalID);
            statement.execute();
        }
    }

    public static boolean deleteProposal(@NotNull Connection connection, int userid) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM proposal WHERE userid = ?")) {
            statement.setInt(1, userid);
            int count = statement.executeUpdate();
            return count > 0;
        }
    }

    public static void searchProposal(ProposalSearchRequest request, StringBuilder searchSql, ProposalSearchResponse.Builder responseBuilder) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(searchSql.toString())) {
                int count = 1;
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int proposalid = resultSet.getInt("proposalid");
                    ArrayList<AddTouristSpotRequest> spots = new ArrayList<>();
                    try (PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT tourist_spotid FROM proposal_spot WHERE proposalid = ?")){
                    preparedStatement1.setInt(1,proposalid);
                    ResultSet resultSet1 = preparedStatement1.executeQuery();
                        while(resultSet1.next()){
                            String spotID = resultSet.getString("touristspotid");
                            String cityID = resultSet.getString("cityid");
                            String name = resultSet.getString("name");
                            int charge = resultSet.getInt("charge");
                            spots.add(new AddTouristSpotRequest(spotID,cityID,name,charge));
                        }
                    }
                    int submitid = resultSet.getInt("submitid");
                    int reviewid = 0;
                    int destination = resultSet.getInt("destination");
                    String arrival_date = resultSet.getString("arrival_date");
                    String leave_date = resultSet.getString("leave_date");
                    int did = resultSet.getInt("departure_flightid");
                    int rid = resultSet.getInt("return_flightid");
                    int hid = resultSet.getInt("hotelid");
                    int budget = resultSet.getInt("budget");
                    responseBuilder.addProposal(new AddProposalRequest(proposalid,submitid,reviewid,destination,arrival_date,leave_date,did,rid,hid,budget,spots));
                }
            }
        }
    }
}
