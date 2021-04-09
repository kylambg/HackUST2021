package touristSpot;

import database.DatabaseCity;
import database.DatabaseTouristSpot;

import java.sql.SQLException;

public class TouristSpotService {

    private static final TouristSpotService instance = new TouristSpotService();

    public static TouristSpotService getInstance() {
        return instance;
    }

    public TouristSpotSearchResponse searchCity(TouristSpotSearchRequest request) throws SQLException {
        StringBuilder searchSql = new StringBuilder();
        searchSql.append("SELECT * FROM tourist_spot");
        if(request.isSearchByCityID() || request.isSearchByName()){
            boolean multiStatement = false;
            searchSql.append(" WHERE");
            if(request.isSearchByCityID()){
                searchSql.append(" cityid=?");
                multiStatement = true;
            }
            if(request.isSearchByName()){
                if(multiStatement){
                    searchSql.append(" AND");
                }
                searchSql.append(" INSTR(name, ?) > 0");
                multiStatement = true;
            }
        }
        if (request.isSorted()){
            searchSql.append(" ORDER BY");
            switch (request.getSortType()) {
                case BY_CHARGE:
                    searchSql.append(" charge");
                    break;
                case BY_CITYID:
                    searchSql.append(" cityid");
                    break;
            }
            if (request.isSortReversed()) {
                searchSql.append(" DESC");
            }
        }
        TouristSpotSearchResponse.Builder responseBuilder = new TouristSpotSearchResponse.Builder();
        DatabaseTouristSpot.searchSpots(request, searchSql, responseBuilder);
        return  responseBuilder.build();
    }
}
