
package com.okstatelibrary.spacesui.core;

import com.okstatelibrary.spacesui.models.SAMLUser;
import com.okstatelibrary.spacesui.models.SAMLUserList;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.saml2.provider.service.authentication.DefaultSaml2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.okstatelibrary.spacesui.stereotypes.CurrentUser;

/**
 * @author Damith
 *
 */
@Component
public class CurrentUserHandlerMethodArgumentResolver implements
        HandlerMethodArgumentResolver {

    /**
     *
     */
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(CurrentUser.class) != null
                && methodParameter.getParameterType().equals(User.class);
    }

    /**
     *
     */
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        if (this.supportsParameter(methodParameter)) {

            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                return null;
            }

            Object principal = authentication.getPrincipal();

            if (principal instanceof DefaultSaml2AuthenticatedPrincipal samlPrincipal) {

                HttpServletRequest request =
                        webRequest.getNativeRequest(HttpServletRequest.class);

                HttpSession session = request.getSession();

//                samlPrincipal.getAttributes().forEach(
//                        (key, value) -> System.out.println(key + " = " + value)
//                );

                String email = samlPrincipal.getFirstAttribute(
                        "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress");

                String firstName = samlPrincipal.getFirstAttribute(
                        "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/givenname");

                String lastName = samlPrincipal.getFirstAttribute(
                        "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/surname");

                String bannerId = samlPrincipal.getFirstAttribute(
                        "Banner_ID");

                // For Testing purposes with
//                String email = samlPrincipal.getFirstAttribute(
//                        "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/name");
//                String userID = email;
//                String firstName = samlPrincipal.getFirstAttribute(
//                        "http://schemas.microsoft.com/identity/claims/displayname");
//
//                String lastName = "last Name";
//
//                String bannerId = "A112";

                SAMLUser samlUser = new SAMLUser(session.getId(), firstName, lastName, bannerId, email);

                SAMLUserList.getInstance().addToArray(samlUser);

                return User.withUsername(email)
                        .password("")
                        .authorities(authentication.getAuthorities())
                        .build();
            }

            if (principal instanceof User user) {

                System.out.println("User name " + user.getUsername());

                return user;
            }

        } else {
            return WebArgumentResolver.UNRESOLVED;
        }
        return null;
    }
}