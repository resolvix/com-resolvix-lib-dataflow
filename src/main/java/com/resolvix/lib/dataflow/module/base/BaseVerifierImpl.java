package com.resolvix.lib.dataflow.module.base;

import com.resolvix.lib.dataflow.api.Event;
import com.resolvix.lib.dataflow.api.Substrate;
import com.resolvix.lib.dataflow.context.api.VerifierContext;
import com.resolvix.lib.dataflow.module.api.Verifier;

public abstract class BaseVerifierImpl<V extends BaseVerifierImpl<V, S, C, E>, S extends Substrate<S>, C extends VerifierContext<E>, E extends Event<E>>
    extends BaseModuleImpl<V, S, C, E>
    implements Verifier<S, C, E>
{

    protected abstract boolean verify(S substrate, C context);

    @Override
    public final boolean execute(S substrate, C context) {
        return verify(substrate, context);
    }
}
