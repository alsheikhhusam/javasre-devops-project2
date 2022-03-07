//
//
//import com.project.Controller.WeatherController;
//import com.project.DAO.WeatherRepository;
//import com.project.DTO.ModelDTO;
//import com.project.DTO.RequestDTO;
//import com.project.Models.City;
//import com.project.Models.Request;
//import com.project.Models.Weather;
//import com.project.Models.ZipCode;
//import com.project.Service.RequestService;
//import com.project.Service.WeatherService;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.math.BigDecimal;
//import java.time.OffsetDateTime;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//
//@WebMvcTest(WeatherController.class)
//@ContextConfiguration(classes = WeatherController.class)
////@RunWith(MockitoJUnitRunner.class)
////@ImportResource({"classpath:application.yaml"})
//
//public class WeatherControllerTest {
//
//    @Autowired
//    MockMvc mockMvc;
//
//
////    @Autowired
////    ObjectMapper mapper;
//
//    @MockBean
//    private WeatherRepository weatherRepository;
//
//    @MockBean
//    private RestTemplate restTemplate;
//
//    @MockBean
//    private WeatherService weatherService;
//
//    @MockBean
//    private RequestService requestService;
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @InjectMocks
//    WeatherController controller = new WeatherController(restTemplate, weatherService, requestService);
//
//
//
//    @Before
//    public void setUp()
//    {
//
//
//        List mockedList = mock(List.class);
//
//        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//    }
//
//    @Test
//    public void ackTest() throws Exception {
//        Weather sample = new Weather();
//        int zipCode = 0;
//        String cityName = "";
//        String location = "Davis";
//        City cityObject = new City(2106, location, BigDecimal.valueOf(38.544907),BigDecimal.valueOf(-121.740517),"State");
//        ZipCode zipObject = new ZipCode(1, BigDecimal.valueOf(38.544907),BigDecimal.valueOf(-121.740517));
//
//        String latitude;
//        String longitude;
//
//
//
//        when(requestService.getZipCode(zipCode)).thenReturn(zipObject);
//        when(requestService.getCity(location)).thenReturn(cityObject);
//        when(requestService.save(new RequestDTO())).thenReturn(1);
//        when(weatherRepository.save(sample)).thenReturn(sample);
//        //when(restTemplate.getForObject()).thenReturn();
//        //when(controller.acknowledgeReceipt(location)).thenReturn(ResponseEntity.accepted().build());
//
//        longitude = cityObject.getLongNum().toString();
//        latitude = cityObject.getLatNum().toString();
//
//        latitude = zipObject.getLatNum().toString();
//        longitude = zipObject.getLongNum().toString();
//
//        String key = "f46a1727320302ec8f10c83db557ecf8";
//
////        Temp temp = new Temp();
////        temp.acknowledgeReceipt(location);
////        System.out.println(temp);
//
//        String theweather = restTemplate.getForObject("https://api.openweathermap.org/data/2.5/onecall?lat="
//                + latitude
//                + "&lon="
//                + longitude
//                + "&exclude=current,minutely,hourly,alerts&units=imperial&appid="
//                + key, String.class);
//
//        ModelDTO weather = new ModelDTO();
//        weather.setDate(OffsetDateTime.now());
//        weather.setTemperature(0.0);
//        weather.setFeelsLike(0.0);
//        weather.setPressure(0.0);
//        weather.setHumidity(0.0);
//        weather.setWindSpeed(0.0);
//        weather.setDescription("Test");
//        //weather.setRequest(update);
//        doNothing().when(weatherService).save(weather);
//        controller.acknowledgeReceipt(location);
//    }
//
//
//    @Test
//    public void acknowledgeReceiptTest()throws Exception{
//        Weather weather = new Weather();
//        City city = new City();
//        ZipCode zipCode = new ZipCode();
//        RequestDTO request = new RequestDTO();
//        Request requestTest = new Request();
//        int testID = 900;
//
//        city.setId(10);
//        city.setLongNum(BigDecimal.valueOf(32.4));
//        city.setLatNum(BigDecimal.valueOf(-72.3));
//
//        zipCode.setId(10);
//        zipCode.setLongNum(BigDecimal.valueOf(23.4));
//        zipCode.setLatNum(BigDecimal.valueOf(-75.2));
//
//
//        request.setReqDate(OffsetDateTime.now());
//        request.setCities(city);
//        request.setZipCodes(zipCode);
//
//        requestTest.setReqDate(OffsetDateTime.now());
//        requestTest.setCities(city);
//        requestTest.setZipCodes(zipCode);
//
//        Mockito.when(requestService.save(request)).thenReturn(900);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/update-db")
//                .content("Glendale")).andExpect(status().is4xxClientError());
//
//        int expected = 202;
//
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
//                "/update-db");
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//        Assert.assertEquals(expected,
//                result.getResponse().getContentAsString());
//
////        System.out.println(mockMvc.perform(MockMvcRequestBuilders.post("/update-db")
////                .content("Glendale")).andExpect(status().isCreated()));
//                //.andExpect(status().isCreated())
//                //.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                //.andExpect(MockMvcResultMatchers.jsonPath("temperature").exists());
//
//
//
//
//
//        weather.setDate(OffsetDateTime.now());
//        weather.setTemperature(32.0);
//        weather.setHumidity(0.0);
//        weather.setPressure(0.0);
//        weather.setFeelsLike(0.0);
//        weather.setPressure(0.0);
//        weather.setDescription("This is a test case.");
//        weather.setRequests(requestTest);
//
//        System.out.println("Request: " + request.toString());
//        System.out.println("Weather: " + weather.toString());
//
//
//    }
//
//}
//
