package com.resolvix.dataflow.mainframe;

import com.resolvix.dataflow.api.Context;
import com.resolvix.dataflow.api.Event;
import com.resolvix.dataflow.api.Mainframe;
import com.resolvix.dataflow.api.Module;
import com.resolvix.dataflow.api.Substrate;
import com.resolvix.dataflow.api.annotation.ModuleDependency;
import com.resolvix.dataflow.context.api.ProcessorContext;
import com.resolvix.dataflow.context.api.ResolverContext;
import com.resolvix.dataflow.context.api.ValidatorContext;
import com.resolvix.dataflow.context.base.BaseCompositeContextImpl;
import com.resolvix.dataflow.mainframe.base.BaseMainframeImpl;
import com.resolvix.dataflow.module.api.Processor;
import com.resolvix.dataflow.module.api.Resolver;
import com.resolvix.dataflow.module.api.Validator;
import com.resolvix.dataflow.module.base.BaseProcessorImpl;
import com.resolvix.dataflow.module.base.BaseResolverImpl;
import com.resolvix.dataflow.module.base.BaseValidatorImpl;
import com.resolvix.lib.list.ListBuilder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.function.Supplier;

import static org.junit.Assert.assertTrue;

public class BaseMainframeImplUT {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private interface ProcessingEvent
        extends Event<ProcessingEvent>
    {

    }

    private static class LocalCompositeContext
        extends BaseCompositeContextImpl<LocalCompositeContext, Context<ProcessingEvent>, ProcessingEvent>
        implements ResolverContext<ProcessingEvent>,
            ValidatorContext<ProcessingEvent>,
            ProcessorContext<ProcessingEvent>
    {

        @Override
        protected Map<String, Supplier<Context<ProcessingEvent>>> getRegisteredContextTypes() {
            return null;
        }


    }

    private static class LocalSubstrate
        implements Substrate<LocalSubstrate>
    {


    }

    protected static class LocalValidatorA
        extends BaseValidatorImpl<LocalValidatorA, LocalSubstrate, LocalCompositeContext, ProcessingEvent>
        implements Validator<LocalSubstrate, LocalCompositeContext, ProcessingEvent>
    {
        private static final Logger LOGGER = LoggerFactory.getLogger(LocalValidatorA.class);

        public LocalValidatorA() {
            //
        }

        @Override
        protected Logger getLogger() {
            return LOGGER;
        }

        @Override
        protected String getModuleName() {
            return "LocalValidatorA";
        }

        @Override
        protected boolean preconditions(LocalSubstrate substrate, LocalCompositeContext context) {
            LOGGER.debug("LocalValidatorA preconditions");
            return true;
        }

        @Override
        protected boolean validate(LocalSubstrate substrate, LocalCompositeContext context) {
            return true;
        }

        @Override
        protected boolean postconditions(LocalSubstrate substrate, LocalCompositeContext context) {
            LOGGER.debug("LocalValidatorA postconditions");
            return true;
        }
    }

    @ModuleDependency(modules = {LocalValidatorA.class})
    protected static class LocalValidatorB
        extends BaseValidatorImpl<LocalValidatorB, LocalSubstrate, LocalCompositeContext, ProcessingEvent>
        implements Validator<LocalSubstrate, LocalCompositeContext, ProcessingEvent>
    {
        public static final Logger LOGGER = LoggerFactory.getLogger(LocalValidatorB.class);

        public LocalValidatorB() {
            //
        }

        @Override
        protected Logger getLogger() {
            return LOGGER;
        }

        @Override
        protected String getModuleName() {
            return "LocalValidatorB";
        }

        @Override
        protected boolean preconditions(LocalSubstrate substrate, LocalCompositeContext context) {
            LOGGER.debug("LocalValidatorB preconditions");
            return true;
        }

        @Override
        protected boolean validate(LocalSubstrate substrate, LocalCompositeContext context) {
            return true;
        }

        @Override
        protected boolean postconditions(LocalSubstrate substrate, LocalCompositeContext context) {
            LOGGER.debug("LocalValidatorB postconditions");
            return true;
        }
    }

    @ModuleDependency(modules = {LocalValidatorA.class, LocalValidatorB.class})
    protected static class LocalResolverC
        extends BaseResolverImpl<LocalResolverC, LocalSubstrate, LocalCompositeContext, ProcessingEvent>
        implements Resolver<LocalSubstrate, LocalCompositeContext, ProcessingEvent>
    {
        private static final Logger LOGGER = LoggerFactory.getLogger(LocalResolverC.class);

        public LocalResolverC() {
            //
        }

        @Override
        protected Logger getLogger() {
            return LOGGER;
        }

        @Override
        protected String getModuleName() {
            return "LocalResolverC";
        }

        @Override
        protected boolean preconditions(LocalSubstrate substrate, LocalCompositeContext context) {
            LOGGER.debug("LocalResolverC preconditions");
            return true;
        }

        @Override
        protected boolean resolve(LocalSubstrate substract, LocalCompositeContext context) {
            return true;
        }

        @Override
        protected boolean postconditions(LocalSubstrate substrate, LocalCompositeContext context) {
            LOGGER.debug("LocalResolverC postconditions");
            return true;
        }
    }

    @ModuleDependency(modules = LocalResolverC.class)
    protected static class LocalProcessorD
        extends BaseProcessorImpl<LocalProcessorD, LocalSubstrate, LocalCompositeContext, ProcessingEvent>
        implements Processor<LocalSubstrate, LocalCompositeContext, ProcessingEvent>
    {
        private static final Logger LOGGER = LoggerFactory.getLogger(LocalProcessorD.class);

        public LocalProcessorD() {
            //
        }

        @Override
        protected Logger getLogger() {
            return LOGGER;
        }

        @Override
        protected String getModuleName() {
            return "LocalProcessorD";
        }

        @Override
        protected boolean preconditions(LocalSubstrate substrate, LocalCompositeContext context) {
            LOGGER.debug("LocalProcessorD preconditions");
            return true;
        }

        @Override
        protected boolean process(LocalSubstrate substrate, LocalCompositeContext context) {
            return true;
        }

        @Override
        protected boolean postconditions(LocalSubstrate substrate, LocalCompositeContext context) {
            LOGGER.debug("LocalProcessorD postconditions");
            return true;
        }
    }

    private class LocalMainframeImpl
        extends BaseMainframeImpl<LocalMainframeImpl, LocalSubstrate, LocalCompositeContext, ProcessingEvent>
        implements Mainframe<LocalSubstrate, LocalCompositeContext, ProcessingEvent>
    {
        private final Logger logger = LoggerFactory.getLogger(LocalMainframeImpl.class);

        @Override
        protected Logger getLogger() {
            return logger;
        }

        @Override
        protected Class<? extends Module<LocalSubstrate, LocalCompositeContext, ProcessingEvent>>[] getModuleClasses() {
            Class<? extends Module<LocalSubstrate, LocalCompositeContext, ProcessingEvent>>[] modules
                = (Class<? extends Module<LocalSubstrate, LocalCompositeContext, ProcessingEvent>>[])
                    Array.newInstance(Class.class, 4);
            return ListBuilder.getBuilder(Class.class)
                .add(LocalResolverC.class)
                .add(LocalProcessorD.class)
                .add(LocalValidatorB.class)
                .add(LocalValidatorA.class)
                .build()
                .toArray(modules);
        };
    }

    @Before
    public void before() {

    }

    @Test
    public void testBaseMainframeImpl() {
        LocalMainframeImpl localMainframe = new LocalMainframeImpl();
        LocalSubstrate localSubstrate = new LocalSubstrate();
        LocalCompositeContext localCompositeContext = new LocalCompositeContext();
        assertTrue(localMainframe.execute(localSubstrate, localCompositeContext));
    }
}
