package com.resolvix.lib.dataflow.module.base;

import com.resolvix.lib.dataflow.api.Event;
import com.resolvix.lib.dataflow.api.Substrate;
import com.resolvix.lib.dataflow.context.api.VerifierContext;
import com.resolvix.lib.dataflow.module.api.Verifier;

public abstract class BaseVerifierImpl<V extends BaseVerifierImpl<V, I, O, C, S, E>, I, O, C extends VerifierContext<E>, S extends Substrate<S>, E extends Event<E>>
    extends BaseModuleImpl<V, I, O, C, S, E>
    implements Verifier<I, O, C, S, E>
{

    protected abstract boolean verify(I input, C context, S substrate);

    @Override
    public final boolean execute(I input, C context, S substrate) {
        return verify(input, context, substrate);
    }
}
