package handler;

import touristSpot.TouristSpotSearchRequest;
import touristSpot.TouristSpotSearchResponse;
import touristSpot.TouristSpotService;

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

public class TouristSpotHandler extends ServerRequestHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectWriter objectWriter = objectMapper.writer();

    @Override
    public void handle(String httpMethod, String path, Map<String, String> param, @Nullable InputStream request, HttpResponse response) throws InternalServerException{
        if (!httpMethod.equalsIgnoreCase("GET")) {
            response.setStatusCode(HttpStatus.SC_NOT_FOUND);
            return;
        }
        try{
            TouristSpotSearchRequest.Builder builder = new TouristSpotSearchRequest.Builder();
            if (param.containsKey("cityid")){
                builder.cityID(Integer.parseInt(param.get("cityid")));
            }
            if (param.containsKey("name")) {
                builder.cityName(param.get("name"));
            }
            if (param.containsKey("sortby")) {
                String sortby = param.get("sortby");
                if (sortby.equals("cityid")) {
                    builder.sortByCityID();
                }
                if (sortby.equals("charge")) {
                    builder.sortByCharge();
                }
            }
            if (param.containsKey("order") && param.get("order").equals("desc")) {
                builder.reverseSort();
            }
            TouristSpotSearchResponse searchResponse = TouristSpotService.getInstance().searchCity(builder.build());
            if (searchResponse.gettotalNumSpot() == 0){
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
