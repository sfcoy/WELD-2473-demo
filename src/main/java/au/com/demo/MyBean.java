package au.com.demo;

import java.util.Optional;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import org.jboss.weld.environment.se.bindings.Parameters;

@Dependent
public class MyBean {

    private final String[] args;

    @Inject
    MyBean(@Parameters String[] args) {
        this.args = args;
    }

    @Produces
    @Configuration
    String produceConfiguration(InjectionPoint ip) {
        final Annotated annotated = ip.getAnnotated();
        if (annotated.isAnnotationPresent(Configuration.class)) {
            final String defaultParameterName = annotated instanceof AnnotatedParameter ?
              ((AnnotatedParameter) annotated).getJavaParameter().getName() : ip.getMember().getName();
            return lookupCommandLineArgumentValue(defaultParameterName).orElse("mystery person");
        }
        return null;
    }

    Optional<String> lookupCommandLineArgumentValue(String configElementName) {
        final String parameterName = "--" + configElementName;
        for (int parameterIndex = 0; parameterIndex < args.length; ++parameterIndex) {
            if (args[parameterIndex].equals(parameterName)) {
                if (parameterIndex < args.length - 1) {
                    return Optional.of(args[parameterIndex + 1]);
                }
            }
        }
        return Optional.empty();
    }
}
