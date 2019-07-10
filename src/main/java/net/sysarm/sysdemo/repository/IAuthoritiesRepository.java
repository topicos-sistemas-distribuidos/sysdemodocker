package net.sysarm.sysdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.sysarm.sysdemo.model.Role;


@Repository
public interface IAuthoritiesRepository extends JpaRepository<Role, Long>{
	Role findByNome(String nome);
}
