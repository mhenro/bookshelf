package ru.khadzhinov.bookshelf.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import ru.khadzhinov.bookshelf.entity.MyUser;

@Repository
@Transactional
@Service("userService")
public class UserServiceImpl implements IUserService, UserDetailsService {

    private final IUserRepository userRepository;

    @Autowired
    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public MyUser getUserById(long id) {
        return userRepository.findOne(id);
    }

    @Override
    public MyUser getUserByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }
    
    @Override
    public MyUser getUserByToken(String token) {
    	return userRepository.findOneByToken(token);
    }

    @Override
    public List<MyUser> getAllUsers() {
        return Lists.newArrayList(userRepository.findAll());
    }

    @Override
    public MyUser save(MyUser user) {
        return userRepository.save(user);
    }

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		
		MyUser myUser = getUserByEmail(email);
		
		Collection<SimpleGrantedAuthority> authArr = new ArrayList<SimpleGrantedAuthority>();
		SimpleGrantedAuthority auth = new SimpleGrantedAuthority(myUser.getRole().toString());
		authArr.add(auth);
		
		//username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities
		UserDetails user = new User(myUser.getEmail(), myUser.getPasswordHash(), myUser.getEnabled(), 
				true, true, true, authArr);
		
		return user;
	}
}