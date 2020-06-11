package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features="src/test/java/features/Ajio.feature",glue="steps",monochrome=true)
public class Ajio_Runner extends AbstractTestNGCucumberTests{

	
	
}
