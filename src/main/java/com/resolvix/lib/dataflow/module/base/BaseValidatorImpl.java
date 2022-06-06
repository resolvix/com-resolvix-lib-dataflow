package com.resolvix.lib.dataflow.module.base;

import com.resolvix.lib.dataflow.api.Event;
import com.resolvix.lib.dataflow.context.api.ValidatorContext;
import com.resolvix.lib.dataflow.api.Substrate;
import com.resolvix.lib.dataflow.module.api.Validator;

public abstract class BaseValidatorImpl<V extends BaseValidatorImpl<V, S, C, E>, S extends Substrate<S>, C extends ValidatorContext<E>, E extends Event<E>>
    extends BaseModuleImpl<V, S, C, E>
    implements Validator<S, C, E>
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
    protected abstract boolean validate(S substrate, C context);

    @Override
    public final boolean execute(S substrate, C context) {
        return validate(substrate, context);
    }
}
