package com.resolvix.dataflow.module.base;

import com.resolvix.dataflow.api.Event;
import com.resolvix.dataflow.context.api.ProcessorContext;
import com.resolvix.dataflow.module.api.Processor;
import com.resolvix.dataflow.api.Substrate;

public abstract class BaseProcessorImpl<P extends BaseProcessorImpl<P, S, C, E>, S extends Substrate<S>, C extends ProcessorContext<E>, E extends Event<E>>
    extends BaseModuleImpl<P, S, C, E>
    implements Processor<P, S, C, E>
{

    /**
     * Processes the context, {@code C}, based on the services supplied by
     * the system substrate, {@code S}.
     *
     * @param substrate the system substrate
     * @param context the processor context
     * @return true, if the process was successful; false, otherwise
     */
    protected abstract boolean process(S substrate, C context);

    @Override
    public final boolean execute(S substrate, C context) {
        return process(substrate, context);
    }
}
