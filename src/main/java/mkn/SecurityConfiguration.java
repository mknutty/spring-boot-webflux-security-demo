package mkn;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import io.jsonwebtoken.SignatureAlgorithm;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {
  static final String KEY = "secret01234567890ABCDEFGHIJKLMNO";
  
	@Bean
	SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
		serverHttpSecurity
			.authorizeExchange(exchanges -> 
				exchanges
				  .pathMatchers("/jwt").permitAll()
					.anyExchange().authenticated()
			)
			.oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(withDefaults()));
		return serverHttpSecurity.build();
	}
	
	@Bean
	ReactiveJwtDecoder reactiveJwtDecoder() {
	  SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
	  return NimbusReactiveJwtDecoder.withSecretKey(secretKey).build();
	}
}
