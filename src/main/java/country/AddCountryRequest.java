package country;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddCountryRequest {
    @JsonProperty("countryid")
    private String id;
    @JsonProperty("countryname")
    private String name;

    public AddCountryRequest() {
        this.id = "";
        this.name = "";
    }

    public AddCountryRequest(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
