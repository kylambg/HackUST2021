package flight;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class FlightSearchResponse {
    @JsonProperty("foundFlight")
    private final int totalNumFlight;

    @JsonProperty("flight")
    private final List<AddFlightRequest> searchResults;

    public FlightSearchResponse() {
        totalNumFlight = 0;
        searchResults = new ArrayList<>();
    }

    private FlightSearchResponse(int totalNumFlight, List<AddFlightRequest> searchResults) {
        this.totalNumFlight = totalNumFlight;
        this.searchResults = searchResults;
    }

    public int getTotalNumFlight() {
        return totalNumFlight;
    }

    public List<AddFlightRequest> getSearchResults() {
        return searchResults;
    }

    public static class Builder {
        private final List<AddFlightRequest> list;

        public Builder() {
            list = new ArrayList<>();
        }

        public Builder addFlight(AddFlightRequest hotel) {
            list.add(hotel);
            return this;
        }

        public FlightSearchResponse build() {
            return new FlightSearchResponse(list.size(), list);
        }


    }
}
