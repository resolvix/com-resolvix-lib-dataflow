package com.resolvix.lib.dataflow.module.api;

import com.resolvix.lib.dataflow.api.Event;
import com.resolvix.lib.dataflow.api.Module;
import com.resolvix.lib.dataflow.api.Substrate;
import com.resolvix.lib.dataflow.api.Context;

public interface Resolver<S extends Substrate<S>, C extends Context<E>, E extends Event<E>>
    extends Module<S, C, E>
{

}
