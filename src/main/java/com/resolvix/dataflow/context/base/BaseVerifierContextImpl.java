package com.resolvix.dataflow.context.base;

import com.resolvix.dataflow.api.Event;
import com.resolvix.dataflow.context.api.VerifierContext;

public abstract class BaseVerifierContextImpl<C extends BaseVerifierContextImpl<C, E>, E extends Event<E>>
    extends BaseContextImpl<C, E>
    implements VerifierContext<E>
{

    protected BaseVerifierContextImpl() {
        super();
    }
}
