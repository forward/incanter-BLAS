package uk.co.forward.clojure.incanter;

import org.jblas.DoubleMatrix;

/**
 * User: Antonio Garrote
 * Date: 01/02/2012
 * Time: 11:44
 */
public interface DoubleDoubleFunction {
    DoubleMatrix apply(DoubleMatrix x, DoubleMatrix y);
}
