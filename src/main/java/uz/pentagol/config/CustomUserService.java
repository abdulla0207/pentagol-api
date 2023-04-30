package uz.pentagol.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pentagol.entity.UserEntity;
import uz.pentagol.repository.ProfileRepository;

import java.util.Optional;

@Service
public class CustomUserService implements UserDetailsService {

    private final ProfileRepository profileRepository;

    public CustomUserService(ProfileRepository profileRepository){
        this.profileRepository = profileRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> getByUsername = profileRepository.findByUsername(username);

        if(getByUsername.isEmpty())
            throw new UsernameNotFoundException("Bad credentials");

        UserEntity userEntity = getByUsername.get();

        return new CustomUserDetail(userEntity);
    }
}
