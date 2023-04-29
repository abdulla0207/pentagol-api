package uz.pentagol.service;

import org.springframework.stereotype.Service;
import uz.pentagol.dto.RegistrationDTO;
import uz.pentagol.dto.autherization.AuthLoginDTO;
import uz.pentagol.dto.autherization.AuthResponseDTO;
import uz.pentagol.entity.UserEntity;
import uz.pentagol.enums.UserRoleEnum;
import uz.pentagol.exceptions.AppBadRequest;
import uz.pentagol.exceptions.ItemAlreadyExists;
import uz.pentagol.repository.ProfileRepository;
import uz.pentagol.util.MD5Util;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class AuthService {
    private final ProfileRepository profileRepository;

    public AuthService(ProfileRepository profileRepository){
        this.profileRepository = profileRepository;
    }
    public String register(RegistrationDTO registrationDTO) {
        Optional<UserEntity> userByEmail = profileRepository.findByEmail(registrationDTO.getEmail());

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
        Optional<UserEntity> userByEmailAndPassword = profileRepository.findByEmailAndPassword(authLoginDTO.getEmail(),
                MD5Util.encode(authLoginDTO.getPassword()));

        if(userByEmailAndPassword.isEmpty())
            throw new AppBadRequest("User not found. Credentials are wrong");

        AuthResponseDTO responseDTO = toResponseDTO(userByEmailAndPassword.get());
        return responseDTO;
    }

    private AuthResponseDTO toResponseDTO(UserEntity userEntity) {
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setEmail(userEntity.getEmail());
        authResponseDTO.setUsername(userEntity.getUsername());
        authResponseDTO.setRoleEnum(userEntity.getUserRoleEnum());

        return authResponseDTO;
    }
}
