package quarantineinfo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class QuarantineSearchResponse {
    @JsonProperty("foundinfo")
    private final int totalNumInfo;

    @JsonProperty("info")
    private final List<AddQuarantineRequest> searchResults;

    public QuarantineSearchResponse() {
        totalNumInfo = 0;
        searchResults = new ArrayList<>();
    }

    private QuarantineSearchResponse(int totalNumInfo, List<AddQuarantineRequest> searchResults) {
        this.totalNumInfo = totalNumInfo;
        this.searchResults = searchResults;
    }

    public int getTotalNumInfo() {
        return totalNumInfo;
    }

    public List<AddQuarantineRequest> getSearchResults() {
        return searchResults;
    }

    public static class Builder {
        private final List<AddQuarantineRequest> list;

        public Builder() {
            list = new ArrayList<>();
        }

        public Builder addInfo(AddQuarantineRequest info) {
            list.add(info);
            return this;
        }

        public QuarantineSearchResponse build() {
            return new QuarantineSearchResponse(list.size(), list);
        }


    }
}
