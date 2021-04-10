package covidinfo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddCovidInfoRequest {
    @JsonProperty("infoid")
    private int infoID;
    @JsonProperty("countryid")
    private int countryID;
    @JsonProperty("info")
    private String info;

    public AddCovidInfoRequest() {
        this.infoID = 0;
        this.countryID=0;
        this.info = "";
    }

    public AddCovidInfoRequest(int infoID, int countryID, String info) {
        this.infoID = infoID;
        this.countryID = countryID;
        this.info = info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public void setInfoID(int infoID) {
        this.infoID = infoID;
    }

    public int getInfoID() {
        return infoID;
    }

    public int getCountryID() {
        return countryID;
    }

    public String getInfo() {
        return info;
    }
}
