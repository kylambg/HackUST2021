package quarantineinfo;

public class QuarantineSearchRequest {
    private boolean searchByCountryID = false;

    private int countryid;

    private QuarantineSearchRequest(){}

    public boolean isSearchByCountryID() {
        return searchByCountryID;
    }

    public int getCountryid() {
        return countryid;
    }

    public static class Builder {
        final QuarantineSearchRequest instance;

        public Builder() { instance = new QuarantineSearchRequest();}

        public QuarantineSearchRequest build() { return instance; }

        public Builder countryid(int countryid){
            instance.searchByCountryID = true;
            instance.countryid = countryid;
            return this;
        }

    }
}
