package com.ums.bean;

import com.ums.config.FacesViewScope;
import lombok.Data;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import javax.persistence.*;

@Component("user")
@Scope(FacesViewScope.NAME)
@Data
@ToString
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    @Transient
    private String confirmPassword;
    private String role;


}
