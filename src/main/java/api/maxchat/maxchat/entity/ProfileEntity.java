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

    /*
    * 2. Foydalanuvchilar ro‘yxatdan o'tishi va oldin ro‘yxatdan o'tgan foydalanuvchilar
    tizimga login bo'lib kira olishi kerak.

    Ro‘yxatdan o'tganda Isim, Email/Phone va Parol ma'lumotlarni kiritishi kerak.
    Telefon raqam bilan ro‘yxatdan o'tish imkoni ham bo'lsin (Isim, PhoneNumber, Parol).
    Ro‘yxatdan o'tishda Email yoki Phone ni tasdiqlashi kerak.

    Login qilishda Email/Phone va Password orqali bo'ladi.
    Parolni unutdingizni (parolni qayta tiklash) funksionali bo'lsin.
    * */

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
    private GeneralStatus status;  //Active, Block Emums ochiladi

    @Column(name="visible")
    private boolean visible = Boolean.TRUE;

    private LocalDateTime createdDate;


}
