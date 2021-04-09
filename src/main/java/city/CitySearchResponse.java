package city;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CitySearchResponse {
    @JsonProperty("foundCity")
    private final int totalNumCity;

    @JsonProperty("Country")
    private final List<AddCityRequest> searchResults;

    public CitySearchResponse() {
        totalNumCity = 0;
        searchResults = new ArrayList<>();
    }

    private CitySearchResponse(int totalNumCountry, List<AddCityRequest> searchResults) {
        this.totalNumCity = totalNumCountry;
        this.searchResults = searchResults;
    }

    public int getTotalNumCity() {
        return totalNumCity;
    }

    public List<AddCityRequest> getSearchResults() {
        return searchResults;
    }

    public static class Builder {
        private final List<AddCityRequest> list;

        public Builder() {
            list = new ArrayList<>();
        }

        public Builder addCountry(AddCityRequest city) {
            list.add(city);
            return this;
        }

        public CitySearchResponse build() {
            return new CitySearchResponse(list.size(), list);
        }


    }
}
