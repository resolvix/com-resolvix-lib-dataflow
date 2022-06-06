package com.resolvix.lib.dataflow.api;

import org.checkerframework.checker.units.qual.C;

public interface Mainframe<I, O, C extends Context<E>, S extends Substrate<S>, E extends Event<E>> {

    /**
     * Execute one or modules on the context, {@code C}, using the system
     * substrate, {@code S}, in the appropriate dependency order.
     *
     * @param input the input
     * @param context the context
     * @param substrate the substrate
     * @return true, if the modules were processed successfully; false,
     *  otherwise
     */
    boolean execute(I input, C context, S substrate);
}
