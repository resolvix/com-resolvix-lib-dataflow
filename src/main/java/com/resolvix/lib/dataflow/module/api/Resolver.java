package com.resolvix.lib.dataflow.module.api;

import com.resolvix.lib.dataflow.api.Event;
import com.resolvix.lib.dataflow.api.Module;
import com.resolvix.lib.dataflow.api.Substrate;
import com.resolvix.lib.dataflow.api.Context;
import org.checkerframework.checker.units.qual.C;

public interface Resolver<I, O, C extends Context<E>, S extends Substrate<S>,  E extends Event<E>>
    extends Module<I, O, C, S, E>
{

}
