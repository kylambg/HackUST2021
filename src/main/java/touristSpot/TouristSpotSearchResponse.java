package touristSpot;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class TouristSpotSearchResponse {
    @JsonProperty("foundTouristSpot")
    private final int totalNumSpot;

    @JsonProperty("TouristSpot")
    private final List<AddTouristSpotRequest> searchResults;

    public TouristSpotSearchResponse() {
        totalNumSpot = 0;
        searchResults = new ArrayList<>();
    }

    private TouristSpotSearchResponse(int totalNumSpot, List<AddTouristSpotRequest> searchResults) {
        this.totalNumSpot = totalNumSpot;
        this.searchResults = searchResults;
    }

    public int gettotalNumSpot() {
        return totalNumSpot;
    }

    public List<AddTouristSpotRequest> getSearchResults() {
        return searchResults;
    }

    public static class Builder {
        private final List<AddTouristSpotRequest> list;

        public Builder() {
            list = new ArrayList<>();
        }

        public Builder addSpot(AddTouristSpotRequest spot) {
            list.add(spot);
            return this;
        }

        public TouristSpotSearchResponse build() {
            return new TouristSpotSearchResponse(list.size(), list);
        }


    }
}
