package uz.pentagol.service;

import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Service;
import uz.pentagol.dto.authentication.RegistrationDTO;
import uz.pentagol.dto.authentication.RegistrationResponseDTO;
import uz.pentagol.dto.authentication.UserRegistrationResponseDTO;
import uz.pentagol.dto.authorization.AuthLoginDTO;
import uz.pentagol.dto.authorization.AuthResponseDTO;
import uz.pentagol.entity.UserEntity;
import uz.pentagol.enums.UserRoleEnum;
import uz.pentagol.enums.UserStatusEnum;
import uz.pentagol.exceptions.*;
import uz.pentagol.repository.ProfileRepository;
import uz.pentagol.util.JwtUtil;
import uz.pentagol.util.MD5Util;

import java.util.Optional;

@Service
public class AuthService {
    private final ProfileRepository profileRepository;

    private final EmailService emailService;

    public AuthService(ProfileRepository profileRepository, EmailService emailService){
        this.profileRepository = profileRepository;
        this.emailService = emailService;
    }
    public RegistrationResponseDTO register(RegistrationDTO registrationDTO) {
        Optional<UserEntity> userByEmail = profileRepository.findByEmail(registrationDTO.getEmail());
        Optional<UserEntity> userByUsername = profileRepository.findByUsername(registrationDTO.getUsername());

        if(userByUsername.isPresent())
            throw new ItemAlreadyExists("User with this username already exists");
        if(userByEmail.isPresent())
            throw new ItemAlreadyExists("User with this email already exists");


        UserEntity newUser = new UserEntity();
        newUser.setPassword(MD5Util.encode(registrationDTO.getPassword()));
        newUser.setUsername(registrationDTO.getUsername());
        newUser.setEmail(registrationDTO.getEmail());
        newUser.setUserRoleEnum(UserRoleEnum.USER);
        newUser.setUserStatusEnum(UserStatusEnum.NOT_ACTIVE);

        profileRepository.save(newUser);

        sendConfirmationEmail(newUser.getId(), newUser.getUsername(), newUser.getEmail());

        RegistrationResponseDTO responseDTO = toRegistrationResponseDTO(newUser);
        return responseDTO;
    }

    private void sendConfirmationEmail(int id, String username, String toEmail){
        String stringBuilder = "Hey " + username + "!\n" +
                "Thanks for creating a new account in Pentagol. We want to make sure it is really you.\n" +
                "Click to verify your email address: http://localhost:8080/auth/verify/email/" + JwtUtil.encodeEmailVerification(id);

        emailService.sendEmail(toEmail, "[Pentagol] Verify your account", stringBuilder);

    }

    private RegistrationResponseDTO toRegistrationResponseDTO(UserEntity newUser) {
        RegistrationResponseDTO dto = new RegistrationResponseDTO();
        dto.setToken(JwtUtil.encode(newUser.getId(), newUser.getUserStatusEnum(), newUser.getUsername(), newUser.getUserRoleEnum()));
        dto.setUserResponse(new UserRegistrationResponseDTO(newUser.getId(), newUser.getUsername(), newUser.getEmail()));
        return dto;
    }

    public AuthResponseDTO login(AuthLoginDTO authLoginDTO) {
        Optional<UserEntity> userByEmailAndPassword = profileRepository.findByUsernameAndPassword(authLoginDTO.getUsername(),
                MD5Util.encode(authLoginDTO.getPassword()));

        if(userByEmailAndPassword.isEmpty())
            throw new AppBadRequestException("User not found. Credentials are wrong");

        AuthResponseDTO responseDTO = toResponseDTO(userByEmailAndPassword.get());
        return responseDTO;
    }

    private AuthResponseDTO toResponseDTO(UserEntity userEntity) {
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setToken(JwtUtil.encode(userEntity.getId(), userEntity.getUserStatusEnum(), userEntity.getUsername(), userEntity.getUserRoleEnum()));
        authResponseDTO.setUsername(userEntity.getUsername());
        authResponseDTO.setRoleEnum(userEntity.getUserRoleEnum());

        return authResponseDTO;
    }

    public String verifyEmail(String token) {
        Integer userId;

        try{
            userId = JwtUtil.decodeEmailVerification(token);
        }catch (JwtException e){
            return "Verification failed";
        }

        Optional<UserEntity> userById = profileRepository.findById(userId);
        if(userById.isEmpty())
            throw new ItemNotFound("Profile not found");

        int updateUserStatus = profileRepository.updateUserStatus(userId, UserStatusEnum.ACTIVE);
        if(updateUserStatus <= 0)
            throw new EntityNotUpdateException("User status has not been updated");

        return "Verified";
    }
}
