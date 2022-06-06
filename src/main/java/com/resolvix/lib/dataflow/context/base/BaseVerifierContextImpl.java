package com.resolvix.lib.dataflow.context.base;

import com.resolvix.lib.dataflow.api.Event;
import com.resolvix.lib.dataflow.context.api.VerifierContext;

public abstract class BaseVerifierContextImpl<C extends BaseVerifierContextImpl<C, E>, E extends Event<E>>
    extends BaseContextImpl<C, E>
    implements VerifierContext<E>
{

    protected BaseVerifierContextImpl() {
        super();
    }
}
