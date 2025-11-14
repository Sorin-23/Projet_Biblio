package biblio.config;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import biblio.dao.IDAOPersonne;
import biblio.model.Personne;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    @Autowired
    private IDAOPersonne dao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        return this.dao.findByLogin(username)
            .map(person -> User
                    .withUsername(username)
                    .password(person.getPassword())
                    .roles(person.getRole())
                    .build()
            )
            .orElseThrow(() -> new UsernameNotFoundException("User not found"))
        ;
    }
}
