package net.sysarm.sysdemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.sysarm.sysdemo.model.Person;
import net.sysarm.sysdemo.model.Picture;

@Repository
public interface IPictureRepository extends JpaRepository<Picture, Long>{
	List<Picture> findByPerson(Person person);
}
