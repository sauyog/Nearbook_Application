package org.briarproject.bramble.api.db;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 * Annotation for injecting the executor for database tasks. Also used for
 * annotating methods that should run on the database executor.
 * <p>
 * The contract of this executor is that tasks are run in the order they're
 * submitted, tasks are not run concurrently, and submitting a task will never
 * block. Tasks must not run indefinitely. Tasks submitted during shutdown are
 * discarded.
 * <p>
 * It is not mandatory to use this executor for database tasks. The database
 * can be accessed from any thread, but this executor's guarantee that tasks
 * are run in the order they're submitted may be useful in some cases.
 */
@Qualifier
@Target({FIELD, METHOD, PARAMETER})
@Retention(RUNTIME)
public @interface DatabaseExecutor {
}
