package com.resolvix.lib.dataflow.api;

public interface Module<S extends Substrate<S>, C extends Context<E>, E extends Event<E>>
{

    /**
     * Execute the module on the context, {@code C}, using the
     * system substrate, {@code S}.
     *
     * @param substrate the system substrate
     * @param context the context
     * @return true, if the module completed successfully; false, otherwise
     */
    boolean executeModule(S substrate, C context);
}
