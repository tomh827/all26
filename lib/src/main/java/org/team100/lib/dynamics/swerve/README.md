# Swerve Dynamics

Dynamics of the swerve drivetrain.

<img src="image_swerve.png" width="300" />

Divide the problem into three pieces:

* Determine the total rigid-body forces and torques for the desired rigid-body accelerations, using $F=ma$ and $\tau=I\alpha$.
* Find the set of drive forces (the "grasp") that sum to the total.
* Project those drive forces into the wheel axes.

See [WRENCH.md](../WRENCH.md) for background.

See [SE2](../se2/README.md) regarding the rigid-body effort.

## Contact Points

In the Swerve drive, the contact points are fixed.  For example:

```math
\mathbf{r_1}
=
\begin{bmatrix}
1 \\
1
\end{bmatrix}
\tag{1}
```

```math
\mathbf{r_2}
=
\begin{bmatrix}
1 \\
-1
\end{bmatrix}
\tag{2}
```

```math
\mathbf{r_3}
=
\begin{bmatrix}
-1 \\
1
\end{bmatrix}
\tag{3}
```

```math
\mathbf{r_4}
=
\begin{bmatrix}
-1 \\
-1
\end{bmatrix}
\tag{4}
```

## Free Actuation

In the swerve drive, the contact directions (wheel angles)
are variable, and centrifugal forces are not at all
negligible, so the only actuation case that makes sense
is the "free" case.

Also, we should model the "side force" resolved by
each wheel, using the "slip angle" concept from
tire dynamics.

The free dynamics are:

```math
\begin{bmatrix}
F_x \\
F_y \\
\tau
\end{bmatrix}
=
\mathbf{G}
\begin{bmatrix}
f_{1x} \\
f_{1y} \\
f_{2x} \\
f_{2y} \\
f_{3x} \\
f_{3y} \\
f_{4x} \\
f_{4y} 
\end{bmatrix}
\tag{5}
```
where

```math
\mathbf{G}
=
\begin{bmatrix}
1 & 0 & 1 & 0 & 1 & 0 & 1 & 0 \\
0 & 1 & 0 & 1 & 0 & 1 & 0 & 1 \\
-r_{1y} & r_{1x} & -r_{2y} & r_{2x} &
-r_{3y} & r_{3x} & -r_{4y} & r_{4x} &
\end{bmatrix}
\tag{6}
```

For the example above, this is

```math
\mathbf{G}
=
\begin{bmatrix}
1 & 0 & 1 & 0 & 1 & 0 & 1 & 0 \\
0 & 1 & 0 & 1 & 0 & 1 & 0 & 1 \\
-1 & 1 & 1 & 1 & -1 & -1 & 1 & -1 
\end{bmatrix}
\tag{6}
```

and

```math
\mathbf{G^{-1}}
=
\begin{bmatrix}
 0.25 & 0    & -0.125 \\
 0    & 0.25 &  0.125 \\
 0.25 & 0    &  0.125 \\
 0    & 0.25 &  0.125 \\
 0.25 & 0    & -0.125 \\
 0    & 0.25 & -0.125 \\
 0.25 & 0    &  0.125 \\
 0    & 0.25 & -0.125 \\
\end{bmatrix}
\tag{6}
```