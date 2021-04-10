package flight;

import database.DatabaseFlight;
import database.DatabaseHotel;

import java.sql.SQLException;

public class FlightService {

    private static final FlightService instance = new FlightService();

    public static FlightService getInstance() {
        return instance;
    }

    public FlightSearchResponse searchFlight(FlightSearchRequest request) throws SQLException {
        StringBuilder searchSql = new StringBuilder();
        searchSql.append("SELECT * FROM flight");
        if(request.isSearchByDeparture() || request.isSearchByDepartureDate() || request.isSearchByDestination() || request.isSearchByCompany()){
            boolean multiStatement = false;
            searchSql.append(" WHERE");
            if(request.isSearchByDeparture()){
                searchSql.append(" departure=?");
                multiStatement = true;
            }
            if(request.isSearchByDepartureDate()){
                if(multiStatement){
                    searchSql.append(" AND");
                }
                searchSql.append(" INSTR(departure_date, ?) > 0");
                multiStatement = true;
            }
            if(request.isSearchByDestination()){
                if(multiStatement){
                    searchSql.append(" AND");
                }
                searchSql.append(" destination=?");
                multiStatement = true;
            }
            if(request.isSearchByCompany()){
                if(multiStatement){
                    searchSql.append(" AND");
                }
                searchSql.append(" company=?");
                multiStatement = true;
            }
        }
        if (request.isSorted()){
            searchSql.append(" ORDER BY");
            switch (request.getSortType()) {
                case BY_PRICE:
                    searchSql.append(" price");
                    break;
                case BY_ARRIVALTIME:
                    searchSql.append(" arrival_time");
                    break;
                case BY_ARRIVALDATE:
                    searchSql.append(" arrival_date");
                    break;
                case BY_DEPARTURETIME:
                    searchSql.append(" departure_time");
                    break;
                case BY_DEPARTUREDATE:
                    searchSql.append(" departure_date");
                    break;
                case BY_DEPARTURE:
                    searchSql.append(" departure");
                    break;
                case BY_DESTINATION:
                    searchSql.append(" destination");
                    break;
                case BY_COMPANY:
                    searchSql.append(" company");
                    break;
            }
            if (request.isSortReversed()) {
                searchSql.append(" DESC");
            }
        }
        System.out.println(searchSql);
        FlightSearchResponse.Builder responseBuilder = new FlightSearchResponse.Builder();
        DatabaseFlight.searchFlights(request, searchSql, responseBuilder);
        return  responseBuilder.build();
    }
}
