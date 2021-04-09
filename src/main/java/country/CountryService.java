package country;

import com.fasterxml.jackson.annotation.JsonProperty;
import database.ConnectionManager;
import database.DatabaseCountry;
import exception.InternalServerException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CountryService {

    private static final CountryService instance = new CountryService();

    public static CountryService getInstance() {
        return instance;
    }

    public CountrySearchResponse searchCountry() throws SQLException {
        CountrySearchResponse.Builder responseBuilder = new CountrySearchResponse.Builder();
        DatabaseCountry.getCountries(responseBuilder);
        return  responseBuilder.build();
    }
}
