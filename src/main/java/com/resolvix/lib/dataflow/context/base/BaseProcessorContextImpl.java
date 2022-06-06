package com.resolvix.lib.dataflow.context.base;

import com.resolvix.lib.dataflow.api.Event;
import com.resolvix.lib.dataflow.context.api.ProcessorContext;

public abstract class BaseProcessorContextImpl<C extends BaseProcessorContextImpl<C, E>, E extends Event<E>>
    extends BaseContextImpl<C, E>
    implements ProcessorContext<E>
{

    protected BaseProcessorContextImpl() {
        super();
    }
}
