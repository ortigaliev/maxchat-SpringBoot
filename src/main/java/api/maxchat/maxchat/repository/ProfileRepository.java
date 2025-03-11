package api.maxchat.maxchat.repository;

import api.maxchat.maxchat.entity.ProfileEntity;
import api.maxchat.maxchat.enums.GeneralStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {

    //select * from profile where username = ? and visible = true;
    Optional<ProfileEntity> findByUsernameAndVisible(String username, boolean visible);
    Optional<ProfileEntity> findByIdAndVisibleTrue(Integer id);


    @Modifying
    @Transactional
    @Query("update ProfileEntity set status =?2 where id = ?1 ")
    void changeStatus(Integer id, GeneralStatus status);
}
