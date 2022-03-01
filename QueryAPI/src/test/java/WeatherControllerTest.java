import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Controller.WeatherController;
import com.project.DAO.CityRepository;
import com.project.DAO.RequestRepository;
import com.project.DAO.WeatherRepository;
import com.project.DAO.ZipRepository;
import com.project.Models.City;
import com.project.Models.Request;
import com.project.Models.Weather;
import com.project.Models.ZipCode;
import com.project.QueryBase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


@WebMvcTest(WeatherController.class)
@ContextConfiguration(classes = QueryBase.class)
//@ImportResource({"classpath*:application.yaml"})

public class WeatherControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    WeatherRepository weatherRepository;

    @MockBean
    RequestRepository requestRepository;

    @MockBean
    CityRepository cityRepository;

    @MockBean
    ZipRepository zipRepository;

    @Test
    public void acknowledgeReceiptTest()throws Exception{
        Weather weather = new Weather();
        City city = new City();
        ZipCode zipCode = new ZipCode();
        Request request = new Request();

        city.setId(10);
        city.setLongNum(BigDecimal.valueOf(32.4));
        city.setLatNum(BigDecimal.valueOf(-72.3));

        zipCode.setId(10);
        zipCode.setLongNum(BigDecimal.valueOf(23.4));
        zipCode.setLatNum(BigDecimal.valueOf(-75.2));

        request.setReqDate(OffsetDateTime.now());
        request.setCities(city);
        request.setZipCodes(zipCode);

        Mockito.when(requestRepository.save(request)).thenReturn(request);

        weather.setDate(OffsetDateTime.now());
        weather.setTemperature(32.0);
        weather.setHumidity(0.0);
        weather.setPressure(0.0);
        weather.setFeelsLike(0.0);
        weather.setPressure(0.0);
        weather.setDescription("This is a test case.");
        weather.setRequests(request);
    }
}
