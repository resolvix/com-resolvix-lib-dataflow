package com.resolvix.dataflow.module.base;

import com.resolvix.dataflow.api.Event;
import com.resolvix.dataflow.context.api.ResolverContext;
import com.resolvix.dataflow.module.api.Resolver;
import com.resolvix.dataflow.api.Substrate;

public abstract class BaseResolverImpl<R extends BaseResolverImpl<R, S, C, E>, S extends Substrate<S>, C extends ResolverContext<E>, E extends Event<E>>
    extends BaseModuleImpl<R, S, C, E>
    implements Resolver<R, S, C, E>
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
    protected abstract boolean resolve(S substract, C context);

    @Override
    public final boolean execute(S substrate, C context) {
        return resolve(substrate, context);
    }
}
