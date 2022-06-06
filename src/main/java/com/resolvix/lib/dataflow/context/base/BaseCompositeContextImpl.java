package com.resolvix.lib.dataflow.context.base;

import com.resolvix.lib.dataflow.api.Context;
import com.resolvix.lib.dataflow.api.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class BaseCompositeContextImpl<
        S extends BaseCompositeContextImpl<S, C, E>,
        C extends Context<E>,
        E extends Event<E>>
    extends BaseContextImpl<S, E>
{
    private Map<String, C> contextMap;

    protected BaseCompositeContextImpl() {
        super();
        this.contextMap = new HashMap<>();
    }

    protected abstract Map<String, Supplier<C>> getRegisteredContextTypes();

    private <D extends C> D instantiate(Class<D> contextType) {
        Supplier<D> supplierD = (Supplier<D>) getRegisteredContextTypes().get(contextType.getCanonicalName());
        if (supplierD == null)
            throw new RuntimeException(
                String.format("Context [%s] not available", contextType.getCanonicalName()));
        return supplierD.get();
    }

    /**
     * Opens a context of type {@code contextType} for use by the relevant
     * module operation, and returns a reference to the context.
     *
     * @param contextType the context type
     * @return the relevant context
     */
    public final <D extends C> D open(Class<D> contextType) {
        D context = (D) contextMap.get(contextType.getCanonicalName());
        if (context != null)
            return context;

        context = instantiate(contextType);
        contextMap.put(contextType.getCanonicalName(), context);
        return context;
    }

    /**
     * Closes the relevant context, and copies the events generated by the
     * relevant module operation to the composite context.
     *
     * @param context the relevant context
     */
    public final void close(C context) {
         record(context.getEvents());
    }
}