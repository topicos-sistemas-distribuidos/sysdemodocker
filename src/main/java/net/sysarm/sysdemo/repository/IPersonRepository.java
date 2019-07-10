package net.sysarm.sysdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.sysarm.sysdemo.model.Person;
import net.sysarm.sysdemo.model.Users;

/**
 * Interface repositório de Usuário baseada no JPARepository do Spring
 * @author armandosoaressousa
 *
 */
@Repository
public interface IPersonRepository extends JpaRepository<Person, Long>{
	Person findByUser(Users user);
	Person findByName(String name);
}