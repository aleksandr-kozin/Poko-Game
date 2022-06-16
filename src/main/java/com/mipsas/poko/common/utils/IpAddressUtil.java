package com.mipsas.poko.common.utils;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

public class IpAddressUtil {
    public static final String X_FORWARDED_FOR_HEADER = "x-forwarded-for";

    public static String extractIp(HttpServletRequest request) {
        return Optional
                .ofNullable(request.getHeader(X_FORWARDED_FOR_HEADER))
                .map(IpAddressUtil::parseXForwardedHeader)
                .orElse(request.getRemoteAddr());
    }

    public static String parseXForwardedHeader(String xForwardedHeader) {
        return xForwardedHeader.split(" *, *")[0];
    }
}
