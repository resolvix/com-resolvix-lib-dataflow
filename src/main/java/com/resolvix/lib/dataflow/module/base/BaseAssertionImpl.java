package com.resolvix.lib.dataflow.module.base;

import com.resolvix.lib.dataflow.api.Event;
import com.resolvix.lib.dataflow.api.Substrate;
import com.resolvix.lib.dataflow.context.api.AssertionContext;
import com.resolvix.lib.dataflow.module.api.Assertion;

public abstract class BaseAssertionImpl<A extends BaseAssertionImpl<A, I, O, C, S, E>, I, O, C extends AssertionContext<E>, S extends Substrate<S>, E extends Event<E>>
    extends BaseModuleImpl<A, I, O, C, S, E>
    implements Assertion<I, O, C, S, E>
{

    protected abstract void assertion(S substrate, C context)
        throws AssertionError;

    public final boolean execute(I input, C context, S substrate) {
        try {
            assertion(substrate, context);
            return true;
        } catch (AssertionError e) {
            throw e;
        }
    }
}
