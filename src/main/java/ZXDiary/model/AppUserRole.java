package ZXDiary.model;

import org.springframework.security.core.GrantedAuthority;

public enum AppUserRole implements GrantedAuthority {
  ROLE_ADMIN, ROLE_CLIENT, ROLE_PARENT, ROLE_THERAPIST;

  public String getAuthority() {
    return name();
  }

}
