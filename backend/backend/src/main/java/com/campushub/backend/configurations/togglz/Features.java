package com.campushub.backend.configurations.togglz;

import jdk.jfr.Label;
import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.context.FeatureContext;

public enum Features implements Feature {
    @Label("CreateUser")
    @EnabledByDefault
    CREATE_USER;




    public boolean isActive(){
        return FeatureContext.getFeatureManager().isActive(this);
    }

}