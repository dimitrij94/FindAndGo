package com.example.neo_services.user;

import com.example.configuration.CustomNeo4JDatabaseConfiguration;
import com.example.configuration.CustomSpringSecurityConfig;
import com.example.constants.image.sizes.UserImageSizes;
import com.example.graph.photos.PlaceUserPhoto;
import com.example.graph.user.PlaceUser;
import com.example.graph_repositories.user.PlaceUserPhotoRepository;
import com.example.pojo.dto.PhotoDTO;
import com.example.pojo.dto.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertTrue;

/**
 * Created by Dmitrij on 10.05.2016.
 */
@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CustomNeo4JDatabaseConfiguration.class)
@Transactional
public class UserServiceImplementationTest {

    @Autowired
    UserService userService;

    @Autowired
    PlaceUserPhotoRepository userPhotoRepository;

    private final String userName = "Dimitrij94";
    private final String userPassword = "d147896325";
    private final String userEmail = "dim.kost.94@gmial.com";
    private final String userPhotoPath = "E:\\Projects\\untitled1\\src\\main\\resources\\photo.jpg";
    private MockMultipartFile userPhoto;


    @Before
    public void setUp() throws IOException {
        File photoFile = new File(userPhotoPath);
        try (InputStream in = new FileInputStream(photoFile)) {
            userPhoto = new MockMultipartFile("user_photo", "photo.jpg", MediaType.IMAGE_JPEG_VALUE, in);
        }
        userPhotoRepository.clearDataBaseBeforeTests();
    }

    private UserDTO createUserDTOMock() {
        return new UserDTO(userEmail, userName, userPassword);
    }

    @Test
    public void testNewUser() throws Exception {
        UserDTO userDTO = createUserDTOMock();
        PlaceUser user = userService.create(userDTO);
        assertTrue(user.getEmail().equals(userEmail));
        assertTrue(user.getId() != null);
        assertTrue(user.getUserName().equals(userName));
        assertTrue(user.getAuthority().equals(CustomSpringSecurityConfig.ROLE_USER));
    }

    @Test
    public void testPlaceUser() throws Exception {
        assertTrue(userService.find(userName).getUserName().equals(userName));
    }

    @Test
    public void testSavePhoto() throws Exception {
        userService.savePhoto(userName, new PhotoDTO(userPhoto));
        for (UserImageSizes size : UserImageSizes.values()) {
            PlaceUserPhoto photo = userPhotoRepository.findByNameAndUserUserName(size.getName(), userName);
            assertTrue(photo.getName().equals(size.getName()));
        }
    }
}