package com.inv.scrambleid.configurations;

import org.hibernate.boot.model.naming.*;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.internal.util.StringHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.regex.Pattern;

@Configuration
public class HibernateConfiguration {


    private static final Pattern CAMEL_CASE_BOUNDARY_PATTERN = Pattern.compile("([^_])([A-Z][a-z]+)");
    private static final Pattern LOWER_UPPER_BOUNDARY_PATTERN = Pattern.compile("([a-z0-9])([A-Z])");
    private static final String REPLACEMENT_PATTERN = "$1_$2";

    /**
     * Converts a given camelCase string to snake_case.
     *
     * @param input the input string in camelCase format
     * @return the converted string in snake_case format
     */
    private static String convertToSnakeCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String step1 = CAMEL_CASE_BOUNDARY_PATTERN.matcher(input).replaceAll(REPLACEMENT_PATTERN);
        return LOWER_UPPER_BOUNDARY_PATTERN.matcher(step1).replaceAll(REPLACEMENT_PATTERN).toLowerCase();
    }

    /**
     * Converts a given Hibernate Identifier to snake_case format.
     *
     * @param identifier the input Identifier
     * @return the converted Identifier in snake_case format, or null if input is null
     */
    private static Identifier convertToSnakeCase(Identifier identifier) {
        return identifier == null ? null : new Identifier(convertToSnakeCase(identifier.getText()), identifier.isQuoted());
    }

    /**
     * Bean that provides an implicit naming strategy for Hibernate, converting entity and attribute names
     * to snake_case format.
     *
     * @return the custom ImplicitNamingStrategy
     */
    @Bean
    public ImplicitNamingStrategy implicitNamingStrategy() {
        return new ImplicitNamingStrategyJpaCompliantImpl() {

            @Override
            public Identifier determineJoinColumnName(ImplicitJoinColumnNameSource source) {
                return toIdentifier(source.getReferencedColumnName().getText(), source.getBuildingContext());
            }

            @Override
            protected String transformAttributePath(AttributePath attributePath) {
                return convertToSnakeCase(attributePath.getProperty());
            }

            @Override
            protected String transformEntityName(EntityNaming entityNaming) {
                // Prefer JPA entity name if specified, else convert entity name to snake_case.
                return StringHelper.isNotEmpty(entityNaming.getJpaEntityName()) ? entityNaming.getJpaEntityName() : convertToSnakeCase(StringHelper.unqualify(entityNaming.getEntityName()));
            }
        };
    }

    /**
     * Bean that provides a physical naming strategy for Hibernate, converting database identifiers
     * (e.g., tables, columns) to snake_case format.
     *
     * @return the custom PhysicalNamingStrategy
     */
    @Bean
    public PhysicalNamingStrategy physicalNamingStrategy() {
        return new PhysicalNamingStrategy() {

            @Override
            public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
                return convertToSnakeCase(name);
            }

            @Override
            public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
                return convertToSnakeCase(name);
            }

            @Override
            public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
                return convertToSnakeCase(name);
            }

            @Override
            public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
                return convertToSnakeCase(name);
            }

            @Override
            public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
                return convertToSnakeCase(name);
            }
        };
    }
}
