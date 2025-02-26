package api.maxchat.maxchat.service;

import api.maxchat.maxchat.entity.ProfileRoleEntity;
import api.maxchat.maxchat.enums.ProfileRole;
import api.maxchat.maxchat.repository.ProfileRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProfileRoleService {
    @Autowired
    private ProfileRoleRepository profileRoleRepository;

    public void create(Integer profileId, ProfileRole role) {
        ProfileRoleEntity entity = new ProfileRoleEntity();
        entity.setProfileId(profileId);
        entity.setRoles(ProfileRole.ROLE_USER);
        entity.setCreatedDate(LocalDateTime.now());
        profileRoleRepository.save(entity);
    }

    public void deleteRoles(Integer profileId){
        profileRoleRepository.deleteByProfileId(profileId);
    }
}
