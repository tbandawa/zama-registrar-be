package me.tbandawa.web.skyzmetro.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import me.tbandawa.web.skyzmetro.daos.ProvinceDao;
import me.tbandawa.web.skyzmetro.dtos.ProvinceDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import me.tbandawa.web.skyzmetro.daos.RoleDao;
import me.tbandawa.web.skyzmetro.daos.TokenDao;
import me.tbandawa.web.skyzmetro.daos.UserDao;
import me.tbandawa.web.skyzmetro.dtos.LoginDto;
import me.tbandawa.web.skyzmetro.dtos.RegisterDto;
import me.tbandawa.web.skyzmetro.dtos.UserDto;
import me.tbandawa.web.skyzmetro.jwt.JwtUtils;
import me.tbandawa.web.skyzmetro.entities.User;
import me.tbandawa.web.skyzmetro.entities.UserToken;
import me.tbandawa.web.skyzmetro.exceptions.NotProcessedException;
import me.tbandawa.web.skyzmetro.exceptions.ResourceConflictException;
import me.tbandawa.web.skyzmetro.exceptions.ResourceNotFoundException;
import me.tbandawa.web.skyzmetro.entities.Role;

@Service
public class UserServiceImpl implements UserService {

	private final AuthenticationManager authenticationManager;

	private final PasswordEncoder passwordEncoder;

	private final JwtUtils jwtUtils;

	private final UserDao userDao;

	private final RoleDao roleDao;

	private final TokenDao tokenDao;

	private final UserMapper userMapper;

	private final ProvinceDao provinceDao;

	private final ProvinceMapper provinceMapper;

	public UserServiceImpl(
			AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder,
			JwtUtils jwtUtils,
			UserDao userDao,
			RoleDao roleDao,
			TokenDao tokenDao,
			UserMapper userMapper,
			ProvinceDao provinceDao,
			ProvinceMapper provinceMapper
	) {
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtils = jwtUtils;
		this.userDao = userDao;
		this.roleDao = roleDao;
		this.tokenDao = tokenDao;
		this.userMapper = userMapper;
		this.provinceDao = provinceDao;
		this.provinceMapper = provinceMapper;
	}

	@Override
	public UserDto addUser(RegisterDto request) {
		
		userDao.findByEmail(request.getEmail()).ifPresent(user -> {
			throw new ResourceConflictException("Email already exists");
		});

		ProvinceDto provinceDto = provinceDao.getProvinces().stream()
				.map(provinceMapper::mapToProvinceDto)
				.filter(dto -> dto.getName().equalsIgnoreCase(request.getDistrict()))
				.findAny()
				.orElse(null);

		if (provinceDto == null) {
			throw new NotProcessedException("Province/District not found: " + request.getDistrict());
		}
		
		User user = new User();
		user.setFirstname(request.getFirstname());
		user.setLastname(request.getLastname());
		user.setEmail(request.getEmail());
		user.setDistrict(request.getDistrict());
		user.setIsActive(request.getIsActive());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		Set<String> requestRoles = request.getRole();
	    Set<Role> roles = new HashSet<>();

	    if (requestRoles == null) {
	      Role userRole = roleDao.findByName("user")
	          .orElseThrow(() -> new ResourceNotFoundException("Role is not found."));
	      roles.add(userRole);
	    } else {
	    	requestRoles.forEach(role -> {
	        switch (role) {
	        case "admin":
	          Role adminRole = roleDao.findByName("admin")
	              .orElseThrow(() -> new ResourceNotFoundException("Role is not found."));
	          roles.add(adminRole);

	          break;
	        case "mod":
	          Role modRole = roleDao.findByName("mod")
	              .orElseThrow(() -> new ResourceNotFoundException("Role is not found."));
	          roles.add(modRole);

	          break;
	        default:
	          Role userRole = roleDao.findByName("user")
	              .orElseThrow(() -> new ResourceNotFoundException("Role is not found."));
	          roles.add(userRole);
	        }
	      });
	    }
		
		user.setRoles(roles);
		
		if (userDao.addUser(user) < 1) {
            throw new NotProcessedException("User not created");
        }

		return new UserDto(
				null,
				user.getId(),
				StringUtils.capitalize(user.getFirstname()),
				StringUtils.capitalize(user.getLastname()),
				user.getEmail(),
				user.getDistrict(),
				roles.stream().map(Role::getName).collect(Collectors.toList()),
				user.getIsActive()
		);
	}

	@Override
	public UserDto signInUser(LoginDto request) {
		
		Authentication authentication = authenticationManager.authenticate(
		        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		    SecurityContextHolder.getContext().setAuthentication(authentication);
		    String jwt = jwtUtils.generateJwtToken(authentication);
		    
		    final Claims claims = jwtUtils.getAllClaimsFromToken(jwt);
		    
		    UserToken userToken = new UserToken();
		    userToken.setUserId(Long.valueOf(claims.get("user").toString()));
		    userToken.setToken(jwt);
		    
		    tokenDao.deleteToken(Long.parseLong(claims.get("user").toString()));
		    tokenDao.addToken(userToken);
		    
		    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
		    List<String> roles = userDetails.getAuthorities().stream()
		        .map(GrantedAuthority::getAuthority)
		        .collect(Collectors.toList());
		    
		return new UserDto(
				jwt,
				userDetails.getId(),
				StringUtils.capitalize(userDetails.getFirstname()),
				StringUtils.capitalize(userDetails.getLastname()),
				userDetails.getEmail(),
				userDetails.getDistrict(),
				roles,
				userDetails.getIsActive()
			);
	}
	
	@Override
	public UserDto editUserProfile(RegisterDto request) {

		User user = new User();
		user.setId(request.getUserId());
		user.setFirstname(request.getFirstname());
		user.setLastname(request.getLastname());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setIsActive(request.getIsActive());

		Set<String> requestRoles = request.getRole();
		Set<Role> roles = new HashSet<>();

		if (requestRoles == null) {
			Role userRole = roleDao.findByName("user")
					.orElseThrow(() -> new ResourceNotFoundException("Role is not found."));
			roles.add(userRole);
		} else {
			requestRoles.forEach(role -> {
				switch (role) {
					case "admin":
						Role adminRole = roleDao.findByName("admin")
								.orElseThrow(() -> new ResourceNotFoundException("Role is not found."));
						roles.add(adminRole);

						break;
					case "mod":
						Role modRole = roleDao.findByName("mod")
								.orElseThrow(() -> new ResourceNotFoundException("Role is not found."));
						roles.add(modRole);

						break;
					default:
						Role userRole = roleDao.findByName("user")
								.orElseThrow(() -> new ResourceNotFoundException("Role is not found."));
						roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		
		if (userDao.editUser(user) == 0) {
			throw new NotProcessedException("Could not save changes");
		}
		
		return new UserDto(
				null,
				user.getId(),
				StringUtils.capitalize(user.getFirstname()),
				StringUtils.capitalize(user.getLastname()),
				user.getEmail(),
				user.getDistrict(),
				user.getRoles().stream().map(Role::getName).collect(Collectors.toList()),
				user.getIsActive()
		);
	}

	@Override
	public UserDto getUserProfile(Long id) {
		
		User user = userDao.getUser(id)
				.orElseThrow(() -> new ResourceNotFoundException("User is not found."));
		
		UserDto userDto = new UserDto();
		userDto.setUserId(user.getId());
		userDto.setFirstname(StringUtils.capitalize(user.getFirstname()));
		userDto.setLastname(StringUtils.capitalize(user.getLastname()));
		userDto.setEmail(user.getEmail());
		userDto.setDistrict(user.getDistrict());
		userDto.setRole(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
		userDto.setIsActive(user.getIsActive());
		
		return userDto;
	}

	@Override
	public String logoutUser(String token) {
		final Claims claims = jwtUtils.getAllClaimsFromToken(token.substring(7, token.length()));
        tokenDao.deleteToken(Long.valueOf(claims.get("user").toString()));
		return jwtUtils.expireToken(token.substring(7, token.length()));
	}

	@Override
	public List<UserDto> getUsers(Long id) {
		return userMapper.mapToDtoList(userDao.getUsers(id));
	}

	@Override
	public int deleteUser(long id) {
		return userDao.deleteUser(id);
	}

	@Override
	public UserDto activateUser(long id, int isActive) {
		if (userDao.activateUser(id, isActive) == 0) {
			throw new NotProcessedException("Could not change user status");
		}
		
		User user = userDao.getUser(id)
				.orElseThrow(() -> new ResourceNotFoundException("User is not found."));
		
		UserDto userDto = new UserDto();
		userDto.setUserId(user.getId());
		userDto.setFirstname(StringUtils.capitalize(user.getFirstname()));
		userDto.setLastname(StringUtils.capitalize(user.getLastname()));
		userDto.setEmail(user.getEmail());
		userDto.setDistrict(user.getDistrict());
		userDto.setRole(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
		userDto.setIsActive(user.getIsActive());
		
		return userDto;
	}
}
