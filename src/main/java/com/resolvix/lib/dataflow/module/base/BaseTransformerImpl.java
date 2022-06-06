package com.resolvix.lib.dataflow.module.base;

import com.resolvix.lib.dataflow.api.Context;
import com.resolvix.lib.dataflow.api.Event;
import com.resolvix.lib.dataflow.api.Substrate;
import com.resolvix.lib.dataflow.module.api.Transformer;
import org.checkerframework.checker.units.qual.C;

public abstract class BaseTransformerImpl<T extends BaseTransformerImpl<T, I, O, C, S, E>, I, O, C extends Context<E>, S extends Substrate<S>, E extends Event<E>>
    extends BaseModuleImpl<T, I, O, C, S, E>
    implements Transformer<I, O, C, S, E>
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
    protected abstract boolean transform(I input, C context, S substrate);

    @Override
    public boolean execute(I input, C context, S substrate) {
        return transform(input, context, substrate);
    }
}
