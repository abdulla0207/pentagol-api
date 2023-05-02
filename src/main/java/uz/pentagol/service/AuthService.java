package uz.pentagol.service;

import org.springframework.stereotype.Service;
import uz.pentagol.dto.RegistrationDTO;
import uz.pentagol.dto.authorization.AuthLoginDTO;
import uz.pentagol.dto.authorization.AuthResponseDTO;
import uz.pentagol.entity.UserEntity;
import uz.pentagol.enums.UserRoleEnum;
import uz.pentagol.exceptions.AppBadRequestException;
import uz.pentagol.exceptions.ItemAlreadyExists;
import uz.pentagol.repository.ProfileRepository;
import uz.pentagol.util.JwtUtil;
import uz.pentagol.util.MD5Util;

import java.util.Optional;

@Service
public class AuthService {
    private final ProfileRepository profileRepository;

    public AuthService(ProfileRepository profileRepository){
        this.profileRepository = profileRepository;
    }
    public String register(RegistrationDTO registrationDTO) {
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

        profileRepository.save(newUser);
        return "User Created";
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
        authResponseDTO.setToken(JwtUtil.encode(userEntity.getId(), userEntity.getUsername(), userEntity.getUserRoleEnum()));
        authResponseDTO.setUsername(userEntity.getUsername());
        authResponseDTO.setRoleEnum(userEntity.getUserRoleEnum());

        return authResponseDTO;
    }
}
