package com.resolvix.dataflow.module.base;

import com.resolvix.dataflow.api.Event;
import com.resolvix.dataflow.api.Substrate;
import com.resolvix.dataflow.context.api.AssertionContext;
import com.resolvix.dataflow.module.api.Assertion;

public abstract class BaseAssertionImpl<A extends BaseAssertionImpl<A, S, C, E>, S extends Substrate<S>, C extends AssertionContext<E>, E extends Event<E>>
    extends BaseModuleImpl<A, S, C, E>
    implements Assertion<S, C, E>
{

    protected abstract void assertion(S substrate, C context)
        throws AssertionError;

    public final boolean execute(S substrate, C context) {
        try {
            assertion(substrate, context);
            return true;
        } catch (AssertionError e) {
            throw e;
        }
    }
}
