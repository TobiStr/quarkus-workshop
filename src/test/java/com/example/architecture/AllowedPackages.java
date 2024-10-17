package com.example.architecture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contains allowed package references for this application.
 */
class AllowedPackages {
    private final List<String> packageIdentifiers = new ArrayList<>();

    static AllowedPackages builder() {
        return new AllowedPackages().javaEE();
    }

    String[] build() {
        return packageIdentifiers.stream().distinct().sorted().toArray(String[]::new);
    }

    AllowedPackages and(String packageIdentifier) {
        packageIdentifiers.add(packageIdentifier);
        return this;
    }

    AllowedPackages and(String... newPackageIdentifier) {
        packageIdentifiers.addAll(Arrays.asList(newPackageIdentifier));
        return this;
    }

    AllowedPackages javaEE() {
        packageIdentifiers.add("java..");
        packageIdentifiers.add("jakarta..");
        packageIdentifiers.add("lombok..");
        packageIdentifiers.add("org.eclipse.microprofile..");
        packageIdentifiers.add("org.apache.commons..");
        packageIdentifiers.add("org.mapstruct..");
        packageIdentifiers.add("org.hibernate.validator..");
        packageIdentifiers.add("com.fasterxml.jackson..");
        return this;
    }

    AllowedPackages quarkus() {
        packageIdentifiers.add("io.micrometer..");
        packageIdentifiers.add("io.opentelemetry..");
        packageIdentifiers.add("io.quarkiverse..");
        packageIdentifiers.add("io.quarkus..");
        packageIdentifiers.add("io.smallrye..");
        packageIdentifiers.add("org.jboss..");
        packageIdentifiers.add("io.vertx..");
        packageIdentifiers.add("org.slf4j..");
        return this;
    }

    AllowedPackages hibernate() {
        packageIdentifiers.add("org.hibernate..");
        return this;
    }

    AllowedPackages testing() {
        packageIdentifiers.add("com.tngtech.archunit..");
        packageIdentifiers.add("io.restassured..");
        packageIdentifiers.add("junit.framework..");
        packageIdentifiers.add("org.hamcrest..");
        packageIdentifiers.add("org.junit..");
        packageIdentifiers.add("org.mockito..");
        packageIdentifiers.add("org.testcontainers..");
        return this;
    }

    // Add allowed packages here:
    AllowedPackages allowedPackages() {
        packageIdentifiers.add("an.awesome.pipelinr..");
        packageIdentifiers.add("com.google..");
        packageIdentifiers.add("io.swagger..");
        return this;
    }
}
