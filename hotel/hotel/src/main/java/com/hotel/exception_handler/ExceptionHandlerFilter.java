package com.hotel.exception_handler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    private final List<HandlerExceptionResolver> resolvers;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ServletException | IOException e) {
            throw e;
        } catch (Exception e) {
            boolean exceptionHandled = false;
            for (HandlerExceptionResolver resolver : resolvers) {

                if (resolver.resolveException(request, response, null, e) != null) {
                    exceptionHandled = true;
                    break;
                }
            }
            if (!exceptionHandled) {
                throw new ServletException(e);
            }
        }
    }
}
