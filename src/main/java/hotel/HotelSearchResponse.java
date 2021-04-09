package hotel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class HotelSearchResponse {
    @JsonProperty("foundHotel")
    private final int totalNumHotel;

    @JsonProperty("Hotel")
    private final List<AddHotelRequest> searchResults;

    public HotelSearchResponse() {
        totalNumHotel = 0;
        searchResults = new ArrayList<>();
    }

    private HotelSearchResponse(int totalNumHotel, List<AddHotelRequest> searchResults) {
        this.totalNumHotel = totalNumHotel;
        this.searchResults = searchResults;
    }

    public int getTotalNumHotel() {
        return totalNumHotel;
    }

    public List<AddHotelRequest> getSearchResults() {
        return searchResults;
    }

    public static class Builder {
        private final List<AddHotelRequest> list;

        public Builder() {
            list = new ArrayList<>();
        }

        public Builder addSpot(AddHotelRequest hotel) {
            list.add(hotel);
            return this;
        }

        public HotelSearchResponse build() {
            return new HotelSearchResponse(list.size(), list);
        }


    }
}
