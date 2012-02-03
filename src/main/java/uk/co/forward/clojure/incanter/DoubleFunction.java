package uk.co.forward.clojure.incanter;

/**
 * User: antonio
 * Date: 01/02/2012
 * Time: 11:34
 */

import org.jblas.DoubleMatrix;

/**
 * Ported from
 */
public interface DoubleFunction {
    DoubleMatrix apply(DoubleMatrix argument);
}
