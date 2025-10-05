package org.example.Config;

import org.example.Router;
import org.example.controller.TestController;
import org.example.httphandler.HttpResponseBuilder;
import org.example.repository.DataRepository;
import org.example.service.DataService;

public class RouterConfig {

    public static void configure() {
        Router router = Router.getInstance();

        // 컨트롤러 인스턴스 생성
        DataRepository dataRepository = new DataRepository();
        DataService dataService = new DataService(dataRepository);
        HttpResponseBuilder httpResponseBuilder = new HttpResponseBuilder();
        TestController testController = new TestController(dataService, httpResponseBuilder);

        // 라우트 등록
        router.get("/", testController, "handleGet");
        router.get("/data/{key}", testController, "handleGet");
        router.post("/data/{key}", testController, "handlePost");
    }
}
