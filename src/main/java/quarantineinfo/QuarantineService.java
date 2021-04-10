package quarantineinfo;

import database.DatabaseQuarantineInfo;

import java.sql.SQLException;

public class QuarantineService {

    private static final QuarantineService instance = new QuarantineService();

    public static QuarantineService getInstance() {
        return instance;
    }

    public QuarantineSearchResponse searchCity(QuarantineSearchRequest request) throws SQLException {
        StringBuilder searchSql = new StringBuilder();
        searchSql.append("SELECT * FROM quarantine_info");
        if(request.isSearchByCountryID()){
            boolean multiStatement = false;
            searchSql.append(" WHERE");
            if(request.isSearchByCountryID()){
                searchSql.append(" countryid=?");
                multiStatement = true;
            }
        }
        QuarantineSearchResponse.Builder responseBuilder = new QuarantineSearchResponse.Builder();
        DatabaseQuarantineInfo.searchInfo(request, searchSql, responseBuilder);
        return  responseBuilder.build();
    }
}
