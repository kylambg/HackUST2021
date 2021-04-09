package hotel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddHotelRequest {
    @JsonProperty("hotelid")
    private int touristSpotID;
    @JsonProperty("cityid")
    private int cityID;
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private int price;
    @JsonProperty("star")
    private int star;

    public AddHotelRequest() {
        this.touristSpotID = 0;
        this.cityID = 0;
        this.name = "";
        this.price = 0;
        this.star = 0;
    }

    public AddHotelRequest(int hotelID, int cityID, String name, int price, int star) {
        this.touristSpotID = hotelID;
        this.cityID = cityID;
        this.name = name;
        this.price = price;
        this.star = star;
    }

    public int getCityID() {
        return cityID;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getStar() {
        return star;
    }

    public int getTouristSpotID() {
        return touristSpotID;
    }

    public void setTouristSpotID(int touristSpotID) {
        this.touristSpotID = touristSpotID;
    }

    public void setCharge(int price) {
        this.price = price;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStar(int star) {
        this.star = star;
    }
}
