## About

Modified version of [incanter-core](http://incanter.org/) to use [jBLAS](https://github.com/mikiobraun/jblas) as the matrix manipulation library instead of Parallel COLT.

## Usage

In your project.clj file just add the modified library and exclude standard incanter-core:

    [incanter/incanter "1.3.0-SNAPSHOT"
      :exclusions [incanter/incanter-core]]
                  [uk.co.forward/incanter-core-jblas "1.3.0-SNAPSHOT"]]

## Benchmarking

The tests can be run using the *incanter/benchmark.clj* file.

## Build

Use Maven:

    $ mvn compile
    $ mvn clojure:compile
    $ mvn package
    $ mvn assembly:single # optional

## Tests
    
After compiling Java and Clojure sources, you can run incanter-core tests + some additional smoke tests can be run using this Maven goal:
    
    $ mvn clojure:test

## Credit & License

Incanter has been written by David Edgar Liebke and it is provided under the [Eclipse Public License 1.0](https://github.com/liebke/incanter/blob/master/epl-v10.html).
jBLAS has been written by Mikio L. Braun and it is provded under a modified [BSD License ](https://github.com/mikiobraun/jblas/blob/master/COPYING)