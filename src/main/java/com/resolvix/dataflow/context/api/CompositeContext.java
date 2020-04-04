package com.resolvix.dataflow.context.api;

import com.resolvix.dataflow.api.Context;
import com.resolvix.dataflow.api.Event;

public interface CompositeContext<E extends Event<E>>
    extends Context<E>
{

}
