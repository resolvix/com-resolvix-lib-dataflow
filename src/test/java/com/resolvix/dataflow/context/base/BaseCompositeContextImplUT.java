package com.resolvix.dataflow.context.base;

import com.resolvix.dataflow.api.Context;
import com.resolvix.dataflow.api.Event;
import com.resolvix.lib.map.MapBuilder;
import org.hamcrest.Matchers;
import org.junit.Assert;
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

    private interface ProcessingEvent
        extends Event<ProcessingEvent>
    {

    }

    private static abstract class LocalValidatorContext
        extends BaseValidatorContextImpl<LocalValidatorContext, ProcessingEvent>
    {
        abstract String getValueToBeValidated();
    }

    private static abstract class LocalResolverContext
        extends BaseResolverContextImpl<LocalResolverContext, ProcessingEvent>
    {
        abstract String getFirstParameter();

        abstract String getSecondParameter();

        abstract void setResolvedValue(String value);
    }

    private static class LocalCompositeContextTypeA
        extends BaseCompositeContextImpl<LocalCompositeContextTypeA, Context<ProcessingEvent>, ProcessingEvent>
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
            registeredContextTypeInstantiators.put(LocalValidatorContext.class.getCanonicalName(), this::getValidatorContext);
            registeredContextTypeInstantiators.put(LocalResolverContext.class.getCanonicalName(), this::getResolverContext);

            this.valueToBeValidated = valueToBeValidated;
            this.firstParameter = firstParameter;
            this.secondParameter = secondParameter;
        }

        @Override
        protected Map<String, Supplier<Context<ProcessingEvent>>> getRegisteredContextTypes() {
            return registeredContextTypeInstantiators;
        }

        private LocalValidatorContext getValidatorContext() {
             return new LocalValidatorContext() {

                 @Override
                 public String getValueToBeValidated() {
                     return valueToBeValidated;
                 }
             };
        }

        private LocalResolverContext getResolverContext() {
             return new LocalResolverContext() {

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
    public void testOpenReadCloseLocalValidatorContext() {
        LocalValidatorContext c = contextA.open(LocalValidatorContext.class);
        assertThat(c.getValueToBeValidated(), equalTo("one"));
        contextA.close(c);
    }

    @Test
    public void testOpenReadWriteCloseLocalResolverContext() {
        LocalResolverContext c = contextA.open(LocalResolverContext.class);
        assertThat(c.getFirstParameter(), equalTo("two"));
        assertThat(c.getSecondParameter(), equalTo("three"));
        c.setResolvedValue(c.getFirstParameter() + " plus " + c.getSecondParameter());
        contextA.close(c);
        assertThat(contextA.getResolvedValue(), equalTo("two plus three"));
    }
}
