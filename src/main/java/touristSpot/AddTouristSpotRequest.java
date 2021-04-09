package touristSpot;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddTouristSpotRequest {
    @JsonProperty("tourist_spot_id")
    private String touristSpotID;
    @JsonProperty("cityid")
    private String cityID;
    @JsonProperty("name")
    private String name;
    @JsonProperty("charge")
    private int charge;

    public AddTouristSpotRequest() {
        this.touristSpotID = "";
        this.cityID = "";
        this.name = "";
        this.charge = 0;
    }

    public AddTouristSpotRequest(String touristSpotID, String cityID, String name, int charge) {
        this.touristSpotID = touristSpotID;
        this.cityID = cityID;
        this.name = name;
        this.charge = charge;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCharge() {
        return charge;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getTouristSpotID() {
        return touristSpotID;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public void setTouristSpotID(String touristSpotID) {
        this.touristSpotID = touristSpotID;
    }
}
