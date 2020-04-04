package com.resolvix.dataflow.context.base;

import com.resolvix.dataflow.api.Event;
import com.resolvix.dataflow.context.api.ValidatorContext;

public abstract class BaseValidatorContextImpl<C extends BaseValidatorContextImpl<C, E>, E extends Event<E>>
    extends BaseContextImpl<C, E>
    implements ValidatorContext<E>
{

    public BaseValidatorContextImpl() {
        super();
    }
}
