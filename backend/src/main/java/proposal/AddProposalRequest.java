package proposal;

import com.fasterxml.jackson.annotation.JsonProperty;

import touristSpot.AddTouristSpotRequest;

import java.util.ArrayList;

public class AddProposalRequest {
    @JsonProperty("proposalid")
    private int proposalid = 0;
    @JsonProperty("submitter")
    private int submitid = 0;
    @JsonProperty("reviewer")
    private int reviewid = 0;
    @JsonProperty("destination")
    private int destination = 0;
    @JsonProperty("arrival_date")
    private String arrivalDate = "";
    @JsonProperty("leave_date")
    private String leaveDate = "";
    @JsonProperty("departure_flight")
    private int departure_flight = 0;
    @JsonProperty("return_flight")
    private int return_flight = 0;
    @JsonProperty("hotelid")
    private int hotelid = 0;
    @JsonProperty("budget")
    private int budget = 0;
    @JsonProperty("spot")
    private ArrayList<AddTouristSpotRequest> spots = new ArrayList<AddTouristSpotRequest>();

    public AddProposalRequest(int proposalid, int submitid, int reviewid, int destination, String arrivalDate,
                              String leaveDate, int departure_flight, int return_flight, int hotelid, int budget,
                              ArrayList<AddTouristSpotRequest> spots){
        this.proposalid = proposalid;
        this.submitid = submitid;
        this.reviewid = reviewid;
        this.destination = destination;
        this.arrivalDate = arrivalDate;
        this.leaveDate = leaveDate;
        this.departure_flight = departure_flight;
        this.return_flight = return_flight;
        this.hotelid = hotelid;
        this.budget = budget;
        this.spots = spots;
    }

    public int getDestination() {
        return destination;
    }

    public int getBudget() {
        return budget;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public ArrayList<AddTouristSpotRequest> getSpots() {
        return spots;
    }

    public int getDeparture_flight() {
        return departure_flight;
    }

    public int getHotelid() {
        return hotelid;
    }

    public int getProposalid() {
        return proposalid;
    }

    public int getReturn_flight() {
        return return_flight;
    }

    public int getReviewid() {
        return reviewid;
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public int getSubmitid() {
        return submitid;
    }
}
