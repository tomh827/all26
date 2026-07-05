package org.team100.lib.dynamics.swerve;

import org.junit.jupiter.api.Test;

import edu.wpi.first.math.MatBuilder;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.Nat;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.numbers.N8;

public class SwerveDynamicsTest {
    // Confirm the pseudoinverse of the example.
    @Test
    void test0() {
        Matrix<N3, N8> m = MatBuilder.fill(Nat.N3(), Nat.N8(),
                1, 0, 1, 0, 1, 0, 1, 0, //
                0, 1, 0, 1, 0, 1, 0, 1, //
                -1, 1, 1, 1, -1, -1, 1, -1);
        Matrix<N8, N3> minv = new Matrix<>(m.getStorage().pseudoInverse());
        System.out.println(minv);
    }

}
