package com.resolvix.lib.dataflow.module.base;

import com.resolvix.lib.dataflow.api.Event;
import com.resolvix.lib.dataflow.context.api.ResolverContext;
import com.resolvix.lib.dataflow.module.api.Resolver;
import com.resolvix.lib.dataflow.api.Substrate;
import org.checkerframework.checker.units.qual.C;

public abstract class BaseResolverImpl<R extends BaseResolverImpl<R, I, O, C, S, E>, I, O, C extends ResolverContext<E>, S extends Substrate<S>, E extends Event<E>>
    extends BaseModuleImpl<R, I, O, C, S, E>
    implements Resolver<I, O, C, S, E>
{
    /**
     * Resolves data contained in the context, {@code C}, based on the
     * services supplied by the system substrate, {@code S}.
     *
     * @param substract the system substrate
     * @param context the resolver context
     * @return true, if the resolution process was successful; false,
     *  otherwise
     */
    protected abstract boolean resolve(I input, C context, S substract);

    @Override
    public final boolean execute(I input, C context, S substrate) {
        return resolve(input, context, substrate);
    }
}
