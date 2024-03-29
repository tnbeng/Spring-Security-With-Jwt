package in.tnb.main.securityconfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidator extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt=request.getHeader(JWTConstant.jwt_header);
		
		if(jwt!=null)
		{
			try
			{
				
				String email=JwtProvider.getEmailFormToken(jwt);
				
				List<GrantedAuthority> authorities=new ArrayList<>();
				Authentication authentication=new UsernamePasswordAuthenticationToken(email,null, authorities);
				SecurityContextHolder.getContext().setAuthentication(authentication);// we must write this to inform spring security that the request is authenticated
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		
		filterChain.doFilter(request, response);//This line passes the request and response objects to the next filter in the chain. It allows the request to continue processing after the JWT validation/authentication logic has been applied.
		
		
	}

	
}
