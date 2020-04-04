package com.resolvix.dataflow.module.base;

import com.resolvix.dataflow.api.Context;
import com.resolvix.dataflow.api.Event;
import com.resolvix.dataflow.api.Module;
import com.resolvix.dataflow.api.Substrate;

public abstract class BaseModuleImpl<M extends BaseModuleImpl<M, S, C, E>, S extends Substrate<S>, C extends Context<E>, E extends Event<E>>
    implements Module<M, S, C, E>
{

}
