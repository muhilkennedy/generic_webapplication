package com.test.tenant;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.SuiteDisplayName;

@org.junit.platform.suite.api.Suite
@SuiteDisplayName("A demo Test Suite")
@SelectPackages({"com.test.tenant"})
@SelectClasses( { TenantTests.class, SpringbootMocitoDemoTests.class } )
//@IncludeTags("production")
public class ApplicationTestSuite {

}
