package handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import exception.InternalServerException;
import flight.FlightSearchRequest;
import flight.FlightSearchResponse;
import flight.FlightService;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NByteArrayEntity;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;

public class FlightHandler extends ServerRequestHandler{
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectWriter objectWriter = objectMapper.writer();

    @Override
    public void handle(String httpMethod, String path, Map<String, String> param, @Nullable InputStream request, HttpResponse response) throws InternalServerException {
        if (!httpMethod.equalsIgnoreCase("GET")) {
            response.setStatusCode(HttpStatus.SC_NOT_FOUND);
            return;
        }
        try{
            FlightSearchRequest.Builder builder = new FlightSearchRequest.Builder();
            if (param.containsKey("destination")){
                builder.destination(Integer.parseInt(param.get("destination")));
            }
            if (param.containsKey("departure")) {
                builder.departure(Integer.parseInt(param.get("departure")));
            }
            if (param.containsKey("departure_date")) {
                builder.departureDate(param.get("departure_date"));
            }
            if (param.containsKey("company")) {
                builder.company(param.get("company"));
            }
            if (param.containsKey("sortby")) {
                String sortby = param.get("sortby");
                if (sortby.equals("destination")) {
                    builder.sortByDestination();
                }
                if (sortby.equals("departure")) {
                    builder.sortByDeparture();
                }
                if (sortby.equals("company")) {
                    builder.sortByCompany();
                }
                if (sortby.equals("departure_date")){
                    builder.sortByDepartureDate();
                }
                if (sortby.equals("departure_time")){
                    builder.sortByDepartureTime();
                }
                if (sortby.equals("arrival_date")){
                    builder.sortByArrivalDate();
                }
                if (sortby.equals("arrival_time")){
                    builder.sortByArrivalTime();
                }
                if (sortby.equals("price")){
                    builder.sortByPrice();
                }
            }
            if (param.containsKey("order") && param.get("order").equals("desc")) {
                builder.reverseSort();
            }

            FlightSearchResponse searchResponse = FlightService.getInstance().searchFlight(builder.build());
            if (searchResponse.getTotalNumFlight() == 0){
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
