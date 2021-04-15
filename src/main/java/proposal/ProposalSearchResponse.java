package proposal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ProposalSearchResponse {
    @JsonProperty("FoundProposal")
    private final int totalNumProposal;

    @JsonProperty("Results")
    private final List<AddProposalRequest> searchResults;

    public ProposalSearchResponse() {
        totalNumProposal = 0;
        searchResults = new ArrayList<>();
    }

    private ProposalSearchResponse(int totalNumProposal, List<AddProposalRequest> searchResults) {
        this.totalNumProposal = totalNumProposal;
        this.searchResults = searchResults;
    }

    public int getTotalNumBooks() {
        return totalNumProposal;
    }

    public List<AddProposalRequest> getSearchResults() {
        return searchResults;
    }

    public static class Builder {
        private final List<AddProposalRequest> list;

        public Builder() {
            list = new ArrayList<>();
        }

        public Builder addProposal(AddProposalRequest proposal) {
            list.add(proposal);
            return this;
        }

        public ProposalSearchResponse build() {
            return new ProposalSearchResponse(list.size(), list);
        }


    }
}
