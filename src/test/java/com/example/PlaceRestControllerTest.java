package com.example;

import com.example.services.placeservice.PlaceService;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Created by Dmitrij on 14.02.2016.
 */
@RunWith(MockitoJUnitRunner.class)

public class PlaceRestControllerTest {
    MockMvc mvc;
    @Mock
    PlaceService placeService;
    
}
