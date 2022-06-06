package com.resolvix.lib.dataflow.context.base;

import com.resolvix.lib.dataflow.api.Event;
import com.resolvix.lib.dataflow.context.api.ValidatorContext;

public abstract class BaseValidatorContextImpl<C extends BaseValidatorContextImpl<C, E>, E extends Event<E>>
    extends BaseContextImpl<C, E>
    implements ValidatorContext<E>
{

    protected BaseValidatorContextImpl() {
        super();
    }
}
