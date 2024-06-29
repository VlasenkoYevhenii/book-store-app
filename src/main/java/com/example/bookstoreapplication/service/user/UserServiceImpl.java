package com.example.bookstoreapplication.service.user;

import com.example.bookstoreapplication.dto.user.UserRegistrationRequestDto;
import com.example.bookstoreapplication.dto.user.UserResponseDto;
import com.example.bookstoreapplication.exception.RegistrationException;
import com.example.bookstoreapplication.mapper.UserMapper;
import com.example.bookstoreapplication.model.Role;
import com.example.bookstoreapplication.model.User;
import com.example.bookstoreapplication.repository.role.RoleRepository;
import com.example.bookstoreapplication.repository.user.UserRepository;
import com.example.bookstoreapplication.service.shoppingcart.ShoppingCartService;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private static final String EXCEPTION = "User is already registered!";
    private static final Long USER_ROLE_ID = 1L;
    private final ShoppingCartService shoppingCartService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        checkExistsByEmail(requestDto.getEmail());
        User user = mapper.toModel(requestDto);
        user.setPassword(encoder.encode(requestDto.getPassword()));
        Optional<Role> userRoleOptional = roleRepository.findById(USER_ROLE_ID);
        if (userRoleOptional.isEmpty()) {
            throw new RegistrationException("Role with ID " + USER_ROLE_ID + " not found");
        }
        Role userRole = userRoleOptional.get();
        user.setRoles(Collections.singleton(userRole));
        User savedUser = userRepository.save(user);

        shoppingCartService.createShoppingCart(savedUser);
        return mapper.toDto(savedUser);
    }

    private void checkExistsByEmail(String email) throws RegistrationException {
        if (!userRepository.findByEmail(email).isEmpty()) {
            throw new RegistrationException(EXCEPTION);
        }
    }
}
