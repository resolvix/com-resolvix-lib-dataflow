package com.resolvix.dataflow.api;

import java.util.List;

public interface Context<E extends Event<E>>
{

    List<E> getEvents();
}
