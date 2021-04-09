package country;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CountrySearchResponse {
    @JsonProperty("foundCountry")
    private final int totalNumCountry;

    @JsonProperty("Country")
    private final List<AddCountryRequest> searchResults;

    public CountrySearchResponse() {
        totalNumCountry = 0;
        searchResults = new ArrayList<>();
    }

    private CountrySearchResponse(int totalNumCountry, List<AddCountryRequest> searchResults) {
        this.totalNumCountry = totalNumCountry;
        this.searchResults = searchResults;
    }

    public int getTotalNumCountry() {
        return totalNumCountry;
    }

    public List<AddCountryRequest> getSearchResults() {
        return searchResults;
    }

    public static class Builder {
        private final List<AddCountryRequest> list;

        public Builder() {
            list = new ArrayList<>();
        }

        public Builder addCountry(AddCountryRequest country) {
            list.add(country);
            return this;
        }

        public CountrySearchResponse build() {
            return new CountrySearchResponse(list.size(), list);
        }


    }
}
