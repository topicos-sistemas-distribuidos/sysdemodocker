package net.sysarm.sysdemo.controller;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.sysarm.sysdemo.exception.UserAlreadyExistsException;
import net.sysarm.sysdemo.model.Person;
import net.sysarm.sysdemo.model.Picture;
import net.sysarm.sysdemo.model.Role;
import net.sysarm.sysdemo.model.Users;
import net.sysarm.sysdemo.service.AuthoritiesService;
import net.sysarm.sysdemo.service.PersonService;
import net.sysarm.sysdemo.service.UsersService;
import net.sysarm.sysdemo.util.GeradorSenha;
import net.sysarm.sysdemo.util.MySessionInfo;

/**
 * Faz o controle do domínio de usuários
 * @author armandosoaressousa
 *
 */
@Controller
public class UserController {
	private Users loginUser;
	@Autowired
	private UsersService userService;
	@Autowired
	private AuthoritiesService authoritiesService;
	@Autowired
	private PersonService personService;	
	@Autowired
	private MySessionInfo mySessionInfo;
	
	/*
	 * Atualiza os dados da sessao do usuario logado
	 */
	private void checkUser() {
		loginUser = mySessionInfo.getCurrentUser();
	}
	
	/**
	 * Lista os usuarios da primeira pagina cadastrados 
	 * @param model
	 * @return pagina com todos os usuarios cadastrados
	 */
	@RequestMapping(value = "/users")
	public String index(final Model model) {
		return "redirect:/users/1";
	}

	/**
	 * Dado um model faz a atribuição dos dados de loginuser
	 * 
	 * @param model Model que vai receber os dados de atributos de loginuser
	 * @return model com os dados de loginuser setados
	 */
	private Model setLoginAttributeToModel(final Model model) {
		model.addAttribute("loginusername", loginUser.getUsername());
		model.addAttribute("loginemailuser", loginUser.getEmail());
		model.addAttribute("loginuserid", loginUser.getId());
		model.addAttribute("loginuser", loginUser);
		return model;
	}

	/**
	 * Lista todos os usuarios paginados cadastrados
	 * 
	 * @param model
	 * @return pagina com todos os usuarios cadastrados
	 */
	@RequestMapping(value = "/users/all")
	public String all(Model model) {
		final List<Users> list = userService.getAll();

		checkUser();
		model = setLoginAttributeToModel(model);
		model.addAttribute("list", list);

		return "users/listAllUsers";
	}

	/**
	 * Faz a paginação da lista de usuários cadastrado
	 * 
	 * @param pageNumber
	 * @param model
	 * @return pagina contendo os usuarios paginados pelo pageNumber
	 */
	@RequestMapping(value = "/users/{pageNumber}", method = RequestMethod.GET)
	public String listUsersByPagination(@PathVariable final Integer pageNumber, Model model) {
		final Page<Users> page = this.userService.getList(pageNumber);
		checkUser();

		final int current = page.getNumber() + 1;
		final int begin = Math.max(1, current - 5);
		final int end = Math.min(begin + 10, page.getTotalPages());

		model.addAttribute("list", page);
		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model = setLoginAttributeToModel(model);

		return "users/list";
	}

	/**
	 * Faz o cadastro de um novo usuário
	 * 
	 * @param model
	 * @return formulario para cadastrar novo usuario
	 */
	@RequestMapping("/users/add")
	public String add(Model model) {
		checkUser();
		final Users user = new Users();
		model.addAttribute("user", user);
		model = setLoginAttributeToModel(model);

		return "users/form";
	}

	/**
	 * Edita um usuário selecionado
	 * 
	 * @param id    do usuario
	 * @param model
	 * @return formulario de edicao do usuario com checagem de password
	 */
	@RequestMapping("/users/edit/{id}")
	public String edit(@PathVariable final Long id, Model model) {
		final Users editUser = userService.get(id);
		final Person person = editUser.getPerson();
		checkUser();

		model.addAttribute("user", editUser);
		model.addAttribute("person", person);
		model = setLoginAttributeToModel(model);

		return "users/formpwd";
	}

	/**
	 * Mostra os detalhes do usuario selecionado
	 * 
	 * @param id    Id do Usuario
	 * @param model Dados do Usuario
	 * @return aboutUser.html
	 */
	@RequestMapping("/users/about/{id}")
	public String about(@PathVariable final Long id, Model model) {
		final Users aboutUser = userService.get(id);
		final Person person = aboutUser.getPerson();
		checkUser();

		model.addAttribute("user", aboutUser);
		model.addAttribute("person", person);
		model = setLoginAttributeToModel(model);

		return "users/aboutUser";
	}

	/**
	 * Edita profile do usuário logado
	 * 
	 * @param id    do usuario logado
	 * @param model
	 * @return formulario de edicao de profile do usuario
	 */
	@RequestMapping("/users/edit/profile/{id}")
	public String editProfile(@PathVariable final Long id, Model model) {
		checkUser();
		final Users user = this.userService.get(loginUser.getId());
		final Person person = user.getPerson();

		model.addAttribute("user", loginUser);
		model.addAttribute("person", person);
		model = setLoginAttributeToModel(model);

		return "users/formpwdProfile";

	}

	/**
	 * Salva os dados de um usuário novo
	 * 
	 * @param user            usuario
	 * @param password        senha
	 * @param confirmPassword confirma senha
	 * @param nome            tipo de acesso ao sistema
	 * @param ra
	 * @return pagina de usuarios com o novo usuario
	 */
	@RequestMapping(value = "/users/save", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("user") final Users user, final BindingResult bindingResult,
			@RequestParam("password") final String password,
			@RequestParam("confirmpassword") final String confirmPassword, @RequestParam("nome") final String authority,
			final RedirectAttributes ra, Model model) {
		String senhaCriptografada;
		final List<Role> roles = new LinkedList<>();

		/**
		 * Caso exista algum erro de entrada de dados vai para a página de listagem de
		 * usuários
		 */
		if (bindingResult.hasErrors()) {
			checkUser();
			model = setLoginAttributeToModel(model);
			return "users/form";
		}

		final String username = user.getUsername();
		final String emailUser = user.getEmail();
		final Users userExists = this.userService.getUserByUserName(username);
		final Users emailExists = this.userService.getUserByEmail(emailUser);

		// TODO melhorar o tratamento de checagem de usuario e/ou e-mail existente
		if ((userExists != null) || (emailExists != null)) {
			throw new UserAlreadyExistsException("Usuário já existe!");
		} else {
			/*
			 * Checa o tipo do usuário O usuário básico é o USER
			 */
			switch (authoritiesService.checkAuthority(authority)) {
			case "USER":
				roles.add(authoritiesService.getRoleByNome("USER"));
				user.setRoles(roles);
				break;
			default:
				ra.addFlashAttribute("errorFlash", "A permissão não está registrada no sistema!");
				break;
			}

			if (password.equals(confirmPassword)) {
				senhaCriptografada = new GeradorSenha().criptografa(password);
				user.setPassword(senhaCriptografada);
				// Criar uma pessoa vazia e associa ao novo usuário
				final Person person = new Person();
				person.setUser(user);

				user.setPerson(person);
				final Users save = userService.save(user);
				ra.addFlashAttribute("successFlash", "Usuário foi salvo com sucesso.");
			} else {
				ra.addFlashAttribute("errorFlash", "A senha do usuário NÃO confere.");
			}

			return "redirect:/users/1";
		}
	}

	/**
	 * Salva as alterações do usuário editado
	 * 
	 * @param user               novos dados do usuário
	 * @param originalPassword   senha original registrada no banco
	 * @param newPassword        nova senha passada pelo usuário
	 * @param confirmnewpassword compara se é igual a newPassword
	 * @param ra                 redireciona os atributos
	 * @return página que lista todos os usuários
	 */
	@RequestMapping(value = "/users/saveedited", method = RequestMethod.POST)
	public String saveEdited(final Users user, @RequestParam("password") final String originalPassword,
			@RequestParam("newpassword") final String newPassword,
			@RequestParam("confirmnewpassword") final String confirmNewPassword, final RedirectAttributes ra) {

		final Users userOriginal = userService.get(user.getId());
		final String recuperaPasswordBanco = userOriginal.getPassword();
		final Person personOriginal = userOriginal.getPerson();
		final List<Role> roles = userOriginal.getRoles();
		final String local = "";

		user.setRoles(roles);
		user.setPerson(personOriginal);

		if (newPassword.equals(confirmNewPassword)) {
			if (new GeradorSenha().comparaSenhas(originalPassword, recuperaPasswordBanco)) {
				final String novaSenhaCriptografada = new GeradorSenha().criptografa(newPassword);
				user.setPassword(novaSenhaCriptografada);
				final Users save = userService.save(user);
				ra.addFlashAttribute("successFlash", "Usuário " + user.getUsername() + " foi alterado com sucesso.");

			} else {
				ra.addFlashAttribute("errorFlash", "A senha informada é diferente da senha original.");
			}
		} else {
			ra.addFlashAttribute("errorFlash", "A nova senha não foi confirmada.");
		}
		return "redirect:/users/edit/profile/" + user.getId();
	}

	/**
	 * Salva as alterações do usuário editado
	 * 
	 * @param user               novos dados do usuário
	 * @param originalPassword   senha original registrada no banco
	 * @param newPassword        nova senha passada pelo usuário
	 * @param confirmnewpassword compara se é igual a newPassword
	 * @param ra                 redireciona os atributos
	 * @return página que lista todos os usuários
	 */
	@RequestMapping(value = "/users/personsaveedited", method = RequestMethod.POST)
	public String savePersonEdited(final Person person, final RedirectAttributes ra) {
		final String local = "";

		final Person original = this.personService.get(person.getId());

		final List<Picture> pictures = original.getPictures();

		person.setPictures(pictures);
		this.personService.update(person);
		ra.addFlashAttribute("successFlash",
				"Os dados pessoais do " + person.getUser().getUsername() + " foram alterados com sucesso.");

		return "redirect:/users/edit/" + person.getUser().getId();
	}

	/**
	 * TODO: Checar as dependencias de usuario. Usuario tem lista de permissoes e
	 * usuario tem lista de amigos. Remove um usuário selecionado
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/users/delete/{id}")
	public String delete(@PathVariable final Long id, final Model model, final RedirectAttributes ra) {
		String mensagem = "";
		String nome = "";
		final Users userToDelete = this.userService.get(id);

		this.userService.delete(id);
		nome = userToDelete.getUsername();
		mensagem = "Usuário " + nome + " removido com sucesso!";

		ra.addFlashAttribute("successFlash", mensagem);
		return "redirect:/users";
	}

	/**
	 * Lista todos os usuários disponíveis
	 * 
	 * @param model
	 * @return pagina com todos os usuarios cadastrados
	 */
	@RequestMapping(value = "/users/list", method = RequestMethod.GET)
	public String listAllUsers(Model model) {
		final List<Users> users = this.userService.getAll();
		checkUser();

		model.addAttribute("list", users);
		model = setLoginAttributeToModel(model);

		return "users/listAllUsers";
	}

	/**
	 * Seleciona uma imagem de um usuario
	 * 
	 * @param idUser id do usuario
	 * @param model
	 * @return um formulario para fazer o upload de uma imagem do perfil do usuario
	 */
	@RequestMapping(value = "/users/{idUser}/select/image")
	public String selectImage(@PathVariable(value = "idUser") final Long idUser, final Model model) {
		final Users editUser = userService.get(idUser);
		checkUser();

		model.addAttribute("user", editUser);
		model.addAttribute("loginusername", loginUser.getUsername());
		model.addAttribute("loginemailuser", loginUser.getEmail());
		model.addAttribute("loginuserid", loginUser.getId());
		model.addAttribute("idUser", editUser.getId());
		model.addAttribute("username", editUser.getUsername());
		model.addAttribute("loginuser", loginUser);

		return "users/formImage";
	}

	/**
	 * TODO é preciso zerar a sessão do usuário Return registration form template
	 * 
	 * @param model
	 * @param user  novo usuario que sera registrado
	 * @return formulario para registro de um novo usuario. Um novo usuario recebe a
	 *         permissao padrao USER.
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegistrationPage(final Model model) {
		model.addAttribute("user", new Users());
		return "/register";
	}

	// TODO Revisar a forma de registra das permissões do usuário
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String processRegistrationForm(final Model model, final Users user,
			@RequestParam("password") final String password,
			@RequestParam("confirmpassword") final String confirmPassword,
			@RequestParam("authority") final String authority, final RedirectAttributes ra) {

		final String username = user.getUsername();
		final String emailUser = user.getEmail();
		final Users userExists = this.userService.getUserByUserName(username);
		final Users emailExists = this.userService.getUserByEmail(emailUser);

		// TODO: melhorar o tratamento de checagem de usuário ou e-mail existente
		if ((userExists != null) || (emailExists != null)) {
			ra.addFlashAttribute("msgerro", "Usuário ou e-mail já existe!");
			return "redirect:/register";
		} else {
			// checa a senha do usuário
			if (password.equals(confirmPassword)) {
				final String senhaCriptografada = new GeradorSenha().criptografa(password);

				user.setPassword(senhaCriptografada);
				user.setEnabled(true);
				Role authorities = new Role();
				final Person person = new Person();
				person.setUser(user);

				// checa o tipo do usuário
				if (authority.equals("USER")) {
					authorities = authoritiesService.getRoleByNome("USER");
					final List<Role> roles = new LinkedList<>();
					roles.add(authorities);
					user.setRoles(roles);
					user.setPerson(person);
					this.userService.save(user);
					model.addAttribute("msg", "Usuário comum registrado com sucesso!");
					return "/login";
				}

				// checa demais tipos de permissões

			} else {
				ra.addFlashAttribute("msgerro", "Senha não confere!");
				return "redirect:/register";
			}

		}
		return "/login";
	}

	@RequestMapping(value = "/users/search", method = RequestMethod.POST)
	public String search(Model model, @RequestParam("search") final String search, final RedirectAttributes ra) {
		final Users userExists = this.userService.getUserByUserName(search);
		
		if (userExists == null) {
			ra.addFlashAttribute("errorFlash", "Usuário " + search +  " pesquisado não existe!");
			return "redirect:/users/list";
		}
		
    	checkUser();
    	
        model.addAttribute("list", userExists);
        model = setLoginAttributeToModel(model);
        
        return "users/listAllUsers";
	
	}
	
}