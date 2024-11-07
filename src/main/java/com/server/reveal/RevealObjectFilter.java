package com.server.reveal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;

import com.infragistics.reveal.sdk.api.IRVObjectFilter;
import com.infragistics.reveal.sdk.api.IRVUserContext;
import com.infragistics.reveal.sdk.api.model.RVDashboardDataSource;
import com.infragistics.reveal.sdk.api.model.RVDataSourceItem;
import com.infragistics.reveal.sdk.api.model.RVMySqlDataSource;
import com.infragistics.reveal.sdk.api.model.RVMySqlDataSourceItem;

    // ****
    // https://help.revealbi.io/web/user-context/#using-the-user-context-in-the-objectfilterprovider
    // ObjectFilter Provider is optional.
    // The Filter functions allow you to control the data sources dialog  on the client.
    // ****


    // ****
    // NOTE:  This is ignored of it is not set in the Builder in RevealJerseyConfig.java --> setObjectFilter(new RevealObjectFilter())
    // ****

public class RevealObjectFilter implements IRVObjectFilter {
    
    @Override
    public boolean filter(IRVUserContext userContext, RVDashboardDataSource dataSource) {
        
        // ****
        // In the scenario I only want the northwind database to be displayed in the Data Sources. 
        // ****
        
        String[] allowedList = { "northwind" }; 
        if (dataSource != null)
        {
            if (dataSource instanceof RVMySqlDataSource dataSQL) 
            {
                if (Arrays.asList(allowedList).contains(dataSQL.getDatabase())) {
                    return true;
                }
            }
        }
        return false; 
    }

    @Override
    public boolean filter(IRVUserContext userContext, RVDataSourceItem dataSourceItem) {
        // ****
        // In the scenario I am using the Roles that are set up in the UserContext Provider to check the 
        // Role property to restrict what is displayed in the Data Sources.
        // If the logged in user is an Admin role, they see all the Tables, Views, Sprocs, if not, 
        // they will only see the 'All Orders' and 'Invoices' tables - Admin role is 11 in this scenario.
        // ****
        
        Object roleObj = userContext.getProperties().get("Role");
        if (roleObj != null && "admin".equalsIgnoreCase(roleObj.toString())) {
            String[] excludedList = { "customers", "orders", "customer_orders", "customer_orders_details" }; 

            if (dataSourceItem != null) {
                if (dataSourceItem instanceof RVMySqlDataSourceItem dataSQLItem) {
                    if (!Arrays.asList(excludedList).contains(dataSQLItem.getTable())) {
                        return false;
                    }
                }
            }
        }
        return true; 
    }
    
    
}