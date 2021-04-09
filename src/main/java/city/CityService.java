package city;

import database.DatabaseCity;
import database.DatabaseCountry;

import java.sql.SQLException;

public class CityService {

    private static final CityService instance = new CityService();

    public static CityService getInstance() {
        return instance;
    }

    public CitySearchResponse searchCity(CitySearchRequest request) throws SQLException {
        StringBuilder searchSql = new StringBuilder();
        searchSql.append("SELECT * FROM city");
        if(request.isSearchByCountryID() || request.isSearchByName()){
            boolean multiStatement = false;
            searchSql.append(" WHERE");
            if(request.isSearchByCountryID()){
                searchSql.append(" countryid=?");
                multiStatement = true;
            }
            if(request.isSearchByName()){
                if(multiStatement){
                    searchSql.append(" AND");
                }
                searchSql.append(" name=?");
                multiStatement = true;
            }
        }
        CitySearchResponse.Builder responseBuilder = new CitySearchResponse.Builder();
        DatabaseCity.searchCities(request, searchSql, responseBuilder);
        return  responseBuilder.build();
    }
}
