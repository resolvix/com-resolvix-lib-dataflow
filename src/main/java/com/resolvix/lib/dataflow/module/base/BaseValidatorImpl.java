package com.resolvix.lib.dataflow.module.base;

import com.resolvix.lib.dataflow.api.Event;
import com.resolvix.lib.dataflow.context.api.ValidatorContext;
import com.resolvix.lib.dataflow.api.Substrate;
import com.resolvix.lib.dataflow.module.api.Validator;
import org.checkerframework.checker.units.qual.C;

public abstract class BaseValidatorImpl<V extends BaseValidatorImpl<V, I, O, C, S, E>, I, O, C extends ValidatorContext<E>, S extends Substrate<S>, E extends Event<E>>
    extends BaseModuleImpl<V, I, O, C, S, E>
    implements Validator<I, O, C, S, E>
{

    /**
     * Validates the context, {@code C}, based on the services supplied by
     * the system substrate, {@code S}.
     *
     * @param substrate the system substrate
     * @param context the validator context
     * @return true, if the validation process was successful; false,
     *  otherwise
     */
    protected abstract boolean validate(I input, C context, S substrate);

    @Override
    public final boolean execute(I input, C context, S substrate) {
        return validate(input, context, substrate);
    }
}
