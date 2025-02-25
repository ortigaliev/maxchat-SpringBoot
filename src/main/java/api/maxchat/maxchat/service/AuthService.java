package api.maxchat.maxchat.service;

import api.maxchat.maxchat.dto.RegistrationDTO;
import api.maxchat.maxchat.entity.ProfileEntity;
import api.maxchat.maxchat.enums.GeneralStatus;
import api.maxchat.maxchat.excp.AppBadException;
import api.maxchat.maxchat.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String register(RegistrationDTO registerDTO) {

        //1. Validation
        //2. username check
       Optional<ProfileEntity>optional = profileRepository.findByUsernameAndVisible(registerDTO.getUsername());

       if (optional.isPresent()) {
           ProfileEntity profile = optional.get();
           if(profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)){
               profileRepository.delete(profile);
           }else {
               throw new AppBadException("Username already exists");
           }

       }

       ProfileEntity entity = new ProfileEntity();
       entity.setName(registerDTO.getName());
       entity.setUsername(registerDTO.getUsername());
       entity.setPassword(bCryptPasswordEncoder.encode(registerDTO.getPassword()));
       entity.setStatus(GeneralStatus.IN_REGISTRATION);
       entity.setVisible(true);
       entity.setCreatedDate(LocalDateTime.now());
       profileRepository.save(entity);

        return null;
    }
}
