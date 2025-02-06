package com.costa.expense_tracker_api.domain.user;

import com.costa.expense_tracker_api.exceptions.UserNotLoggedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public class UserUtils {

    public static UserDetails getUserLogged(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.getPrincipal() instanceof UserDetails){
            return (UserDetails) authentication.getPrincipal();
        }
        throw new UserNotLoggedException();
    }
}
