package com.barchynai.socialMediaApi.services.user;

import com.barchynai.socialMediaApi.config.security.JwtUtils;
import com.barchynai.socialMediaApi.dto.requests.authAndUser.UserRequest;
import com.barchynai.socialMediaApi.dto.requests.authAndUser.UserUpdateRequest;
import com.barchynai.socialMediaApi.dto.responses.Response;
import com.barchynai.socialMediaApi.dto.responses.auth.TokenResponse;
import com.barchynai.socialMediaApi.dto.responses.user.UserResponse;
import com.barchynai.socialMediaApi.dto.responses.user.UserResponseForGetAll;
import com.barchynai.socialMediaApi.enums.Role;
import com.barchynai.socialMediaApi.models.users.AuthInfo;
import com.barchynai.socialMediaApi.models.users.User;
import com.barchynai.socialMediaApi.repositories.UserRepository;
import com.barchynai.socialMediaApi.validations.AuthValidate;
import com.barchynai.socialMediaApi.validations.EMailValidate;
import com.barchynai.socialMediaApi.validations.PasswordValidate;
import com.barchynai.socialMediaApi.validations.UserValidate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthValidate authValidate;
    private final EMailValidate eMailValidate;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final UserValidate userValidate;
    private final PasswordValidate passwordValidate;

    @Value("${cloud.aws.bucket.path}")
    private String path;
    @Value("${cloud.aws.bucket.default.avatar}")
    private String defaultAvatar;

    public TokenResponse registerUser(UserRequest userRequest) {

        authValidate.existsEMail(userRequest.authInfoRequest().email());

        eMailValidate.emailValidate(userRequest.authInfoRequest().email());

        AuthInfo authInfo = AuthInfo.builder()
                .username(userRequest.authInfoRequest().username())
                .email(userRequest.authInfoRequest().email())
                .password(passwordEncoder.encode(userRequest.authInfoRequest().password()))
                .dateOfCreate(LocalDate.now())
                .role(Role.USER)
                .build();

        userRepository.save(
                User.builder()
                        .firstName(userRequest.firstName())
                        .lastName(userRequest.lastName())
                        .authInfo(authInfo)
                        .build()
        );
        return new TokenResponse(
                jwtUtils.generateToken(authInfo),
                authInfo.getRole()
        );

    }

    public List<UserResponseForGetAll> findAllUsers(String search, int page, int size) {

        String searchName = (search != null) ? search.replaceAll("\\s+", " ").trim() : null;

        return userRepository.findAllUsers(path, defaultAvatar, searchName, PageRequest.of(page - 1, size));
    }

    public UserResponse findUserById(Long userId) {

        User user = userValidate.findById(userId);

        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getAuthInfo().getUsername(),
                user.getAuthInfo().getEmail(),
                user.getAvatar() != null ? path + user.getAvatar() : defaultAvatar
        );
    }

    @Transactional
    public Response updateUser(Long userId, UserUpdateRequest userUpdateRequest) {

        User user = userValidate.findById(userId);

        String oldPassword = userUpdateRequest.authInfoUpdateRequest().oldPassword();

        String newPassword = userUpdateRequest.authInfoUpdateRequest().newPassword();

        passwordValidate.validatePasswordIsCorrect(oldPassword, newPassword, user);

        user.setFirstName(userUpdateRequest.firstName());

        user.setLastName(userUpdateRequest.lastName());

        user.setAvatar(userUpdateRequest.avatar());

        user.getAuthInfo().setUsername(userUpdateRequest.authInfoUpdateRequest().username());

        user.getAuthInfo().setEmail(userUpdateRequest.authInfoUpdateRequest().email());

        return new Response("The user has been successfully updated");
    }
}
