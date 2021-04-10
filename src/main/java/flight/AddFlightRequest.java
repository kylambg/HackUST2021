package flight;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddFlightRequest {
    int flightid;
    int destination;
    int departure;
    String company;
    int code;
    String departureDate;
    String departureTime;
    String arrivalDate;
    String arrivalTime;
    int price;


    public AddFlightRequest() {
        flightid = 0;
        destination = 0;
        departure = 0;
        company = "";
        code = 0;
        departureDate = "";
        departureTime = "";
        arrivalDate = "";
        arrivalTime = "";
        price = 0;
    }

    public AddFlightRequest(int id, int destination,int departure,String company, int code, String departureDate,String departureTime, String arrivalDate, String arrivalTime, int price) {
        this.flightid = id;
        this.departure = departure;
        this.destination = destination;
        this.company = company;
        this.code = code;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.price = price;
    }

    public int getCode() {
        return code;
    }

    public int getDeparture() {
        return departure;
    }

    public int getDestination() {
        return destination;
    }

    public int getFlightid() {
        return flightid;
    }

    public int getPrice() {
        return price;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getCompany() {
        return company;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDeparture(int departure) {
        this.departure = departure;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public void setFlightid(int flightid) {
        this.flightid = flightid;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
