package com.resolvix.dataflow.api;

public interface Mainframe<S extends Substrate<S>, C extends Context<E>, E extends Event<E>> {

    /**
     * Execute one or modules on the context, {@code C}, using the system
     * substrate, {@code S}, in the appropriate dependency order.
     *
     * @param substrate the substrate
     * @param context the context
     * @return true, if the modules were processed successfully; false,
     *  otherwise
     */
    boolean execute(S substrate, C context);
}
