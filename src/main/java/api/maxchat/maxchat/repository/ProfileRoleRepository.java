package api.maxchat.maxchat.repository;

import api.maxchat.maxchat.entity.ProfileRoleEntity;
import api.maxchat.maxchat.enums.ProfileRole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProfileRoleRepository extends CrudRepository<ProfileRoleEntity, Integer> {

    @Transactional
    @Modifying
    void deleteByProfileId(Integer profileId);

    List<ProfileRoleEntity>getByProfileId(Integer profileId);

    @Query("select p.roles from ProfileRoleEntity p where p.profileId = ?1")
    List<ProfileRole>getAllRolesListByProfileId(Integer profileId);
}
