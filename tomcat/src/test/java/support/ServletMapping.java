package support;

import com.javax.servlet.Servlet;

public @interface ServletMapping {
    String path();
    Class<? extends Servlet> servlet();
}
