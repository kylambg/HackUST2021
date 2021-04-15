package proposal;

import database.ConnectionManager;
import database.DatabaseProposal;
import exception.InternalServerException;
import exception.ProposalExistException;
import exception.ProposalNotExistException;
import touristSpot.AddTouristSpotRequest;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProposalService {
    private static final ProposalService instance = new ProposalService();

    public static ProposalService getInstance() {
        return instance;
    }

    public ProposalSearchResponse searchProposal(ProposalSearchRequest request) throws SQLException {
        StringBuilder searchSql = new StringBuilder();
        searchSql.append("SELECT * FROM proposal");

        ProposalSearchResponse.Builder responseBuilder = new ProposalSearchResponse.Builder();
        DatabaseProposal.searchProposal(request, searchSql, responseBuilder);

        return responseBuilder.build();
    }

    public int addProposal(AddProposalRequest request) throws InternalServerException {
        int submitID = request.getSubmitid();
        int destination = request.getDestination();
        String arrivalDate = request.getArrivalDate();
        String leaveDate = request.getLeaveDate();
        int depatureFlightID = request.getDeparture_flight();
        int returnFlightID = request.getReturn_flight();
        int hotelID = request.getHotelid();
        int budget = request.getBudget();
        ArrayList<AddTouristSpotRequest> spots = request.getSpots();
        try (Connection connection = ConnectionManager.getConnection()) {
            boolean exist = DatabaseProposal.addProposal(connection, submitID, destination, arrivalDate, leaveDate,depatureFlightID,returnFlightID,hotelID,budget,spots);
            int id = DatabaseProposal.isProposalExist(connection, submitID); // the two queries is not necessarily required to be atomic
            if (exist) {
                throw new ProposalExistException(id);
            } else {
                return id;
            }
        } catch (SQLException | ProposalExistException e) {
            throw new InternalServerException(e);
        }
    }

    public boolean deleteProposal(int id) throws ProposalNotExistException, InternalServerException {
        try (Connection connection = ConnectionManager.getConnection()) {
            boolean bookExist = DatabaseProposal.deleteProposal(connection, id);
            if (!bookExist) {
                throw new ProposalNotExistException();
            }
            return true;
        }
        catch (SQLException e) {
            throw new InternalServerException(e);
        }
    }
}
