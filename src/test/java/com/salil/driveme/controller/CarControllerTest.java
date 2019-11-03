package com.salil.driveme.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salil.driveme.datatransferobject.CarDTO;
import com.salil.driveme.domainvalue.EngineType;
import com.salil.driveme.service.car.CarService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
public class CarControllerTest
{
    private MediaType defaultContentType = new MediaType(
        APPLICATION_JSON.getType(),
        APPLICATION_JSON.getSubtype(),
        Charset.forName("utf8"));

    private MockMvc mockMvc;

    @MockBean
    private CarService carService;


    public static CarDTO convertJsonToCarDTO(final String car)
    {
        try
        {
            return new ObjectMapper().readValue(car, CarDTO.class);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }


    public static String convertDTOToJson(final CarDTO car)
    {
        try
        {
            return new ObjectMapper().writeValueAsString(car);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }


    @Before
    public void setup()
    {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new CarController(carService)).build();
    }


    /**
     * Added few sample methods to test the flow.
     */

    @Test
    public void whenCreatingCarReturnAnErrorIfLicenseDoesNotHaveCorrectLength() throws Exception
    {
        CarDTO carDto = new CarDTO(null, "BER1", 1, false, 1, EngineType.DIESEL, 1L);
        this.mockMvc.perform(post("/v1/cars").accept(defaultContentType).contentType(defaultContentType).content(convertDTOToJson(carDto)))
            .andExpect(status().isBadRequest());
    }


    @Test
    public void whenCreatingCarReturnAnErrorIfEngineTypeIsNull() throws Exception
    {
        CarDTO carDto = new CarDTO(null, "BER1234", 2, false, 1, null, 1L);
        this.mockMvc.perform(post("/v1/cars").accept(defaultContentType).contentType(defaultContentType).content(convertDTOToJson(carDto)))
            .andExpect(status().isBadRequest());
    }
}
