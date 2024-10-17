package com.example.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import static com.example.architecture.ArchitectureTest.ROOTPKG;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

/**
 * Architecture tests are scanning the repository for correct dependency directions and allowed
 * package references.
 */
@AnalyzeClasses(packages = ROOTPKG)
class ArchitectureTest {
    static final String ROOTPKG = "com.example";

    @ArchTest
    static final ArchRule noCyclesOnProjectSubLevels =
        slices().matching(ROOTPKG + ".(*).(*)..").should()
            .beFreeOfCycles();

    @ArchTest
    static final ArchRule apiShouldOnlyHaveInwardDependencies = classes().that()
        .resideInAPackage("..api..")
        .should().onlyDependOnClassesThat(resideInAnyPackage(AllowedPackages.builder()
            .javaEE().quarkus().testing().allowedPackages()
            .and("..api..")
            .and("..business..")
            .and("..core..")
            .build()));

    @ArchTest
    static final ArchRule businessShouldOnlyHaveInwardDependencies =
        classes().that().resideInAPackage("..business..")
            .should().onlyDependOnClassesThat(resideInAnyPackage(AllowedPackages.builder()
                .javaEE().quarkus().testing().allowedPackages()
                .and("..business..")
                .and("..core..")
                .build()));

    @ArchTest
    static final ArchRule infrastructureShouldOnlyHaveInwardDependencies = classes().that()
        .resideInAPackage("..infrastructure..")
        .should().onlyDependOnClassesThat(resideInAnyPackage(AllowedPackages.builder()
            .javaEE().quarkus().hibernate().testing().allowedPackages()
            .and("..infrastructure..")
            .and("..business..")
            .and("..core..")
            .build()))
        .allowEmptyShould(true);

    @ArchTest
    static final ArchRule clientsShouldBeInInfrastructurePackage = classes().that()
        .areAnnotatedWith(RegisterRestClient.class)
        .or().haveSimpleNameEndingWith("Client")
        .or().implement(ClientHeadersFactory.class)
        .or().implement(ResponseExceptionMapper.class)
        .should().resideInAPackage("..infrastructure..").allowEmptyShould(true);

    @ArchTest
    static final ArchRule entitiesShouldBeInInfrastructurePackage =
        classes().that().haveNameMatching(".*(Entity)")
            .should().resideInAnyPackage("..infrastructure..").allowEmptyShould(true);

    @ArchTest
    static final ArchRule entitiesMustResideInInfrastructurePackage = classes().that()
        .areAnnotatedWith("jakarta.persistence.Entity")
        .should().resideInAPackage("..infrastructure..")
        .allowEmptyShould(true)
        .as("Entities should reside in a package '..infrastructure..'");

    @ArchTest
    static final ArchRule onlyBEMayUseTheInfrastructureManager = noClasses().that()
        .resideOutsideOfPackage("..infrastructure..")
        .should().accessClassesThat().areAssignableTo("jakarta.persistence.EntityManager")
        .allowEmptyShould(true)
        .as("Only infrastructure may use the EntityManager");

    @ArchTest
    static final ArchRule resourcesShouldBeInApiPackage =
        classes().that().haveSimpleNameEndingWith("Resource")
            .should().resideInAPackage("..api..").allowEmptyShould(true);

    @ArchTest
    static final ArchRule jbossLogAnnotationShouldNotBeUsed = classes().should()
        .notBeAnnotatedWith("lombok.extern.jbosslog.JBossLog")
        .because("Use @Slf4j annotation instead");

    @ArchTest
    static final ArchRule jbossLogShouldNotBeUsed = noClasses().should().accessClassesThat()
        .resideInAnyPackage("org.jboss.logging..")
        .because("Use @Slf4j annotation instead");

    @ArchTest
    static final ArchRule interfacesShouldNotHaveInterfaceInName =
        noClasses().that().areInterfaces().should()
            .haveSimpleNameContaining("Interface");
}
