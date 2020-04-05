package com.resolvix.dataflow.mainframe.base;

import com.resolvix.dataflow.api.*;
import com.resolvix.dataflow.api.Module;
import com.resolvix.dataflow.api.annotation.ModuleDependency;
import com.resolvix.lib.dependency.api.CyclicDependencyException;
import com.resolvix.lib.dependency.api.DependencyNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

import static com.resolvix.lib.dependency.DependencyResolver.resolveDependencies;

public abstract class BaseMainframeImpl<M extends BaseMainframeImpl<M, S, C, E>, S extends Substrate<S>, C extends Context<E>, E extends Event<E>>
    implements Mainframe<S, C, E>
{
    private Class<? extends Module<S, C, E>>[] orderedModuleClasses;

    private Module<S, C, E> instantiatedModules[];

    protected abstract Logger getLogger();

    /**
     * Returns the array of modules to be processed by the module processing
     * mainframe.
     *
     * @return an array of module classes
     */
    protected abstract Class<? extends Module<S, C, E>>[] getModuleClasses();

    private Class<? extends Module<S, C, E>>[] getOrderedModuleClasses()
        throws CyclicDependencyException, DependencyNotFoundException {
        if (orderedModuleClasses == null)
            orderedModuleClasses = resolveDependencies(
                ModuleDependency.class,
                Module.class,
                (Function<ModuleDependency, Class<? extends Module>[]>) ModuleDependency::modules,
                getModuleClasses());
        return orderedModuleClasses;
    }

    private Module<S, C, E> instantiateModule(Class<? extends Module<S, C, E>> moduleClass)
        throws NoSuchMethodException, InstantiationException, IllegalAccessException,
            InvocationTargetException
    {
        Constructor<? extends Module<S, C, E>> moduleConstructor
            = moduleClass.getConstructor();
        return moduleConstructor.newInstance();
    }

    private Module<S, C, E>[] getInstantiatedModules()
    {
        if (instantiatedModules == null) {
            try {
                Class<? extends Module<S, C, E>> orderedModuleClasses[] = getOrderedModuleClasses();
                instantiatedModules = (Module<S, C, E>[]) Array.newInstance(Module.class, orderedModuleClasses.length);
                int i = 0;
                for (Class<? extends Module<S, C, E>> moduleClass : orderedModuleClasses) {
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

    private boolean execute(Module<S, C, E> module, S substrate, C context) {
        if (module == null)
            throw new IllegalStateException(
                "module has a null value.");
        return module.execute(substrate, context);
    }

    @Override
    public boolean execute(S substrate, C context) {
        Module<S, C, E>[] orderedModules = getInstantiatedModules();
        boolean result = true;
        for (Module<S, C, E> module : orderedModules)
            result = result && execute(module, substrate, context);
        return result;
    }
}
