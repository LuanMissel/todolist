package br.com.luanmissel.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.luanmissel.todolist.users.IUserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component // é uma anottation que vai falar para o spring que ele deve gerenciar essa classe também, por meio dos beans
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var servletPath = request.getServletPath();
        if (servletPath.startsWith("/tasks/")) {

            // pegar a autenticação (usuario e senha)
            var authorization = request.getHeader("Authorization");
            var userPassword = authorization.substring("Basic".length()).trim();

            System.out.println("AUTORIZACAO");
            System.out.println(userPassword);

            byte[] decodedString = Base64.getDecoder().decode(userPassword);

            String decodeString = new String(decodedString, StandardCharsets.UTF_8);

            String[] credentials = decodeString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            // validar usuario
            var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(401);
                return;
            }
            // validar senha
            var result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
            if (result.verified) {
                // segue viagem
                request.setAttribute("idUser", user.getId());
                filterChain.doFilter(request, response);
            } else {
                response.sendError(401);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}

