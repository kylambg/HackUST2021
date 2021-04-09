package city;

public class CitySearchRequest {
    private boolean searchByCountryID = false;
    private boolean searchByName = false;

    private int countryid;
    private String cityName;

    private CitySearchRequest(){}

    public boolean isSearchByCountryID() {
        return searchByCountryID;
    }

    public boolean isSearchByName() {
        return searchByName;
    }

    public int getCountryid() {
        return countryid;
    }

    public String getCityName() {
        return cityName;
    }

    public static class Builder {
        final CitySearchRequest instance;

        public Builder() { instance = new CitySearchRequest();}

        public CitySearchRequest build() { return instance; }

        public Builder countryid(int countryid){
            instance.searchByCountryID = true;
            instance.countryid = countryid;
            return this;
        }

        public Builder cityName(String name){
            instance.searchByName = true;
            instance.cityName = name;
            return this;
        }
    }
}
