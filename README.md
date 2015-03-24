# LearnLib <-> jLearn bridge

This artifact makes the learning algorithms of the [old, closed-source version of LearnLib][1] available in the [new LearnLib framework][2].

## Algorithms
The following is the list of algorithms (class names) provided in these package. Please note that the old LearnLib only supported learning Mealy machines; as a result, all the algorithms in this artifact are Mealy machine learning algorithms as well, and hence parameterized with the usual type parameters `I` and `O`. Furthermore, all algorithms are declared in the package `de.learnlib.jlearn`, and take as arguments the input alphabet (`Alphabet<I>`), the oracle (`MembershipOracle<I,Word<O>>`), and, optionally, a `JLearnSplitterCreator`. The splitter creator argument is optional, and may be omitted - in this case, a "default" counterexample handler is used.

* `JLearnAngluinMealy`: The L* algorithm from the old LearnLib (formerly `Angluin`)
* `JLearnDHCMealy`: The DHC algorithm from the old LearnLib (formerly `DHC`)
* `JLearnDHCModularMealy`: The modular variant of the DHC algorithm from the old LearnLib (formerly `DHCModular`)
* `JLearnObservationPackMealy`: The Observation Pack algorithm from the old LearnLib (formerly `ObservationPack`)

## License
The *source code* of this artifact is distributed under the terms of the [GNU Lesser General Public License, version 3 (LGPLv3)][3]. However, to build and use this artifact, you need the closed-source version of LearnLib, which is distributed under an [academic-use-only license][1]. By building and using this artifact, you hence agree to the closed-source LearnLib license terms.

[1]: http://ls5-www.cs.tu-dortmund.de/projects/learnlib/index.php
[2]: http://learnlib.de/
[3]: https://www.gnu.org/licenses/lgpl-3.0.html
