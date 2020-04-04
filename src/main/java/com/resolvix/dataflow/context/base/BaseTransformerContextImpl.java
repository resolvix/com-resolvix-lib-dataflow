package com.resolvix.dataflow.context.base;

import com.resolvix.dataflow.api.Event;
import com.resolvix.dataflow.context.api.TransformerContext;

public abstract class BaseTransformerContextImpl<C extends BaseTransformerContextImpl<C, E>, E extends Event<E>>
    extends BaseContextImpl<C, E>
    implements TransformerContext<E>
{

    protected BaseTransformerContextImpl() {
        super();
    }
}
