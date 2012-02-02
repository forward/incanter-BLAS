package uk.co.forward.clojure.incanter;

import incanter.Matrix;
import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;

/**
 * User: antonio
 * Date: 01/02/2012
 * Time: 11:39
 */

/**
 * Wraps org.jblas.MatrixFunctions to offer the same interface as cern.jet.math.tdouble.DoubleFunctions
 */
public class DoubleFunctions {

    /*
    public static DoubleFunction abs() {
        return new DoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix argument) {
                return MatrixFunctions.abs(argument);
            }
        };
    }

    public static DoubleFunction acos() {
        return new DoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix argument) {
                return MatrixFunctions.acos(argument);
            }
        };
    }

    public static DoubleFunction asin() {
        return new DoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix argument) {
                return MatrixFunctions.asin(argument);
            }
        };
    }

    public static DoubleFunction atan() {
        return new DoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix argument) {
                return MatrixFunctions.atan(argument);
            }
        };
    }

    public static DoubleDoubleFunction atan2() {
        return new DoubleDoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix x, final double y) {
                ScalarFunction atan2 = new ScalarFunction() {
                    public double apply(double x) {
                        return Math.atan2(x,y);
                    }
                };
                return Matrix.wrap(x).apply(atan2);
            }
        };
    }

    public static DoubleDoubleFunction compare() {
        return new DoubleDoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix x, final double y) {
                ScalarFunction compare = new ScalarFunction() {
                    public double apply(double x) {
                        if(x<y) {
                            return 1.0;
                        } else if(x>y){
                            return -1.0;
                        } else {
                            return 0.0;
                        }
                    }
                };
                return Matrix.wrap(x).apply(compare);
            }
        };
    }

    public static DoubleFunction ceil() {
        return new DoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix argument) {
                final double y = 0;
                ScalarFunction ceil = new ScalarFunction() {
                    public double apply(double x) {
                        return Math.ceil(x);
                    }
                };
                return Matrix.wrap(argument).apply(ceil);
            }
        };
    }

    public static DoubleFunction cos() {
        return new DoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix argument) {
                return MatrixFunctions.cos(argument);
            }
        };
    }

    public static DoubleDoubleFunction div() {
        return new DoubleDoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix argument, double y) {
                return argument.div(y);
            }
        };
    }
    */

    //////////


    public static DoubleFunction abs() {
        return new DoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix argument) {
                return MatrixFunctions.absi(argument);
            }
        };
    }

    public static DoubleFunction acos() {
        return new DoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix argument) {
                return MatrixFunctions.acosi(argument);
            }
        };
    }

    public static DoubleFunction asin() {
        return new DoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix argument) {
                return MatrixFunctions.asini(argument);
            }
        };
    }

    public static DoubleFunction atan() {
        return new DoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix argument) {
                return MatrixFunctions.atani(argument);
            }
        };
    }

    public static DoubleDoubleFunction atan2() {
        return new DoubleDoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix x, final DoubleMatrix y) {
                ScalarScalarFunction atan2 = new ScalarScalarFunction() {
                    public double apply(double x, double y) {
                        return Math.atan2(x,y);
                    }
                };
                Matrix.applyi(x, y, atan2);

                return x;
            }
        };
    }

    public static DoubleDoubleFunction compare() {
        return new DoubleDoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix x, DoubleMatrix y) {
                ScalarScalarFunction compare = new ScalarScalarFunction() {
                    public double apply(double x, double y) {
                        if(x<y) {
                            return 1.0;
                        } else if(x>y){
                            return -1.0;
                        } else {
                            return 0.0;
                        }
                    }
                };
                Matrix.applyi(x, y, compare);

                return x;
            }
        };
    }

    public static DoubleFunction ceil() {
        return new DoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix argument) {
                final double y = 0;
                ScalarFunction ceil = new ScalarFunction() {
                    public double apply(double x) {
                        return Math.ceil(x);
                    }
                };
                Matrix.applyi(argument, ceil);

                return argument;
            }
        };
    }

    public static DoubleFunction cos() {
        return new DoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix argument) {
                return MatrixFunctions.cosi(argument);
            }
        };
    }

    public static DoubleFunction sin() {
        return new DoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix argument) {
                return MatrixFunctions.sini(argument);
            }
        };
    }

    public static DoubleFunction tan() {
        return new DoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix argument) {
                return MatrixFunctions.tani(argument);
            }
        };
    }

    public static DoubleDoubleFunction div() {
        return new DoubleDoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix argument, DoubleMatrix y) {
                return argument.divi(y);
            }
        };
    }

    public static DoubleDoubleFunction plus() {
        return new DoubleDoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix argument, DoubleMatrix y) {
                argument = argument.addi(y);
                return argument;
            }
        };
    }

    public static DoubleDoubleFunction minus() {
        return new DoubleDoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix argument, DoubleMatrix y) {
                return argument.subi(y);
            }
        };
    }

    public static DoubleDoubleFunction mult() {
        return new DoubleDoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix argument, DoubleMatrix y) {
                return argument.muli(y);
            }
        };
    }

    public static DoubleDoubleFunction pow() {
        return new DoubleDoubleFunction() {
            public DoubleMatrix apply(DoubleMatrix argument, DoubleMatrix y) {
                return MatrixFunctions.powi(argument, y);
            }
        };
    }

    public static DoubleFunction sqrt() {
        return new DoubleFunction() {

            public DoubleMatrix apply(DoubleMatrix argument) {
                return MatrixFunctions.sqrti(argument);
            }
        };
    }

    public static DoubleFunction log() {
        return new DoubleFunction() {

            public DoubleMatrix apply(DoubleMatrix argument) {
                return MatrixFunctions.logi(argument);
            }
        };
    }

    public static DoubleFunction log2() {
        return new DoubleFunction() {

            public DoubleMatrix apply(DoubleMatrix argument) {
                final double y = 0;
                ScalarFunction ceil = new ScalarFunction() {
                    public double apply(double x) {
                        return Math.log(x)/Math.log(2);
                    }
                };
                Matrix.applyi(argument, ceil);

                return argument;
            }
        };
    }

    public static DoubleFunction log10() {
        return new DoubleFunction() {

            public DoubleMatrix apply(DoubleMatrix argument) {
                return MatrixFunctions.log10i(argument);
            }
        };
    }

    public static DoubleFunction exp() {
        return new DoubleFunction() {

            public DoubleMatrix apply(DoubleMatrix argument) {
                return MatrixFunctions.expi(argument);
            }
        };
    }
}
