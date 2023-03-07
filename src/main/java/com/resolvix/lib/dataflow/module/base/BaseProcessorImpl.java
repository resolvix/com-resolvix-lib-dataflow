package com.resolvix.lib.dataflow.module.base;

import com.resolvix.lib.dataflow.api.Event;
import com.resolvix.lib.dataflow.context.api.ProcessorContext;
import com.resolvix.lib.dataflow.module.api.Processor;
import com.resolvix.lib.dataflow.api.Substrate;

public abstract class BaseProcessorImpl<P extends BaseProcessorImpl<P, I, O, C, S, E>, I, O, C extends ProcessorContext<E>, S extends Substrate<S>, E extends Event<E>>
    extends BaseModuleImpl<P, I, O, C, S, E>
    implements Processor<I, O, C, S, E>
{

    /**
     * Processes the context, {@code C}, based on the services supplied by
     * the system substrate, {@code S}.
     *
     * @param substrate the system substrate
     * @param context the processor context
     * @return true, if the process was successful; false, otherwise
     */
    protected abstract boolean process(I input, C context, S substrate);

    @Override
    public final boolean execute(I input, C context, S substrate) {
        return process(input, context, substrate);
    }
}
