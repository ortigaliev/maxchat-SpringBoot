package api.maxchat.maxchat.service;

import api.maxchat.maxchat.dto.AuthDTO;
import api.maxchat.maxchat.dto.JwtDTO;
import api.maxchat.maxchat.dto.ProfileDTO;
import api.maxchat.maxchat.dto.RegistrationDTO;
import api.maxchat.maxchat.entity.ProfileEntity;
import api.maxchat.maxchat.enums.GeneralStatus;
import api.maxchat.maxchat.enums.ProfileRole;
import api.maxchat.maxchat.excp.AppBadException;
import api.maxchat.maxchat.repository.ProfileRepository;
import api.maxchat.maxchat.repository.ProfileRoleRepository;
import api.maxchat.maxchat.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
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
        Optional<ProfileEntity>optional = profileRepository.findByUsernameAndVisibleTrue(registerDTO.getUsername() /*, true*/);

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

    public String regVerification(String token){
        try {
            JwtDTO jwt = JwtUtil.decode(token); //to'g'ri decode
            Integer profileId = jwt.getId();//id ni ajratib olish

            ProfileEntity profile = profileService.getById(profileId);
            System.out.println("profile" + profile);

            if(profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)){
                //IN_REGISTRATION => Active change
                profileRepository.changeStatus(profileId, GeneralStatus.ACTIVE);
                return "Registration successful";
            }
        } catch (JwtException e){
        }
        throw new AppBadException("Verification failed");

    }

    public ProfileDTO login(AuthDTO dto){
        //dto
        //check: user bor yoki yoi'qligini tekshiramiz
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername()/*, true*/);
        if(optional.isEmpty()){
            throw new AppBadException("Username or password is wrong!");
        }
        //password: Paswordni shifrlangani bn user kiritgan pasword tekshiriladi
        ProfileEntity profile = optional.get();
        if(!bCryptPasswordEncoder.matches(dto.getPassword(), profile.getPassword())){
            throw new AppBadException("Username or password is wrong!");
        }
        /*Status: Statusini tekshiramiz. Activga tekshiramiz */
        if(!profile.getStatus().equals(GeneralStatus.ACTIVE)){
            throw new AppBadException("Wrong status!");
        }

        /*Response*/
        ProfileDTO response = new ProfileDTO();
        response.setName(profile.getName());
        response.setUsername(profile.getUsername());
        response.setRoleList(profileRoleRepository.getAllRolesListByProfileId(profile.getId()));

        /*JWT*/
        response.setJwt(JwtUtil.encode(profile.getId(), profile.getUsername(), response.getRoleList()));
        return response;
    }

}
