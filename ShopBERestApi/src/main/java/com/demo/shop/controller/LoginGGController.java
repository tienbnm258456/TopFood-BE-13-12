package com.demo.shop.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.shop.config.JwtUtils;
import com.demo.shop.constant.StatusConstant;
import com.demo.shop.entity.Role;
import com.demo.shop.entity.TokenDto;
import com.demo.shop.entity.User;
import com.demo.shop.repository.UserRepository;
import com.demo.shop.service.RoleService;
import com.demo.shop.service.impl.UserDetailsImpl;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

@RestController
@RequestMapping("/api")
public class LoginGGController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private AuthenticationManager authenticationManager;
	@Autowired
    private JwtUtils jwtProvider;
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleService roleService;
	@Value("${google.app.id}")
    String googleClientId;

    @Value("${google.app.secret}")
    String secretPsw;
	@PostMapping("/google")
    public ResponseEntity<?> google(@RequestBody TokenDto tokenDto) throws IOException {
        final NetHttpTransport transport = new NetHttpTransport();
        final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier.Builder verifier =
                new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                .setAudience(Collections.singletonList(googleClientId));
        final GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(), tokenDto.getToken());
        final GoogleIdToken.Payload payload = googleIdToken.getPayload();
        User user = new User();
        if(userRepository.existsByUserName(payload.getEmail()))
            user = userRepository.findByUserNameAndStatus(payload.getEmail(), 1).get();
        
        else
         user = new User(payload.getEmail(), passwordEncoder.encode(secretPsw));
        Role rolUser = roleService.getByRolNombre("ROLE_USER").get();
        Set<Role> roles = new HashSet<>();
        roles.add(rolUser);
        user.setRoles(roles);
        user.setStatus(StatusConstant.STATUS_ACTIVE);
        user.setCreateDate(new Date());
        user.setEmail(payload.getEmail());
        userRepository.save(user);
        TokenDto tokenRes = login(user);
        return new ResponseEntity<>(tokenRes, HttpStatus.OK);
    }
	
	 @PostMapping("/facebook")
	    public ResponseEntity<TokenDto> facebook(@RequestBody TokenDto tokenDto) throws IOException {
	        Facebook facebook = new FacebookTemplate(tokenDto.getToken());
	        final String [] fields = {"email", "picture","first_name","last_name"};
	        org.springframework.social.facebook.api.User user = facebook.fetchObject("me", org.springframework.social.facebook.api.User.class, fields);
	        User usuario = new User();
	        if(userRepository.existsByUserName(user.getId()))
	            usuario = userRepository.findByUserNameAndStatus(user.getId(),1).get();
	        else
	        usuario = new User(user.getId(), passwordEncoder.encode(secretPsw));
	        Role rolUser = roleService.getByRolNombre("ROLE_USER").get();
	        Set<Role> roles = new HashSet<>();
	        roles.add(rolUser);
	        usuario.setRoles(roles);
	        usuario.setStatus(StatusConstant.STATUS_ACTIVE);
	        usuario.setCreateDate(new Date());
	        usuario.setFirstName(user.getFirstName());
	        usuario.setLastName(user.getLastName());
	        userRepository.save(usuario);
	        TokenDto tokenRes = login(usuario);
	        return new ResponseEntity<>(tokenRes, HttpStatus.OK);
	    }
	
	private TokenDto login(User usuario){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuario.getUserName(),secretPsw)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
        TokenDto jwtRes = new TokenDto(jwt, usuario.getId(), usuario.getUserName(), roles);
        return jwtRes;
    }

    private User saveUsuario(String email){
        User user = new User(email, passwordEncoder.encode(secretPsw));
        Role rolUser = roleService.getByRolNombre("ROLE_USER").get();
        Set<Role> roles = new HashSet<>();
        roles.add(rolUser);
        user.setRoles(roles);
        user.setStatus(StatusConstant.STATUS_ACTIVE);
        user.setCreateDate(new Date());
        return userRepository.save(user);
    }
    private User saveUsuarioFB(String id){
        User user = new User(id, passwordEncoder.encode(secretPsw));
        Role rolUser = roleService.getByRolNombre("ROLE_USER").get();
        Set<Role> roles = new HashSet<>();
        roles.add(rolUser);
        user.setRoles(roles);
        user.setStatus(StatusConstant.STATUS_ACTIVE);
        user.setCreateDate(new Date());
        return userRepository.save(user);
    }
}
