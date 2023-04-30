package uz.pentagol.service;

import org.springframework.stereotype.Service;
import uz.pentagol.dto.JwtDTO;
import uz.pentagol.dto.UserDTO;
import uz.pentagol.entity.UserEntity;
import uz.pentagol.exceptions.UserNotFound;
import uz.pentagol.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public UserDTO getById(int id) {
        Optional<UserEntity> byId = userRepository.findById(id);

        if (byId.isEmpty())
            throw new UserNotFound("User with this id not found");

        UserEntity userEntity = byId.get();

        return toDTO(userEntity);
    }

    private UserDTO toDTO(UserEntity entity){
        UserDTO dto = new UserDTO();
        dto.setEmail(entity.getEmail());
        dto.setUsername(entity.getUsername());
        dto.setRoleEnum(entity.getUserRoleEnum());

        return dto;
    }

}
