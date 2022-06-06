package com.resolvix.lib.dataflow.context.api;

import com.resolvix.lib.dataflow.api.Context;
import com.resolvix.lib.dataflow.api.Event;

public interface ValidatorContext<E extends Event<E>>
    extends Context<E>
{

}
