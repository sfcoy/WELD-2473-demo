package au.com.demo;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.jboss.weld.environment.se.bindings.Parameters;
import org.jboss.weld.environment.se.events.ContainerInitialized;

@ApplicationScoped
public class MyApplication {

    private final String config;

    @Inject
    public MyApplication(@Configuration String config) {
        this.config = config;
    }


    void run(@Observes ContainerInitialized event, @Parameters String[] args) {
        System.out.println("Hello " + config);
    }

}
