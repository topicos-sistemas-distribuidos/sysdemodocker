package net.sysarm.sysdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import net.sysarm.sysdemo.model.Person;
import net.sysarm.sysdemo.model.Users;
import net.sysarm.sysdemo.repository.IPersonRepository;

/**
 * Class the manipulate the repository of Person
 * @author armandosoaressousa
 *
 */
@Service
public class PersonService extends AbstractService<Person, Long>{
	@Autowired
	IPersonRepository personRepository;
	
	//public PromotionArea globalPromotionArea = PromotionArea.getInstance();

	@Override
	protected JpaRepository<Person, Long> getRepository() {
		return personRepository;
	}
	
	/**
	 * Find a person by User
	 * @param user User
	 * @return Person
	 */
	public Person getPersonByUser(Users user) {
		return personRepository.findByUser(user);
	}
	
	/**
	 * Find a person by name
	 * @param name 
	 * @return Person
	 */
	public Person getPersonByName(String name) {
		return personRepository.findByName(name);
	}
		
}