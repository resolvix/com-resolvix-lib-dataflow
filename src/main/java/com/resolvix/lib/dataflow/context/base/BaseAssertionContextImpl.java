package com.resolvix.lib.dataflow.context.base;

import com.resolvix.lib.dataflow.api.Event;
import com.resolvix.lib.dataflow.context.api.AssertionContext;

public abstract class BaseAssertionContextImpl<C extends BaseAssertionContextImpl<C, E>, E extends Event<E>>
    extends BaseContextImpl<C, E>
    implements AssertionContext<E>
{

    protected BaseAssertionContextImpl() {
        super();
    }
}
