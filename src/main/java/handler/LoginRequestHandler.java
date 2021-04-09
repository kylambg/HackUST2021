package handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import auth.AuthService;
import auth.LoginRequest;
import auth.LoginResponse;
import exception.BadCredentialsException;
import exception.DuplicateLoginException;
import exception.InternalServerException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NByteArrayEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class LoginRequestHandler extends JsonRequestHandler<LoginRequest> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectWriter objectWriter = objectMapper.writer();

    public LoginRequestHandler() {
        super(LoginRequest.class);
    }

    @Override
    public void handleJson(String httpMethod, String path, Map<String, String> param, @NotNull LoginRequest requestBody, HttpResponse response) throws InternalServerException {
        if (!httpMethod.equalsIgnoreCase("POST")) {
            response.setStatusCode(HttpStatus.SC_NOT_FOUND);
            return;
        }
        try {
            String token = AuthService.getInstance().login(requestBody);
            LoginResponse loginResponse = new LoginResponse(token);
            response.setEntity(new NByteArrayEntity(objectWriter.writeValueAsBytes(loginResponse), ContentType.create("application/json", "UTF-8")));
            response.setStatusCode(HttpStatus.SC_OK);
        } catch (BadCredentialsException e) {
            response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
        } catch (DuplicateLoginException e) {
            response.setStatusCode(HttpStatus.SC_CONFLICT);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(e);
        }
    }
}
