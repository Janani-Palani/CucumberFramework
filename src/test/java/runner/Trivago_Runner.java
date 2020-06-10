package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features="src/test/java/features/Trivago.feature",glue="steps",monochrome=true)
public class Trivago_Runner extends AbstractTestNGCucumberTests{

	
	
}
