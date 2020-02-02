package net.sysarm.sysdemo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sysarm.sysdemo.model.Users;
import net.sysarm.sysdemo.service.UsersService;
import net.sysarm.sysdemo.util.MySessionInfo;

/**
 * Faz o controle do Dashboard
 * @author armandosoaressousa
 *
 */
@Controller
public class DashboardController {
	
	@Autowired
	private UsersService userService;
	private String acesso;
	@Autowired
	private MySessionInfo mySessionInfo;
		
    @RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/metrics")
	public String metrics(){
		return "redirect:/metrics";
	}

	@RequestMapping("/info")
	public String info(){
		return "redirect:/info";
	}
	
	@RequestMapping("/health")
	public String health(){
		return "redirect:/health";
	}

	@RequestMapping("/beans")
	public String beans(){
		return "redirect:/beans";
	}

	@RequestMapping("/trace")
	public String trace(){
		return "redirect:/trace";
	}

	/**
	 * Atualiza os atributos do Dashboard
	 * @param model
	 * @param totalUsers
	 * @param totalPictures
	 * @param loginUser
	 * @param listUsers
	 * @return model com os atributos carregado
	 */
	private Model setDashboardAttributes(Model model, int totalUsers, int totalPictures, Users loginUser, List<Users> listUsers) {
		model.addAttribute("totalUsers", totalUsers);
    	model.addAttribute("totalPictures", totalPictures);
    	model.addAttribute("listUsers", listUsers);
    	model.addAttribute("loginusername", loginUser.getUsername());
    	model.addAttribute("loginemailuser", loginUser.getEmail());
    	model.addAttribute("loginuserid", loginUser.getId());
    	model.addAttribute("person", loginUser.getPerson());
    	model.addAttribute("acesso", acesso);
		model.addAttribute("loginuser", loginUser);
		return model;
	}

	/**
	 * Verifica quais são as permissões do usuário logado e direciona para o dashboard correto
	 * @param model
	 * @param principal
	 * @return
	 */
    @RequestMapping("/")
    public String index(Model model, Principal principal) {    
    	    
    	String servico="/dashboard";
    	
    	if (mySessionInfo.hasRole("ADMIN") && mySessionInfo.hasRole("USER")) {
    		servico = servico + "/admin";
    		return "redirect:"+servico;
    	}
    	if (mySessionInfo.hasRole("USER")) {
    		servico = servico + "/user";
    		return "redirect:"+servico;
    	}
		return "redirec:/logout";    	           	    	
    }
    
    /**
     * Carrega o dashboard do usuário administrador do sistema
     * @param model
     * @param principal
     * @return
     */
    @RequestMapping("/dashboard/admin")
    public String indexAdmin(Model model, Principal principal) {
    	int totalUsers=0;
    	int totalPictures = 0;
    	totalUsers = (int) this.userService.count();    	
    	Users loginUser = userService.getUserByUserName(mySessionInfo.getCurrentUser().getUsername());    	
    	totalPictures = loginUser.getPerson().getPictures().size();
    	acesso = mySessionInfo.getAcesso();    	
    	List<Users> listUsers = this.userService.getAll();    
    	
    	model = setDashboardAttributes(model, totalUsers, totalPictures, loginUser, listUsers);
    	     	
        return "dashboard/index";
    }
    
    /**
     * Carrega o dashboard do usuário comum
     * @param model
     * @param principal
     * @return página com as funções disponíveis para o usuário comum
     */
    @RequestMapping("/dashboard/user")
    public String indexUser(Model model, Principal principal) {    	    	
    	int totalUsers = (int) this.userService.count();    
    	int totalPictures = 0;
    	Users loginUser = mySessionInfo.getCurrentUser();
    	totalPictures = loginUser.getPerson().getPictures().size();
    	acesso = mySessionInfo.getAcesso();
    	List<Users> listUsers = this.userService.getAll();

		model = setDashboardAttributes(model, totalUsers, totalPictures, loginUser, listUsers);
    	
        return "dashboard/indexUser";
    }
       
}