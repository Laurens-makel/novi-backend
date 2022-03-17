package student.laurens.novibackend.services;

import org.junit.runner.RunWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public abstract class ServiceIntegrationTestBase {

}
