package rest.interceptors;

import model.User;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import rest.controller.LoginController;
import rest.response.ResponseFactory;

import javax.annotation.Priority;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author - Srđan Milaković
 */
@Provider
@Priority(1000)
public class AuthorizationInterceptor implements ContainerRequestFilter {

    @Context
    private HttpServletRequest webRequest;
    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Method methodInvoked = resourceInfo.getResourceMethod();

        if (methodInvoked.isAnnotationPresent(RolesAllowed.class)) {
            RolesAllowed rolesAllowedAnnotation = methodInvoked.getAnnotation(RolesAllowed.class);
            Set<String> rolesAllowed = new HashSet<>(Arrays.asList(rolesAllowedAnnotation.value()));

            User user = (User) webRequest.getSession().getAttribute(LoginController.USER_IDENTIFIER);

            if (user == null || !rolesAllowed.contains(user.getType())) {
                requestContext.abortWith(ResponseFactory.createErrorResponse(Response.Status.UNAUTHORIZED));
            } else {
                ResteasyProviderFactory.pushContext(User.class, user);
            }
        }
    }

}
