package mkn;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {
  public boolean validateIdentity(String accountId) {
    return SecurityController.IDENTITY.equals(accountId);
  }
}
