package me.tbandawa.web.skyzmetro.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.tbandawa.web.skyzmetro.daos.UserDao;
import me.tbandawa.web.skyzmetro.entities.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
  private final UserDao userDao;

  public UserDetailsServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userDao.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + username));

    return UserDetailsImpl.build(user);
  }
}
