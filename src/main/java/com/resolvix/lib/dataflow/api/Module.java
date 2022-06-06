package com.resolvix.lib.dataflow.api;

import org.checkerframework.checker.units.qual.C;

/**
 *
 * @param <I> input data type
 * @param <O> output data type
 * @param <C> processing context data type
 * @param <S> platform substrate data type
 * @param <E> event data type
 */
public interface Module<I, O, C extends Context<E>, S extends Substrate<S>, E extends Event<E>>
{

    /**
     * Consume the input, {@code I}, using the context, {@code C}, and the
     * platform substrate, {@code S}.
     *
     * @param substrate the system substrate
     * @param context the context
     * @return true, if the module completed successfully; false, otherwise
     */
    boolean consume(I input, C context, S substrate);
}
