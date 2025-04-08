package com.ums.bean;

import com.ums.config.FacesViewScope;
import lombok.Data;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Component("userHistory")
@Scope(FacesViewScope.NAME)
@Entity
@Data
@ToString
public class UserHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private Integer userId;
}
