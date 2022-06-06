package com.resolvix.lib.dataflow.mainframe.base;

import com.resolvix.lib.dataflow.api.Module;
import com.resolvix.lib.dataflow.api.annotation.ModuleDependency;
import com.resolvix.lib.dataflow.api.Context;
import com.resolvix.lib.dataflow.api.Event;
import com.resolvix.lib.dataflow.api.Mainframe;
import com.resolvix.lib.dataflow.api.Substrate;
import com.resolvix.lib.dependency.DependencyResolver;
import com.resolvix.lib.dependency.api.CyclicDependencyException;
import com.resolvix.lib.dependency.api.DependencyNotFoundException;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

import static com.resolvix.lib.dependency.DependencyResolver.resolveDependencies;

public abstract class BaseMainframeImpl<M extends BaseMainframeImpl<M, I, O, C, S, E>, I, O, C extends Context<E>, S extends Substrate<S>, E extends Event<E>>
    implements Mainframe<I, O, C, S, E>
{
    private Class<? extends Module<I, O, C, S, E>>[] orderedModuleClasses;

    private Module<I, O, C, S, E> instantiatedModules[];

    protected abstract Logger getLogger();

    /**
     * Returns the array of modules to be processed by the module processing
     * mainframe.
     *
     * @return an array of module classes
     */
    protected abstract Class<? extends Module<I, O, C, S, E>>[] getModuleClasses();

    private Class<? extends Module<I, O, C, S, E>>[] getOrderedModuleClasses()
        throws CyclicDependencyException, DependencyNotFoundException {
        if (orderedModuleClasses == null)
            orderedModuleClasses = DependencyResolver.resolveDependencies(
                ModuleDependency.class,
                Module.class,
                (Function<ModuleDependency, Class<? extends Module>[]>) ModuleDependency::modules,
                getModuleClasses());
        return orderedModuleClasses;
    }

    private Module<I, O, C, S, E> instantiateModule(Class<? extends Module<I, O, C, S, E>> moduleClass)
        throws NoSuchMethodException, InstantiationException, IllegalAccessException,
            InvocationTargetException
    {
        Constructor<? extends Module<I, O, C, S, E>> moduleConstructor
            = moduleClass.getConstructor();
        return moduleConstructor.newInstance();
    }

    private Module<I, O, C, S, E>[] getInstantiatedModules()
    {
        if (instantiatedModules == null) {
            try {
                Class<? extends Module<I, O, C, S, E>> orderedModuleClasses[] = getOrderedModuleClasses();
                instantiatedModules = (Module<I, O, C, S, E>[]) Array.newInstance(Module.class, orderedModuleClasses.length);
                int i = 0;
                for (Class<? extends Module<I, O, C, S, E>> moduleClass : orderedModuleClasses) {
                    try {
                        instantiatedModules[i++] = instantiateModule(moduleClass);
                    }
                    catch (NoSuchMethodException | InstantiationException
                        | IllegalAccessException | InvocationTargetException e) {
                        getLogger().debug(e.getLocalizedMessage() + " / " + e.getMessage());
                    }
                }
            } catch (CyclicDependencyException | DependencyNotFoundException e) {
                getLogger().debug(e.getLocalizedMessage() + " / " + e.getMessage());
            }
        }
        return instantiatedModules;
    }

    private boolean execute(Module<I, O, C, S, E> module, I input, C context, S substrate) {
        if (module == null)
            throw new IllegalStateException(
                "module has a null value.");
        return module.consume(input, context, substrate);
    }

    @Override
    public boolean execute(I input, C context, S substrate) {
        Module<I, O, C, S, E>[] orderedModules = getInstantiatedModules();
        boolean result = true;
        for (Module<I, O, C, S, E> module : orderedModules)
            result = result && execute(module, input, context, substrate);
        return result;
    }
}
