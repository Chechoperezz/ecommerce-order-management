package com.Letech.ecommerceordermanagement.Service;


import com.Letech.ecommerceordermanagement.Entity.Customer;
import com.Letech.ecommerceordermanagement.Repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.mockito.Mockito;


@SpringBootTest
class UserServiceTest {
//
//    @Autowired
//    private UserService userService;
//
//    @MockBean
//    private CustomerRepository customerRepository;
//
//    @BeforeEach
//    void setUp() {
//
//        Customer existingUser = Customer.builder()
//                .(1L)
//                .userGmail("Sergio@gmail.com")
//                .userName("SergioP")
//                .userPhone("30012345")
//                .build();
//
//
//        Mockito.when(customerRepository.findUserByAccountId(1L)).thenReturn(existingUser);
//    }
//
//
//    @Test
//    public void updateUserWithDuplicateValuesTest() {
//        // Mock existing user in the repository
//        User existingUser = User.builder()
//                .userId(1L)
//                .userGmail("existing@gmail.com")
//                .userName("existingUser")
//                .userPhone("123456789")
//                .build();
//
//        Mockito.when(userRepository.findUserByUserId(1L)).thenReturn(existingUser);
//        Mockito.when(userRepository.existsByUserUserName(Mockito.anyString())).thenReturn(true); // Set to true to simulate duplicate
//        Mockito.when(userRepository.existsByUserGmail(Mockito.anyString())).thenReturn(true);
//        Mockito.when(userRepository.existsByUserPhone(Mockito.anyString())).thenReturn(true);
//
//        // Configuring a userToUpdate with duplicate values
//        User userToUpdate = User.builder()
//                .userGmail("existing@gmail.com")
//                .userName("existingUser")
//                .userPhone("123456789")
//                .build();
//
//        // Attempting to update existing user with duplicate values should throw DuplicateUserException
//        DuplicateUserException exception = assertThrows(DuplicateUserException.class,
//                () -> userService.updateUserInfo(userToUpdate, 1L));
//
//        assertEquals("VALUES ARE DUPLICATED", exception.getMessage());
//    }
//
//
//    @Test
//    public void updateUserWithUniqueValuesTest() {
//        // Mock existing user in the repository
//        User existingUser = User.builder()
//                .userId(1L)
//                .userGmail("existing@gmail.com")
//                .userName("existingUser")
//                .userPhone("123456789")
//                .build();
//
//        Mockito.when(userRepository.findUserByUserId(1L)).thenReturn(existingUser);
//        Mockito.when(userRepository.existsByUserUserName(Mockito.anyString())).thenReturn(false);
//        Mockito.when(userRepository.existsByUserGmail(Mockito.anyString())).thenReturn(false);
//        Mockito.when(userRepository.existsByUserPhone(Mockito.anyString())).thenReturn(false);
//
//        // Configuring a userToUpdate with unique values
//        User userToUpdate = User.builder()
//                .userGmail("new@gmail.com")
//                .userName("newUser")
//                .userPhone("987654321")
//                .build();
//
//        // Attempting to update existing user with unique values should not throw an exception
//        User updatedUser = userService.updateUserInfo(userToUpdate, 1L);
//
//        // Fix: Comparing userName instead of userGmail
//        assertEquals("newUser", updatedUser.getUserName());  // Fix: change from "new@gmail.com" to "newUser"
//        assertEquals("new@gmail.com", updatedUser.getUserGmail());
//        assertEquals("987654321", updatedUser.getUserPhone());
//    }
}

