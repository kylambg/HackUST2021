package touristSpot;

public class TouristSpotSearchRequest {
    private boolean searchByCityID = false;
    private boolean searchByName = false;
    private boolean sorted = false;
    private boolean sortReversed = false;
    private boolean searchBySpotID = false;

    private int spotID;
    private int cityID;
    private String spotName;
    private int charge;

    private SortType sortType;

    public enum SortType {
        BY_CITYID,
        BY_CHARGE,
        BY_SPOTID
    }

    public TouristSpotSearchRequest(){}

    public boolean isSearchByName() {
        return searchByName;
    }

    public boolean isSearchByCityID() {
        return searchByCityID;
    }

    public boolean isSorted() {
        return sorted;
    }

    public boolean isSortReversed() {
        return sortReversed;
    }

    public int getCityID() {
        return cityID;
    }

    public String getSpotName() {
        return spotName;
    }

    public int getCharge() {
        return charge;
    }

    public boolean isSearchBySpotID() {
        return searchBySpotID;
    }

    public int getSpotID() {
        return spotID;
    }

    public SortType getSortType() {
        return sortType;
    }

    public static class Builder {
        final TouristSpotSearchRequest instance;

        public Builder() { instance = new TouristSpotSearchRequest();}

        public TouristSpotSearchRequest build() { return instance; }

        public Builder cityID(int cityID){
            instance.searchByCityID = true;
            instance.cityID = cityID;
            return this;
        }

        public Builder cityName(String name){
            instance.searchByName = true;
            instance.spotName = name;
            return this;
        }

        public Builder touristSpotID(int spotID){
            instance.searchBySpotID = true;
            instance.spotID = spotID;
            return this;
        }

        public Builder sortByCityID(){
            instance.sorted = true;
            instance.sortType = SortType.BY_CITYID;
            return this;
        }

        public Builder sortByCharge(){
            instance.sorted = true;
            instance.sortType = SortType.BY_CHARGE;
            return this;
        }

        public Builder reverseSort(){
            instance.sortReversed = true;
            return this;
        }
    }
}
