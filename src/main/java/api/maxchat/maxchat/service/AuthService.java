package api.maxchat.maxchat.service;

import api.maxchat.maxchat.dto.RegistrationDTO;
import api.maxchat.maxchat.entity.ProfileEntity;
import api.maxchat.maxchat.enums.GeneralStatus;
import api.maxchat.maxchat.enums.ProfileRole;
import api.maxchat.maxchat.excp.AppBadException;
import api.maxchat.maxchat.repository.ProfileRepository;
import api.maxchat.maxchat.repository.ProfileRoleRepository;
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

    @Autowired
    private ProfileRoleRepository profileRoleRepository;
    @Autowired
    private ProfileRoleService profileRoleService;

    @Autowired
    private EmailSendingService emailSendingService;

    @Autowired
    private ProfileService profileService;

    public String register(RegistrationDTO registerDTO) {

        //1. Validation
        //2. username check
        Optional<ProfileEntity>optional = profileRepository.findByUsernameAndVisible(registerDTO.getUsername(), true);

        if (optional.isPresent()) {
            ProfileEntity profile = optional.get();
            if(profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)){
                profileRoleService.deleteRoles(profile.getId());
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

        //Insert Roles
        profileRoleService.create(entity.getId(), ProfileRole.ROLE_USER);

        emailSendingService.sendRegistrationEmail(registerDTO.getUsername(), entity.getId());

        return "Registration successful";
    }

    public String regVerification(Integer profileId){
        ProfileEntity profile = profileService.getById(profileId);
        System.out.println("profile" + profile);
        if(profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)){
            //IN_REGISTRATION => Active change
            profileRepository.changeStatus(profileId, GeneralStatus.ACTIVE);
            return "Registration successful";
        }
        throw new   AppBadException("Verification failed");

    }
}
