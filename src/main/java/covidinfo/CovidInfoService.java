package covidinfo;

import database.DatabaseInfo;

import java.sql.SQLException;

public class CovidInfoService {

    private static final CovidInfoService instance = new CovidInfoService();

    public static CovidInfoService getInstance() {
        return instance;
    }

    public CovidInfoSearchResponse searchCountry() throws SQLException {
        CovidInfoSearchResponse.Builder responseBuilder = new CovidInfoSearchResponse.Builder();
        DatabaseInfo.getInfo(responseBuilder);
        return  responseBuilder.build();
    }
}
