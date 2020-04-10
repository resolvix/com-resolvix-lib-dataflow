package com.resolvix.dataflow.context.base;

import com.resolvix.dataflow.api.Event;
import com.resolvix.dataflow.context.api.AssertionContext;

public abstract class BaseAssertionContextImpl<C extends BaseAssertionContextImpl<C, E>, E extends Event<E>>
    extends BaseContextImpl<C, E>
    implements AssertionContext<E>
{

    protected BaseAssertionContextImpl() {
        super();
    }
}
