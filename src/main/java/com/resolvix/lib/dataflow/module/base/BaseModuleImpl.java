package com.resolvix.lib.dataflow.module.base;

import com.resolvix.lib.dataflow.api.Context;
import com.resolvix.lib.dataflow.api.Event;
import com.resolvix.lib.dataflow.api.Module;
import com.resolvix.lib.dataflow.api.Substrate;
import org.slf4j.Logger;

public abstract class BaseModuleImpl<M extends BaseModuleImpl<M, I, O, C, S, E>, I, O, C extends Context<E>, S extends Substrate<S>,  E extends Event<E>>
    implements Module<I, O, C, S, E>
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
    protected abstract boolean preconditions(I input, C context, S substrate);

    /**
     *
     * @param substrate the system substrate
     * @param context the context
     * @return
     */
    protected abstract boolean execute(I input, C context, S substrate);

    /**
     *
     * @param substrate
     * @param context
     * @return
     */
    protected abstract boolean postconditions(C context, S substrate);

    @Override
    public final boolean consume(I input, C context, S substrate) {
        getLogger().debug("BEGIN {}", getModuleName());
        assert preconditions(input, context, substrate); //NOSONAR
        boolean b = execute(input, context, substrate);
        assert postconditions(context, substrate); //NOSONAR
        getLogger().debug("END: {}", getModuleName());
        return b;
    }
}
