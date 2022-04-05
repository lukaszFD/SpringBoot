package pl.lukasz.fd.restapi.DB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lukasz.fd.restapi.Model.Accounts;

@Repository
public interface IGlobalRepository extends JpaRepository <Accounts,Long> { }