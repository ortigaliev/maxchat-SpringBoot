package api.maxchat.maxchat.service;

import api.maxchat.maxchat.entity.ProfileEntity;
import api.maxchat.maxchat.excp.AppBadException;
import api.maxchat.maxchat.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileEntity getById(int id){
//       Optional<ProfileEntity> optional = profileRepository.findByIdAndVisible(id);
//       if(optional.isEmpty()){
//           throw new AppBadException("Profile not found");
//       }
//       return optional.get();

        return profileRepository.findByIdAndVisibleTrue(id).orElseThrow(() -> {
            throw new AppBadException("Profile Not Found");
        });
    }

}
