package org.example;

import org.example.controller.Controller;
import org.example.repository.DataRepository;
import org.example.service.DataService;

public class Main {

    public static void main(String[] args) {
        // Initialize layers from bottom to top
        DataRepository dataRepository = new DataRepository();
        DataService dataService = new DataService(dataRepository);
        ResponseBuilder responseBuilder = new ResponseBuilder();
        Controller controller = new Controller(dataService, responseBuilder);
        HttpRequestParser httpRequestParser = new HttpRequestParser();
        RequestHandler requestHandler = new RequestHandler(httpRequestParser, controller);
        Server server = new Server(requestHandler);

        server.start();
    }
}
