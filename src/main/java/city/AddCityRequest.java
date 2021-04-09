package city;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddCityRequest {
    @JsonProperty("cityid")
    private String cityid;
    @JsonProperty("countryid")
    private String countryid;
    @JsonProperty("cityname")
    private String name;

    public AddCityRequest() {
        this.cityid = "";
        this.countryid = "";
        this.name = "";
    }

    public AddCityRequest(String cityid, String countryid, String name) {
        this.cityid = cityid;
        this.countryid = countryid;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public void setCountryid(String countryid) {
        this.countryid = countryid;
    }

    public String getName() {
        return name;
    }

    public String getCityid() {
        return cityid;
    }

    public String getCountryid() {
        return countryid;
    }
}
