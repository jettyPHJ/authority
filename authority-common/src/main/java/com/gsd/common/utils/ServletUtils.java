package com.gsd.common.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletUtils {
    public static void renderString(HttpServletResponse response, String str) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
