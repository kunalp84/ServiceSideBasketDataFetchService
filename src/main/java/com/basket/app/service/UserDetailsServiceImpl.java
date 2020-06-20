package com.basket.app.service;
import java.util.ArrayList; import java.util.List;


import com.basket.app.pojo.BasketUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; import
        org.springframework.security.core.GrantedAuthority; import
        org.springframework.security.core.authority.SimpleGrantedAuthority; import
        org.springframework.security.core.userdetails.User.UserBuilder; import
        org.springframework.security.core.userdetails.UserDetails; import
        org.springframework.security.core.userdetails.UserDetailsService; import
        org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;




@Service("userDetailsService") public class UserDetailsServiceImpl implements
        UserDetailsService {

    @Autowired private UserService userService;

   // @Autowired private UserRoleService userRoleService;
   private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);


    @Override public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException { LOGGER.info("UserName="+username);

        BasketUser user=this.userService.getUserByName(username);

        LOGGER.info("Found="+user.getName()+","+user.getPassword()+","+
                user.isEnabled());

        UserBuilder builder = null; if (user != null) {

            builder =
                    org.springframework.security.core.userdetails.User.withUsername(username);

            LOGGER.info("IS THIS USER ENABLED :::::"+user.isEnabled());
            if(user.isEnabled()) { builder.disabled(false); }
            else
            {
                builder.disabled(true);
            }
          //  builder.disabled(false);

            builder.password(user.getPassword());
            LOGGER.info("IS THIS USER ENABLED BUILDER :::::"+builder);

            String[] authorities = new String[1];
            authorities[0] = user.getUserType();
                  /*  this.userRoleService.getUserRoleByName(username) .stream().map(a ->
                            a.getRole()).toArray(String[]::new); */

            LOGGER.info("Length="+authorities.length);
            builder.authorities(authorities);
        } else
            {
                throw new
                UsernameNotFoundException("User not found.");
            }

                return builder.build();

    }

}
