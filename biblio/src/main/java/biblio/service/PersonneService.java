package biblio.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import biblio.dao.IDAOPersonne;
import biblio.model.Personne;

@Service
public class PersonneService {

    @Autowired
    private IDAOPersonne dao;

    @Autowired
    private PasswordEncoder passwordEncoder; 

    public Personne create(Personne p) {
        p.setPassword(passwordEncoder.encode(p.getPassword()));
        return dao.save(p);
    }

    public Optional<Personne> getByLogin(String login) {
        return dao.findByLogin(login);
    }

    public Personne update(Personne p) {
        if (p.getPassword() != null) {
            p.setPassword(passwordEncoder.encode(p.getPassword()));
        }
        return dao.save(p);
    }

    public void deleteById(Integer id) {
        dao.deleteById(id);
    }

    public void delete(Personne p) {
        dao.delete(p);
    }

    public Optional<Personne> login(String login, String rawPassword) {
        Optional<Personne> opt = dao.findByLogin(login);
        if (opt.isPresent() && passwordEncoder.matches(rawPassword, opt.get().getPassword())) {
            return opt;
        }
        return Optional.empty();
    }
}
