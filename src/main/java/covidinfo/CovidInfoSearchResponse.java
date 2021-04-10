package covidinfo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CovidInfoSearchResponse {
    @JsonProperty("foundInfo")
    private final int totalNumInfo;

    @JsonProperty("info")
    private final List<AddCovidInfoRequest> searchResults;

    public CovidInfoSearchResponse() {
        totalNumInfo = 0;
        searchResults = new ArrayList<>();
    }

    private CovidInfoSearchResponse(int totalNumInfo, List<AddCovidInfoRequest> searchResults) {
        this.totalNumInfo = totalNumInfo;
        this.searchResults = searchResults;
    }

    public int getTotalNumInfo() {
        return totalNumInfo;
    }

    public List<AddCovidInfoRequest> getSearchResults() {
        return searchResults;
    }

    public static class Builder {
        private final List<AddCovidInfoRequest> list;

        public Builder() {
            list = new ArrayList<>();
        }

        public Builder addInfo(AddCovidInfoRequest info) {
            list.add(info);
            return this;
        }

        public CovidInfoSearchResponse build() {
            return new CovidInfoSearchResponse(list.size(), list);
        }


    }
}
