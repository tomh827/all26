package org.team100.lib.reference.se2;

import org.team100.lib.state.ControlSE2;
import org.team100.lib.state.ModelSE2;

/** Use a delegate for cartesian reference, and a target for rotation. */
public class TargetReferenceSE2 implements ReferenceSE2 {

    private final ReferenceSE2 m_delegate;

    public TargetReferenceSE2(ReferenceSE2 delegate) {
        m_delegate = delegate;
    }

    public void initialize(ModelSE2 measurement) {
        m_delegate.initialize(measurement);
    }

    public ModelSE2 current() {
        return m_delegate.current();
    }

    public ControlSE2 next() {
        return m_delegate.next();
    }

    public boolean done() {
        return m_delegate.done();
    }

    public ModelSE2 goal() {
        return m_delegate.goal();
    }
}
