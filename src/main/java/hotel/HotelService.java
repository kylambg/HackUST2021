package hotel;

import database.DatabaseHotel;

import java.sql.SQLException;

public class HotelService {

    private static final HotelService instance = new HotelService();

    public static HotelService getInstance() {
        return instance;
    }

    public HotelSearchResponse searchHotel(HotelSearchRequest request) throws SQLException {
        StringBuilder searchSql = new StringBuilder();
        searchSql.append("SELECT * FROM hotels");
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
                case BY_PRICE:
                    searchSql.append(" price");
                    break;
                case BY_CITYID:
                    searchSql.append(" cityid");
                    break;
                case BY_STAR:
                    searchSql.append(" star");
                    break;
            }
            if (request.isSortReversed()) {
                searchSql.append(" DESC");
            }
        }
        System.out.println(searchSql);
        HotelSearchResponse.Builder responseBuilder = new HotelSearchResponse.Builder();
        DatabaseHotel.searchHotels(request, searchSql, responseBuilder);
        return  responseBuilder.build();
    }
}
