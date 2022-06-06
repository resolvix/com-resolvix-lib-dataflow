package com.resolvix.lib.dataflow.context.base;

import com.resolvix.lib.dataflow.api.Context;
import com.resolvix.lib.dataflow.api.Event;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class BaseContextImpl<S extends BaseContextImpl<S, E>, E extends Event<E>>
    implements Context<E>
{
    private List<E> events;

    protected BaseContextImpl() {
        this.events = new LinkedList<>();
    }

    /**
     * Record an event to the context event log.
     *
     * @param event the event
     */
    public final void record(E event) {
        events.add(event);
    }

    /**
     * Record a series of events to the context event log.
     *
     * @param events the events
     */
    public final void record(E... events) {
        for (E event : events)
            record(event);
    }

    /**
     * Record a list of events to the context event log
     *
     * @param events the list of events
     */
    public final void record(List<E> events) {
        this.events.addAll(events);
    }

    /**
     *
     *
     * @return
     */
    public final List<E> getEvents() {
        return Collections.unmodifiableList(events);
    }
}
