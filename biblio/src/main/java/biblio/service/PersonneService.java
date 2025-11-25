package biblio.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import biblio.dao.IDAOPersonne;
import biblio.model.Personne;

@Service
public class PersonneService {
    private static final Logger log = LoggerFactory.getLogger(PersonneService.class);

    @Autowired
    private IDAOPersonne dao;

    @Autowired
    private PasswordEncoder passwordEncoder; 

    public Personne create(Personne p) {
        log.info("Ajouter une nouvelle personne");
        p.setPassword(passwordEncoder.encode(p.getPassword()));
        return dao.save(p);
    }

    public Optional<Personne> getByLogin(String login) {
        log.info("FindByLogin");
        return dao.findByLogin(login);
    }

    public Personne update(Personne p) {
        log.info("Update personne");
        if (p.getPassword() != null) {
            p.setPassword(passwordEncoder.encode(p.getPassword()));
        }
        return dao.save(p);
    }

    public void deleteById(Integer id) {
        log.info("Delete personne id {} ",id);
        dao.deleteById(id);
    }

    public void delete(Personne p) {
        log.info("Delete personne");
        dao.delete(p);
    }

    public Optional<Personne> login(String login, String rawPassword) {
        log.info("Login ");
        Optional<Personne> opt = dao.findByLogin(login);
        if (opt.isPresent() && passwordEncoder.matches(rawPassword, opt.get().getPassword())) {
            log.info("Login réussi");
            return opt;
        }
        return Optional.empty();
    }
}
