package com.mipsas.poko.security.jwt;

import com.mipsas.poko.data.entity.UserLocationEntity;
import com.mipsas.poko.data.repository.LocationRepository;
import com.mipsas.poko.data.repository.UserRepository;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class LocationFilter extends GenericFilterBean {
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        locationRepository.save(UserLocationEntity.builder()
                        .latitude(25.25863)
                        .longitude(55.22222222)
                        .user(userRepository.getReferenceById(1L))
                .build());
    }
}
