package api.maxchat.maxchat.config;

import api.maxchat.maxchat.entity.ProfileEntity;
import api.maxchat.maxchat.enums.ProfileRole;
import api.maxchat.maxchat.repository.ProfileRepository;
import api.maxchat.maxchat.repository.ProfileRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileRoleRepository profileRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername: " + username);
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisible(username);
        if(optional.isEmpty()){
            throw new UsernameNotFoundException("Username not found");
        }
        ProfileEntity profile = optional.get();
        List<ProfileRole> roleList = profileRoleRepository.getAllRolesListByProfileId(profile.getId());
        return new CustomUserDetails(profile, roleList);
    }
}
