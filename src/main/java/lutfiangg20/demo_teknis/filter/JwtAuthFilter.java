package lutfiangg20.demo_teknis.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lutfiangg20.demo_teknis.service.JwtService;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  @Autowired
  private JwtService jwtService; // service kamu buat validasi JWT

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws IOException, ServletException {

    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7);
      var claims = jwtService.validate(token);
      if (claims != null && !claims.isEmpty()) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            claims.get("userId"), // bisa pakai ID user
            null,
            List.of(new SimpleGrantedAuthority("ROLE_" + claims.get("role"))));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }

    filterChain.doFilter(request, response);
  }

}
