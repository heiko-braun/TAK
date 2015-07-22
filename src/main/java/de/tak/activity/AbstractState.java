package de.tak.activity;

/**
 * @author Heiko Braun
 * @since 22/07/15
 */
public abstract class AbstractState implements ParticipationState {
    @Override
    public void confirm(ParticipationContext context) {
        context.setState(new StateConfirmed());
    }

    @Override
    public void cancel(ParticipationContext context) {
        context.setState(new StateCancelled());
    }
}
