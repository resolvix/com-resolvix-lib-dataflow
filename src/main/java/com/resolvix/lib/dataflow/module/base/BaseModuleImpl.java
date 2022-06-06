package com.resolvix.lib.dataflow.module.base;

import com.resolvix.lib.dataflow.api.Context;
import com.resolvix.lib.dataflow.api.Event;
import com.resolvix.lib.dataflow.api.Module;
import com.resolvix.lib.dataflow.api.Substrate;
import org.slf4j.Logger;

public abstract class BaseModuleImpl<M extends BaseModuleImpl<M, S, C, E>, S extends Substrate<S>, C extends Context<E>, E extends Event<E>>
    implements Module<S, C, E>
{
    protected abstract Logger getLogger();

    /**
     *
     * @return
     */
    protected abstract String getModuleName();

    /**
     *
     * @param substrate
     * @param context
     * @return
     */
    protected abstract boolean preconditions(S substrate, C context);

    /**
     *
     * @param substrate the system substrate
     * @param context the context
     * @return
     */
    protected abstract boolean execute(S substrate, C context);

    /**
     *
     * @param substrate
     * @param context
     * @return
     */
    protected abstract boolean postconditions(S substrate, C context);

    @Override
    public final boolean executeModule(S substrate, C context) {
        getLogger().debug("BEGIN {}", getModuleName());
        assert preconditions(substrate, context); //NOSONAR
        boolean b = execute(substrate, context);
        assert postconditions(substrate, context); //NOSONAR
        getLogger().debug("END: {}", getModuleName());
        return b;
    }
}
