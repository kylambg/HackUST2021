package hotel;

public class HotelSearchRequest {
    private boolean searchByCityID = false;
    private boolean searchByName = false;
    private boolean sorted = false;
    private boolean sortReversed = false;

    private int cityID;
    private String hotelName;
    private int price;
    private int star;

    private SortType sortType;

    public enum SortType {
        BY_CITYID,
        BY_PRICE,
        BY_STAR,
    }

    public HotelSearchRequest(){}

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

    public String getHotelName() {
        return hotelName;
    }

    public int getPrice() {
        return price;
    }

    public SortType getSortType() {
        return sortType;
    }

    public static class Builder {
        final HotelSearchRequest instance;

        public Builder() { instance = new HotelSearchRequest();}

        public HotelSearchRequest build() { return instance; }

        public Builder cityID(int cityID){
            instance.searchByCityID = true;
            instance.cityID = cityID;
            return this;
        }

        public Builder cityName(String name){
            instance.searchByName = true;
            instance.hotelName = name;
            return this;
        }

        public Builder sortByCityID(){
            instance.sorted = true;
            instance.sortType = SortType.BY_CITYID;
            return this;
        }

        public Builder sortByPrice(){
            instance.sorted = true;
            instance.sortType = SortType.BY_PRICE;
            return this;
        }
        public Builder sortByStar(){
            instance.sorted = true;
            instance.sortType = SortType.BY_STAR;
            return this;
        }

        public Builder reverseSort(){
            instance.sortReversed = true;
            return this;
        }
    }
}
