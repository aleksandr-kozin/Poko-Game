package com.mipsas.poko.security.filter;

import com.mipsas.poko.api.service.MetaDataService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class UserMetaDataFilter extends OncePerRequestFilter {
    private final MetaDataService metaDataService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        metaDataService.saveMetaData(request);
        filterChain.doFilter(request, response);
    }
}
