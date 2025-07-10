package api.maxchat.maxchat.dto;

import api.maxchat.maxchat.enums.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class JwtDTO {
    private Integer id;
    private String username;
    private List<ProfileRole> roleList;
}
