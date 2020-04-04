package com.resolvix.dataflow.context.base;

import com.resolvix.dataflow.api.Event;
import com.resolvix.dataflow.context.api.ProcessorContext;

public abstract class BaseProcessorContextImpl<C extends BaseProcessorContextImpl<C, E>, E extends Event<E>>
    extends BaseContextImpl<C, E>
    implements ProcessorContext<E>
{

    protected BaseProcessorContextImpl() {
        super();
    }
}
