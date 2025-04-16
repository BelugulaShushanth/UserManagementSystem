package com.ums.bean;

import com.ums.config.FacesViewScope;
import lombok.Data;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("uiDto")
@Scope(FacesViewScope.NAME)
@Data
@ToString
public class UIDto {

    private String message;
    private User user;
}
