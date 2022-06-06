package com.resolvix.lib.dataflow.module.base;

import com.resolvix.lib.dataflow.api.Context;
import com.resolvix.lib.dataflow.api.Event;
import com.resolvix.lib.dataflow.api.Substrate;
import com.resolvix.lib.dataflow.module.api.Transformer;

public abstract class BaseTransformerImpl<T extends BaseTransformerImpl<T, S, C, E>, S extends Substrate<S>, C extends Context<E>, E extends Event<E>>
    extends BaseModuleImpl<T, S, C, E>
    implements Transformer<S, C, E>
{

    /**
     * Transforms data contained by the context, {@code C}, based on the
     * services supplied by the system substrate, {@code S}.
     *
     * @param substrate the system substrate
     * @param context the transformer context
     * @return true, if the transformation process was successful; false,
     *  otherwise
     */
    protected abstract boolean transform(S substrate, C context);

    @Override
    public boolean execute(S substrate, C context) {
        return transform(substrate, context);
    }
}
