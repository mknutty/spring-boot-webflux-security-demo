package mkn;

import java.security.Principal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import reactor.core.publisher.Mono;

@RestController
public class SecurityController {
  final static String IDENTITY = "1234567890";
  
  // Use this to get a jwt to pass as a bearer token
  @GetMapping("jwt")
  public String jwt() {
    return Jwts.builder()
        .setSubject(IDENTITY)
        .setId(UUID.randomUUID().toString())
        .setIssuedAt(Date.from(Instant.now()))
        .setExpiration(Date.from(Instant.now().plus(30, ChronoUnit.DAYS)))
        .claim("identity", IDENTITY)
        .signWith(SignatureAlgorithm.HS256, SecurityConfiguration.KEY.getBytes())
        .compact();
  }

  
	@GetMapping("/hello")
	@PreAuthorize("@securityService.validateIdentity(#accountId)")
	public Mono<String> index(@AuthenticationPrincipal Jwt jwt, Principal principal, @AuthenticationPrincipal(expression="claims['identity']") String accountId) {
		return Mono.just(String.format("Hello, %s!", jwt.getSubject())); 
	}

}
