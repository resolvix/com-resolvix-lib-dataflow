package com.resolvix.dataflow.context.base;

import com.resolvix.dataflow.api.Event;
import com.resolvix.dataflow.context.api.ResolverContext;

public abstract class BaseResolverContextImpl<C extends BaseResolverContextImpl<C, E>, E extends Event<E>>
    extends BaseContextImpl<C, E>
    implements ResolverContext<E>
{

    protected BaseResolverContextImpl() {
        super();
    }
}
