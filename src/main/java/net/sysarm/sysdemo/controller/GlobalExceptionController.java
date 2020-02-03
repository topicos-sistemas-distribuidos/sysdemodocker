package net.sysarm.sysdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.sysarm.sysdemo.exception.UserAlreadyExistsException;
import net.sysarm.sysdemo.exception.UserNotFoundException;
import net.sysarm.sysdemo.model.Users;
import net.sysarm.sysdemo.util.MySessionInfo;

@ControllerAdvice
public class GlobalExceptionController{
	@Autowired
	private MySessionInfo mySessionInfo;
    private Users loginUser;
    /*
	 * Atualiza os dados da sessao do usuario logado
	 */
	private void checkUser() {
		loginUser = mySessionInfo.getCurrentUser();
	}

    @ExceptionHandler({UserAlreadyExistsException.class})
    public String handleUserAlreadyExistsException(UserAlreadyExistsException exception, Model model, final RedirectAttributes ra) {
        checkUser();
        model.addAttribute("loginusername", loginUser.getUsername());
        model.addAttribute("loginemailuser", loginUser.getEmail());
        model.addAttribute("loginuserid", loginUser.getId());
        model.addAttribute("loginuser", loginUser);
        ra.addFlashAttribute("errorFlash", exception.getMessage());
        return "redirect:/users/1";			
    }
}