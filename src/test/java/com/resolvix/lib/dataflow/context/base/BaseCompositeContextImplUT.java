package com.resolvix.lib.dataflow.context.base;

import com.resolvix.lib.dataflow.api.Context;
import com.resolvix.lib.dataflow.api.Event;
import com.resolvix.lib.dataflow.context.api.ResolverContext;
import com.resolvix.lib.dataflow.context.api.ValidatorContext;
import com.resolvix.lib.dataflow.context.base.BaseCompositeContextImpl;
import com.resolvix.lib.dataflow.context.base.BaseResolverContextImpl;
import com.resolvix.lib.dataflow.context.base.BaseValidatorContextImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class BaseCompositeContextImplUT {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private LocalCompositeContextTypeA contextA = new LocalCompositeContextTypeA(
        "one",
        "two",
        "three"
    );

    private LocalCompositeContextTypeB contextB = new LocalCompositeContextTypeB(
        "one",
        "two",
        "three"
    );

    private interface ProcessingEvent
        extends Event<ProcessingEvent>
    {

    }

    private interface LocalValidatorContext
        extends ValidatorContext<ProcessingEvent>
    {
        String getValueToBeValidated();
    }

    private interface LocalResolverContext
        extends ResolverContext<ProcessingEvent>
    {
        String getFirstParameter();

        String getSecondParameter();

        void setResolvedValue(String value);
    }

    //
    //  Implementation Type A
    //  =====================
    //
    //  Composite context implements one or more subordinate contexts
    //  where upon, when passed to the relevant module, the context
    //  is implicitly type cast to the interface for that module.
    //
    //  Advantages
    //  ----------
    //
    //  1.  Module interface is impliedly selected.
    //
    //  Disadvantages
    //  -------------
    //
    //  1.  Events are committed to the composite context directly rather
    //      that indirectly via an intermediate event collection.
    //

    private static class LocalCompositeContextTypeA
        extends BaseCompositeContextImpl<LocalCompositeContextTypeA, Context<ProcessingEvent>, ProcessingEvent>
        implements LocalValidatorContext, LocalResolverContext
    {
        private final Map<String, Supplier<Context<ProcessingEvent>>>
            registeredContextTypeInstantiators = new HashMap<>();

        private String valueToBeValidated;

        private String firstParameter;

        private String secondParameter;

        private String resolvedValue;

        LocalCompositeContextTypeA(
            String valueToBeValidated,
            String firstParameter,
            String secondParameter)
        {
            registeredContextTypeInstantiators.put(LocalValidatorContext.class.getCanonicalName(), this::self);
            registeredContextTypeInstantiators.put(LocalResolverContext.class.getCanonicalName(), this::self);

            this.valueToBeValidated = valueToBeValidated;
            this.firstParameter = firstParameter;
            this.secondParameter = secondParameter;
        }

        protected LocalCompositeContextTypeA self() {
            return this;
        }

        @Override
        protected Map<String, Supplier<Context<ProcessingEvent>>> getRegisteredContextTypes() {
            return registeredContextTypeInstantiators;
        }

        @Override
        public String getValueToBeValidated() {
            return valueToBeValidated;
        }

        @Override
        public String getFirstParameter() {
            return firstParameter;
        }

        @Override
        public String getSecondParameter() {
            return secondParameter;
        }

        @Override
        public void setResolvedValue(String value) {
            this.resolvedValue = value;
        }

        public String getResolvedValue() {
            return resolvedValue;
        }
    }

    private static abstract class LocalValidatorContextImpl
        extends BaseValidatorContextImpl<LocalValidatorContextImpl, ProcessingEvent>
        implements LocalValidatorContext
    {
        public abstract String getValueToBeValidated();
    }

    private static abstract class LocalResolverContextImpl
        extends BaseResolverContextImpl<LocalResolverContextImpl, ProcessingEvent>
        implements LocalResolverContext
    {
        public abstract String getFirstParameter();

        public abstract String getSecondParameter();

        public abstract void setResolvedValue(String value);
    }

    //
    //  Implementation Type B
    //  =====================
    //
    //  Composite context exposes one or more subordinate interfaces through
    //  abstract classes that are instantiated, at runtime, with anonymous
    //  classes.
    //
    //  Advantages
    //  ----------
    //
    //  1.  Mechanism enables the composite context to exercise greater
    //      control on the runtime behaviour of the client context processing
    //      mainframe through the use of {@code open} and {@code close}
    //      subordinate context operations.
    //
    //  Disadvantages
    //  -------------
    //
    //  1.  More complex to implement.
    //

    private static class LocalCompositeContextTypeB
        extends BaseCompositeContextImpl<LocalCompositeContextTypeB, Context<ProcessingEvent>, ProcessingEvent>
    {
        private final Map<String, Supplier<Context<ProcessingEvent>>>
            registeredContextTypeInstantiators = new HashMap<>();

        private String valueToBeValidated;

        private String firstParameter;

        private String secondParameter;

        private String resolvedValue;

        LocalCompositeContextTypeB(
            String valueToBeValidated,
            String firstParameter,
            String secondParameter)
        {
            registeredContextTypeInstantiators.put(LocalValidatorContextImpl.class.getCanonicalName(), this::getValidatorContext);
            registeredContextTypeInstantiators.put(LocalResolverContextImpl.class.getCanonicalName(), this::getResolverContext);

            this.valueToBeValidated = valueToBeValidated;
            this.firstParameter = firstParameter;
            this.secondParameter = secondParameter;
        }

        @Override
        protected Map<String, Supplier<Context<ProcessingEvent>>> getRegisteredContextTypes() {
            return registeredContextTypeInstantiators;
        }

        private LocalValidatorContextImpl getValidatorContext() {
             return new LocalValidatorContextImpl() {

                 @Override
                 public String getValueToBeValidated() {
                     return valueToBeValidated;
                 }
             };
        }

        private LocalResolverContextImpl getResolverContext() {
             return new LocalResolverContextImpl() {

                 @Override
                 public String getFirstParameter() {
                     return firstParameter;
                 }

                 @Override
                 public String getSecondParameter() {
                     return secondParameter;
                 }

                 @Override
                 public void setResolvedValue(String value) {
                     resolvedValue = value;
                 }
             };
        }

        public String getResolvedValue() {
            return resolvedValue;
        }
    }

    @Before
    public void before() {

    }

    @Test
    public void testOpenReadCloseLocalValidatorContextA() {
        LocalValidatorContext c = contextA.open(LocalValidatorContext.class);
        assertThat(c.getValueToBeValidated(), equalTo("one"));
        contextA.close(c);
    }

    @Test
    public void testOpenReadWriteCloseLocalResolverContextA() {
        LocalResolverContext c = contextA.open(LocalResolverContext.class);
        assertThat(c.getFirstParameter(), equalTo("two"));
        assertThat(c.getSecondParameter(), equalTo("three"));
        c.setResolvedValue(c.getFirstParameter() + " plus " + c.getSecondParameter());
        contextA.close(c);
        assertThat(contextA.getResolvedValue(), equalTo("two plus three"));
    }

    @Test
    public void testOpenReadCloseLocalValidatorContextB() {
        LocalValidatorContextImpl c = contextB.open(LocalValidatorContextImpl.class);
        assertThat(c.getValueToBeValidated(), equalTo("one"));
        contextB.close(c);
    }

    @Test
    public void testOpenReadWriteCloseLocalResolverContextB() {
        LocalResolverContextImpl c = contextB.open(LocalResolverContextImpl.class);
        assertThat(c.getFirstParameter(), equalTo("two"));
        assertThat(c.getSecondParameter(), equalTo("three"));
        c.setResolvedValue(c.getFirstParameter() + " plus " + c.getSecondParameter());
        contextB.close(c);
        assertThat(contextB.getResolvedValue(), equalTo("two plus three"));
    }
}
