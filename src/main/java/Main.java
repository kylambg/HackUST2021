import org.apache.http.ExceptionLogger;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.impl.nio.bootstrap.HttpServer;
import org.apache.http.impl.nio.bootstrap.ServerBootstrap;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.protocol.HttpAsyncRequestHandler;
import org.apache.http.protocol.*;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import handler.*;


public class Main {
    private static final int LISTEN_PORT = 8080;

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Server starting...");

        HttpProcessor httpproc = HttpProcessorBuilder.create()
                .add(new ResponseDate())
                .add(new ResponseServer("MyServer-HTTP/1.1"))
                .add(new ResponseContent())
                .add(new ResponseConnControl())
                .build();

        HttpAsyncRequestHandler<?> loginHandler = new LoginRequestHandler();
        HttpAsyncRequestHandler<?> logoutHandler = new LogoutRequestHandler();
        HttpAsyncRequestHandler<?> countryHandler = new CountryHandler();
        HttpAsyncRequestHandler<?> cityHandler = new CityHandler();
        HttpAsyncRequestHandler<?> touristSpotHandler = new TouristSpotHandler();
        HttpAsyncRequestHandler<?> hotelHandler = new HotelHandler();
        HttpAsyncRequestHandler<?> infoHandler = new InfoHandler();
        HttpAsyncRequestHandler<?> quarantineInfoHandler = new QuarantineInfoHandler();
        HttpAsyncRequestHandler<?> flightHandler = new FlightHandler();

        HttpAsyncRequestHandler<?> defaultHandler = new ServerRequestHandler() {
            @Override
            public void handle(String httpMethod, String path, Map<String, String> param, @Nullable InputStream requestBody, HttpResponse response) {
                System.out.println("Invalid URL");
                response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
            }
        };

        IOReactorConfig socketConfig = IOReactorConfig.custom()
                .setSoTimeout(15000)
                .setTcpNoDelay(true)
                .build();
        final HttpServer server = ServerBootstrap.bootstrap()
                .setListenerPort(LISTEN_PORT)
                .setHttpProcessor(httpproc)
                .setIOReactorConfig(socketConfig)
                .registerHandler("/login", loginHandler)
                .registerHandler("/logout", logoutHandler)
                .registerHandler("/country", countryHandler)
                .registerHandler("/city", cityHandler)
                .registerHandler("/tourist_spot", touristSpotHandler)
                .registerHandler("/hotel", hotelHandler)
                .registerHandler("/info", infoHandler)
                .registerHandler("/quarantine_info", quarantineInfoHandler)
                .registerHandler("/flight", flightHandler)
                //.registerHandler("/BookManagementService/books", authBookAddSearchHandler)
                //.registerHandler("/BookManagementService/books/*", authBookRequestHandler)
                //.registerHandler("/BookManagementService/transaction", authTransactionHandler)
                .registerHandler("*", defaultHandler)
                .setExceptionLogger(ExceptionLogger.STD_ERR)
                .create();

        server.start();

        System.out.println("Server listening on port " + LISTEN_PORT + "...");

        System.out.println("Server Running...");

        server.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> server.shutdown(1, TimeUnit.SECONDS)));
    }
}
