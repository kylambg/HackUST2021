package handler;

import city.CitySearchRequest;
import city.CitySearchResponse;
import city.CityService;

import exception.InternalServerException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NByteArrayEntity;
import org.jetbrains.annotations.Nullable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;

public class CityHandler extends ServerRequestHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectWriter objectWriter = objectMapper.writer();

    @Override
    public void handle(String httpMethod, String path, Map<String, String> param, @Nullable InputStream request, HttpResponse response) throws InternalServerException{
        if (!httpMethod.equalsIgnoreCase("GET")) {
            response.setStatusCode(HttpStatus.SC_NOT_FOUND);
            return;
        }
        try{
            CitySearchRequest.Builder builder = new CitySearchRequest.Builder();
            if (param.containsKey("countryid")){
                builder.countryid(Integer.parseInt(param.get("countryid")));
            }
            if (param.containsKey("name")) {
                builder.cityName(param.get("name"));
            }
            CitySearchResponse searchResponse = CityService.getInstance().searchCity(builder.build());
            if (searchResponse.getTotalNumCity() == 0){
                response.setStatusCode(HttpStatus.SC_NO_CONTENT);
            } else {
                response.setStatusCode(HttpStatus.SC_OK);
                response.setEntity(new NByteArrayEntity(objectWriter.writeValueAsBytes(searchResponse), ContentType.create("application/json", "UTF-8")));
            }
        } catch (SQLException | JsonProcessingException e) {
            throw new InternalServerException(e);
        } catch (NumberFormatException e){
            response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
        }
    }
}
