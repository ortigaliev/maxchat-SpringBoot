package api.maxchat.maxchat.entity;

import api.maxchat.maxchat.enums.GeneralStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name="profile")
@Entity
@Getter
@Setter
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;
    @Column(name="username")
    private String username; //email or phone


    @Column(name="password")
    private String password;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private GeneralStatus status;  //Active, Block

    @Column(name="visible")
    private boolean visible = Boolean.TRUE;


    private LocalDateTime createdDate;


}
