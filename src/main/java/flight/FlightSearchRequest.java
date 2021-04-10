package flight;

public class FlightSearchRequest {
    private boolean searchByDeparture = false;
    private boolean searchByDestination = false;
    private boolean searchByCompany = false;
    private boolean isSearchByDepartureDate = false;

    private boolean sorted = false;
    private boolean sortReversed = false;

    private int departure;
    private int destination;
    private String company;
    private String departureDate;

    private SortType sortType;

    public enum SortType {
        BY_DESTINATION,
        BY_DEPARTURE,
        BY_COMPANY,
        BY_DEPARTUREDATE,
        BY_DEPARTURETIME,
        BY_ARRIVALDATE,
        BY_ARRIVALTIME,
        BY_PRICE
    }

    public FlightSearchRequest(){}

    public String getDepartureDate() {
        return departureDate;
    }

    public String getCompany() {
        return company;
    }

    public int getDestination() {
        return destination;
    }

    public int getDeparture() {
        return departure;
    }

    public boolean isSortReversed() {
        return sortReversed;
    }

    public boolean isSorted() {
        return sorted;
    }

    public boolean isSearchByDeparture() {
        return searchByDeparture;
    }

    public boolean isSearchByDepartureDate() {
        return isSearchByDepartureDate;
    }

    public boolean isSearchByDestination() {
        return searchByDestination;
    }

    public boolean isSearchByCompany() {
        return searchByCompany;
    }

    public SortType getSortType() {
        return sortType;
    }

    public static class Builder {
        final FlightSearchRequest instance;

        public Builder() { instance = new FlightSearchRequest();}

        public FlightSearchRequest build() { return instance; }

        public Builder departure(int departure){
            instance.searchByDeparture = true;
            instance.departure = departure;
            return this;
        }

        public Builder destination(int destination){
            instance.searchByDestination = true;
            instance.destination = destination;
            return this;
        }

        public Builder company(String company){
            instance.searchByCompany = true;
            instance.company = company;
            return this;
        }

        public Builder departureDate(String departureDate){
            instance.isSearchByDepartureDate = true;
            instance.departureDate = departureDate;
            return this;
        }

        public Builder sortByDestination(){
            instance.sorted = true;
            instance.sortType = SortType.BY_DESTINATION;
            return this;
        }

        public Builder sortByCompany(){
            instance.sorted = true;
            instance.sortType = SortType.BY_COMPANY;
            return this;
        }

        public Builder sortByDeparture(){
            instance.sorted = true;
            instance.sortType = SortType.BY_DEPARTURE;
            return this;
        }
        public Builder sortByDepartureDate(){
            instance.sorted = true;
            instance.sortType = SortType.BY_DEPARTUREDATE;
            return this;
        }

        public Builder sortByDepartureTime(){
            instance.sorted = true;
            instance.sortType = SortType.BY_DEPARTURETIME;
            return this;
        }
        public Builder sortByArrivalDate(){
            instance.sorted = true;
            instance.sortType = SortType.BY_ARRIVALDATE;
            return this;
        }
        public Builder sortByArrivalTime(){
            instance.sorted = true;
            instance.sortType = SortType.BY_ARRIVALTIME;
            return this;
        }
        public Builder sortByPrice(){
            instance.sorted = true;
            instance.sortType = SortType.BY_PRICE;
            return this;
        }

        public Builder reverseSort(){
            instance.sortReversed = true;
            return this;
        }
    }
}
