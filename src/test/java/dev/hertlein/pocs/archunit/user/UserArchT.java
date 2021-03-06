package dev.hertlein.pocs.archunit.user;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import com.tngtech.archunit.library.dependencies.SliceRule;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packages = "dev.hertlein.pocs.archunit.user", importOptions = {ImportOption.DoNotIncludeTests.class})
public class UserArchT {

    @ArchTest
    private static final ArchTests rulesWithOnionArchitectureSupport = ArchTests.in(RulesWithOnionArchitectureSupport.class);
    @ArchTest
    private static final ArchTests rulesWithLayersSupport = ArchTests.in(RulesWithLayeredArchitectureSupport.class);

    @ArchTest
    void verifyNoCycles(JavaClasses classes) {
        SliceRule rule = SlicesRuleDefinition.slices()
                .matching("dev.hertlein.pocs.archunit.user.(*)..")
                .should().beFreeOfCycles();

        rule.check(classes);
    }

    private static class RulesWithOnionArchitectureSupport {
        @ArchTest
        static void verifyPortsAndAdapters(JavaClasses classes) {
            /*
             * Evaluation of this approach:
             * + very easy to model the pattern "onionArchitecture"
             * - dependencies that don't fit to the terminology (e.g. package "configuration"), have to be ignored explicitly
             * - pattern "ports & adapters" is missing. "onionArchitecture" is similar, but differs in some points
             *      - no "ports" available: they must either be ignored or reside in package "service"
             *      - no distinction between primary and secondary adapters
             */
            ArchRule rule = onionArchitecture()
                    .ignoreDependency(resideInAPackage("..configuration.."), resideInAnyPackage(".."))
                    .ignoreDependency(resideInAPackage("..core.port.."), resideInAnyPackage(".."))
                    .domainModels("..core.model")
                    .domainServices("..core.service..")
                    .adapter("api", "..adapter.primary.api..")
                    .adapter("persistence", "..adapter.secondary.persistence..")
                    .allowEmptyShould(true);

            rule.check(classes);
        }
    }

    private static class RulesWithLayeredArchitectureSupport {
        /*
         * Evaluation of this approach:
         * + flexible & easy to model any pattern by using layers
         * - "ports & adapters"-terminology has to be created with layers manually
         * - "ports & adapters"-rules have to be defined manually
         */

        private static final String DOMAIN_MODELS = "DomainModels";
        private static final String DOMAIN_SERVICES = "DomainServices";
        private static final String PORTS_PRIMARY = "PortsPrimary";
        private static final String PORTS_SECONDARY = "PortsSecondary";
        private static final String ADAPTERS_PRIMARY = "AdaptersPrimary";
        private static final String ADAPTERS_SECONDARY = "AdaptersSecondary";
        // Glue code to put together the classes
        private static final String CONFIGURATION = "Configuration";

        @ArchTest
        static void verifyPortsAndAdapters(JavaClasses classes) {
            Architectures.LayeredArchitecture layers = layeredArchitecture()
                    .layer(DOMAIN_MODELS).definedBy("..core.model")
                    .layer(DOMAIN_SERVICES).definedBy("..core.service..")
                    .layer(PORTS_PRIMARY).definedBy("..core.port.primary..")
                    .layer(PORTS_SECONDARY).definedBy("..core.port.secondary..")
                    .layer(ADAPTERS_PRIMARY).definedBy("..adapter.primary..")
                    .layer(ADAPTERS_SECONDARY).definedBy("..adapter.secondary..")
                    .layer(CONFIGURATION).definedBy("..configuration");

            ArchRule rules = layers
                    .whereLayer(CONFIGURATION).mayNotBeAccessedByAnyLayer()
                    .whereLayer(ADAPTERS_PRIMARY).mayOnlyBeAccessedByLayers(CONFIGURATION)
                    .whereLayer(ADAPTERS_SECONDARY).mayOnlyBeAccessedByLayers(CONFIGURATION)
                    .whereLayer(PORTS_PRIMARY).mayOnlyBeAccessedByLayers(ADAPTERS_PRIMARY, DOMAIN_SERVICES, CONFIGURATION)
                    .whereLayer(PORTS_SECONDARY).mayOnlyBeAccessedByLayers(ADAPTERS_SECONDARY, DOMAIN_SERVICES, CONFIGURATION)
                    .whereLayer(DOMAIN_SERVICES).mayOnlyBeAccessedByLayers(CONFIGURATION);

            rules.check(classes);
        }

        @ArchTest
        static void verifyPortsAndAdaptersIgnoringConfiguration(JavaClasses classes) {
            Architectures.LayeredArchitecture layers = layeredArchitecture()
                    .layer(DOMAIN_MODELS).definedBy("..core.model")
                    .layer(DOMAIN_SERVICES).definedBy("..core.service..")
                    .layer(PORTS_PRIMARY).definedBy("..core.port.primary..")
                    .layer(PORTS_SECONDARY).definedBy("..core.port.secondary..")
                    .layer(ADAPTERS_PRIMARY).definedBy("..adapter.primary..")
                    .layer(ADAPTERS_SECONDARY).definedBy("..adapter.secondary..");

            ArchRule rules = layers
                    .ignoreDependency(resideInAPackage("..configuration.."), resideInAnyPackage(".."))
                    .whereLayer(DOMAIN_SERVICES).mayNotBeAccessedByAnyLayer()
                    .whereLayer(ADAPTERS_PRIMARY).mayNotBeAccessedByAnyLayer()
                    .whereLayer(ADAPTERS_SECONDARY).mayNotBeAccessedByAnyLayer()
                    .whereLayer(PORTS_PRIMARY).mayOnlyBeAccessedByLayers(ADAPTERS_PRIMARY, DOMAIN_SERVICES)
                    .whereLayer(PORTS_SECONDARY).mayOnlyBeAccessedByLayers(ADAPTERS_SECONDARY, DOMAIN_SERVICES);

            rules.check(classes);
        }
    }
}
