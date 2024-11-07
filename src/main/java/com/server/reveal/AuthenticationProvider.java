package com.server.reveal;
import com.infragistics.reveal.sdk.api.IRVAuthenticationProvider;
import com.infragistics.reveal.sdk.api.IRVDataSourceCredential;
import com.infragistics.reveal.sdk.api.IRVUserContext;
import com.infragistics.reveal.sdk.api.RVUsernamePasswordDataSourceCredential;
import com.infragistics.reveal.sdk.api.model.RVDashboardDataSource;
import com.infragistics.reveal.sdk.api.model.RVMySqlDataSource;

// ****
// https://help.revealbi.io/web/authentication/ 
// The Authentication Provider is required to set the credentials used
// in the DataSourceProvider changeDataSourceAsync to authenticate to your database
// ****

// ****
// NOTE:  This must beset in the Builder in RevealJerseyConfig.java --> .setAuthProvider(new AuthenticationProvider())
// ****

public class AuthenticationProvider implements IRVAuthenticationProvider {
    @Override
    public IRVDataSourceCredential resolveCredentials(IRVUserContext userContext, RVDashboardDataSource dataSource) {
        
        // Check that the incoming request is for the expected data source type
        // You can check the data source type, or any of the information in your UserContext to
        // set credentials
        if (dataSource instanceof RVMySqlDataSource) {

            // for MySql, add a username, password and optional domain
            // note these are just properties, you can set them from configuration, a key vault, a look up to 
            // database, etc.  They are hardcoded here for demo purposes.
            return new RVUsernamePasswordDataSourceCredential("demouser", "demopass");
        }
        return null;
    }
}