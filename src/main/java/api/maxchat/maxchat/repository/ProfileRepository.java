package api.maxchat.maxchat.repository;

import api.maxchat.maxchat.entity.ProfileEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {

    //select * from profile where username = ? and visible = true;
    Optional<ProfileEntity> findByUsernameAndVisible(String username);

}
