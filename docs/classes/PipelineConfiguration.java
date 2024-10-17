package com.example.business.config;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import static java.util.stream.Collectors.toList;

/**
 * Pipeline configuration used for dependency injection. Used for the Mediator pattern. see:
 * https://github.com/sizovs/PipelinR
 */
@Dependent
public class PipelineConfiguration {

    @Inject
    Instance<Command.Handler<?, ?>> handlers;

    @Inject
    Instance<Command.Middleware> middlewares;

    /**
     * Produces a {@link Pipeline} instance that is application-scoped.
     *
     * <p>
     * This method collects all command handlers and middlewares and initializes a {@link Pipelinr}
     * instance with them.
     * </p>
     *
     * @return a configured {@link Pipeline} instance.
     */
    @Produces
    @RequestScoped
    @Unremovable
    public Pipeline pipeline() {
        @SuppressWarnings("rawtypes")
        var commandHandlers = handlers.stream().map(r -> (Command.Handler) r).collect(toList());

        return new Pipelinr()
            .with(() -> commandHandlers.stream())
            .with(() -> middlewares.stream());
    }
}
