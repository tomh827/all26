# Mecanum Drive Dynamics

The dynamics of the Mecanum drivetrain.

There are many dynamics references; they all seem pretty
complicated.

Maybe instead, we could use the two-step method we initially
used for the differential drive:

* Determine SE2 components $F_x$, $F_y$, and $\tau$.
* Project one-fourth of each component into each drive contact,
  and sum them.



## References
* [Lin et al 2013](https://www.scirp.org/journal/paperinformation?paperid=31739)
* [Tlale et al 2008](https://researchspace.csir.co.za/server/api/core/bitstreams/07942b39-865d-4a47-8611-d4e1336b8bb3/content)
* [Zeidis et al 2019](https://onlinelibrary.wiley.com/doi/full/10.1002/zamm.201900173)
* [Muir 1987](https://publications.ri.cmu.edu/storage/publications/pub_files/1991/3/01087767-1.pdf)
* [Moreno-Caireta et al](https://www.iri.upc.edu/files/scidoc/2467-Model-Predictive-Control-for-a-Mecanum-wheeled-Robot-Navigating-among-Obstacles.pdf)