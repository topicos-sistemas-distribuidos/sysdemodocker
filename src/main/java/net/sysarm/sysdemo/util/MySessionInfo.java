package net.sysarm.sysdemo.util;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import net.sysarm.sysdemo.model.Users;
import net.sysarm.sysdemo.service.UsersService;

@Component
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MySessionInfo {
    private Users user;
	private UsersService userService;
	private String acesso;
	
	@Autowired
	public void setUserService(UsersService userServices){
		this.userService = userServices;
	}

	public UserDetails currentUserDetails(){
	    SecurityContext securityContext = SecurityContextHolder.getContext();
	    Authentication authentication = securityContext.getAuthentication();
	    if (authentication != null) {
	        Object principal = authentication.getPrincipal();
	        return principal instanceof UserDetails ? (UserDetails) principal : null;
	    }
	    return null;
	}
	
    public Users getCurrentUser() {
    	String username="";
    	
    	if (user == null) {	              	
    		Object principal = currentUserDetails();
    		
    		if (principal != null) {
    			username = ((UserDetails)principal).getUsername();
    			user = userService.getUserByUserName(username);
    			System.out.println("user name: " + username);
    		}
        	
        }
        
    	return user;
    }
    
    /**
     * Checa se uma determinada regra existe na lista de permissoes do usuário logado
     * @param role
     * @return
     */
    public boolean hasRole(String role) {	
    	boolean checkHasRole = false;

    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	
    	if (authentication != null) {
    		
        	Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();

        	if (authorities != null) {  

        		for (GrantedAuthority authority : authorities) {
        			checkHasRole = authority.getAuthority().equals(role);
        			if (checkHasRole) {
        				break;
        			}
        		}

        		return checkHasRole;
        	}	
    	}
    	else {
    		return false;
    	}
		
    	return checkHasRole;
    }  
    
    /**
     * Verifica se o usuário logou como administrador
     */
    private String checkAccessControl() {
 	   if (hasRole("ADMIN")) {
 		   return acesso = "ADMIN";
 	   }else {
 		   return acesso = "";
 	   }
    }

    /**
     * Checa o acesso do usuario
     * @return acesso ADMIN ou ""
     */
	public String getAcesso() { // NOPMD by armandosoaressousa on 1/30/20 5:44 PM
		return checkAccessControl();
	}
    
}