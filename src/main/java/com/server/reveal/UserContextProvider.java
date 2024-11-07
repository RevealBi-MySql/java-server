package com.server.reveal;

import com.infragistics.reveal.sdk.api.IRVUserContext;
import com.infragistics.reveal.sdk.base.RVUserContext;
import com.infragistics.reveal.sdk.rest.RVContainerRequestAwareUserContextProvider;
import java.util.HashMap;
import jakarta.ws.rs.container.ContainerRequestContext;

    // ****
    // https://help.revealbi.io/web/user-context/ 
    // The User Context is optional, but used in almost every scenario.
    // This accepts the HttpContext from the client, sent using the  $.ig.RevealSdkSettings.setAdditionalHeadersProvider(function (url).
    // UserContext is an object that can include the identity of the authenticated user of the application,
    // as well as other key information you might need to execute server requests in the context of a specific user.
    // The User Context can be used by Reveal SDK providers such as the
    // IRVDashboardProvider, IRVAuthenticationProvider, IRVDataSourceProvider
    // and others to restrict, or define, what permissions the user has.
    // ****


    // ****
    // NOTE:  This is ignored of it is not set in the Builder in RevealJerseyConfig.java -->  .setUserContextProvider(new UserContextProvider())
    // ****

public class UserContextProvider extends RVContainerRequestAwareUserContextProvider {
    @Override
    protected IRVUserContext getUserContext(ContainerRequestContext requestContext) {
  		// ****
        // Retrieve headers from the request object
        // Normally, you'd retrieve tokens or other sensitive information here
        // for setting up the security context for your data requests
        // ****
		String xHeaderOneValue = requestContext.getHeaderString("x-header-one");
        String userId = (xHeaderOneValue != null) ? xHeaderOneValue : "<NOT SET>";	

        // ****
        // Set up roles based on the incoming user ID
        // In a real application, this would be dynamically loaded based on your scenario
        // ****
        String role = "Admin";
        if ("11".equals(userId)) {
            role = "User";
        }

        // ****
        // Create a map of properties to be used in other Reveal functions
        // ****
        var props = new HashMap<String, Object>();
        props.put("EmployeeId", userId);
        props.put("Role", role);

		return new RVUserContext(userId, props); 
    }
}