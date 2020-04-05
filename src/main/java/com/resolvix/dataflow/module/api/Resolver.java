package com.resolvix.dataflow.module.api;

import com.resolvix.dataflow.api.Event;
import com.resolvix.dataflow.api.Module;
import com.resolvix.dataflow.api.Substrate;
import com.resolvix.dataflow.api.Context;

public interface Resolver<S extends Substrate<S>, C extends Context<E>, E extends Event<E>>
    extends Module<S, C, E>
{

}
