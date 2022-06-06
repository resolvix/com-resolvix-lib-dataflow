package com.resolvix.lib.dataflow.context.base;

import com.resolvix.lib.dataflow.api.Event;
import com.resolvix.lib.dataflow.context.api.TransformerContext;

public abstract class BaseTransformerContextImpl<C extends BaseTransformerContextImpl<C, E>, E extends Event<E>>
    extends BaseContextImpl<C, E>
    implements TransformerContext<E>
{

    protected BaseTransformerContextImpl() {
        super();
    }
}
