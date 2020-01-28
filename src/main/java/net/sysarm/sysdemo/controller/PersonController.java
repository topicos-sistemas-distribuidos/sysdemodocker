package net.sysarm.sysdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import net.sysarm.sysdemo.model.Person;
import net.sysarm.sysdemo.model.Picture;
import net.sysarm.sysdemo.model.Users;
import net.sysarm.sysdemo.service.PersonService;
import net.sysarm.sysdemo.service.PictureService;
import net.sysarm.sysdemo.service.UsersService;
import net.sysarm.sysdemo.util.MySessionInfo;

/**
 * Faz o controle do domínio de Controle de Acesso
 * @author armandosoaressousa
 *
 */
@Controller
public class PersonController {
	
	private PersonService personService;
	private UsersService userService;
	private Users loginUser;
	private PictureService pictureService;
	
	@Autowired
	private MySessionInfo mySessionInfo;
		
	@Autowired
	public void setPictureService(PictureService pictureService) {
		this.pictureService = pictureService;
	}
		
	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
	
    @Autowired
    public void setUserService(UsersService userService) {
    	this.userService = userService;
    }
	
    /**
     * Atualiza os dados do usuario logado
     */
	private void checkUser() {
		loginUser = mySessionInfo.getCurrentUser();
	}
	
	/**
	 * Lista todas as pessoas cadastradas no sistema
	 * @param model
	 * @return página com a lista de pessoas cadastradas
	 */
    @RequestMapping(value="/person", method = RequestMethod.GET)
    public String index(Model model) {
    	List<Person> list = this.personService.getAll();
    	checkUser();
    	
    	model.addAttribute("list", list);
    	model.addAttribute("loginusername", loginUser.getUsername());
    	model.addAttribute("loginemailuser", loginUser.getEmail());
    	model.addAttribute("loginuserid", loginUser.getId());
    	model.addAttribute("loginuser", loginUser);

        return "person/list";
    }
    
               
    /**
     * Seleciona uma foto da pessoa para ser adicionada no seu album
     * @param personId id da Pessoa
     * @param model model
     * @return formPicture.html
     */
	@RequestMapping(value = "/person/{personId}/select/picture")
	public String selectImage(@PathVariable(value = "personId") Long personId, Model model){
		Person person = this.personService.get(personId);
		Picture picture = new Picture();
		
		picture.setPerson(person);
		checkUser();
		
        model.addAttribute("person", person);
        model.addAttribute("picture", picture);
        model.addAttribute("loginusername", loginUser.getUsername());
    	model.addAttribute("loginemailuser", loginUser.getEmail());
    	model.addAttribute("loginuserid", loginUser.getId());
    	model.addAttribute("personid", person.getId());
    	model.addAttribute("username", person.getUser().getUsername());
    	model.addAttribute("loginuser", loginUser);
    	
        return "person/formPicture";
	}
    
	@RequestMapping(value="/person/{personId}/picture/{pictureId}/edit")
    public String editMyPicture() {
    	//TODO
    	return null;
    }
		
}