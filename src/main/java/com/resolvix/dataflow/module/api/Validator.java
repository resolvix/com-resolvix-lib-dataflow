package com.resolvix.dataflow.module.api;

import com.resolvix.dataflow.api.Context;
import com.resolvix.dataflow.api.Event;
import com.resolvix.dataflow.api.Module;
import com.resolvix.dataflow.api.Substrate;

public interface Validator<V extends Validator<V, S, C, E>, S extends Substrate<S>, C extends Context<E>, E extends Event<E>>
    extends Module<V, S, C, E>
{

}

