package incanter;

import clojure.lang.*;
import org.jblas.*;
import uk.co.forward.clojure.incanter.DoubleDoubleFunction;
import uk.co.forward.clojure.incanter.DoubleFunction;
import uk.co.forward.clojure.incanter.ScalarFunction;
import uk.co.forward.clojure.incanter.ScalarScalarFunction;

import java.util.Iterator;

/**
 * User: Antonio Garrote
 * Date: 01/02/2012
 * Time: 10:20
 */
public class Matrix implements Sequential, ISeq, Counted, IObj {
    public boolean oneDimensional = false;
    IPersistentMap meta;
    protected DoubleMatrix matrix;

    /**************************************
     * MATRIX CONSTRUCTORS
     **************************************/

    public Matrix(Matrix wrapper){
        this(wrapper.matrix);
    }

    public Matrix(int nrow, int ncol) {
        this(nrow,ncol,0);
    }

    public Matrix(int nrow, int ncol, Number initValue) {
        this.matrix = new DoubleMatrix(nrow,ncol);
        this.meta = null;
        for(int i = 0; i < nrow; i++)
            for(int j = 0; j < ncol; j++)
                matrix.put(i, j, initValue.doubleValue());
        if(matrix.rows == 1 || matrix.columns == 1)
            this.oneDimensional = true;
    }

    public Matrix(double[] data) {
        this.matrix = new DoubleMatrix(data);
        this.oneDimensional = true;
    }

    public Matrix(double[] data, int ncol) {
        matrix = new DoubleMatrix(data.length/ncol, ncol);
        this.meta = null;
        if(matrix.rows == 1 || matrix.columns == 1)
            this.oneDimensional = true;
        for(int i = 0; i < matrix.rows; i++)
            for(int j = 0; j < matrix.columns; j++)
                matrix.put(i, j, data[i*ncol+j]);
    }

    public Matrix(double[][] data) {
        matrix = new DoubleMatrix(data);
        this.meta = null;
        if(matrix.rows == 1 || matrix.columns == 1)
            this.oneDimensional = true;
    }

    public Matrix(IPersistentMap meta, double[][] data) {
        this(data);
        this.meta = meta;
    }


    public Matrix(DoubleMatrix mat) {
        this(mat.rows, mat.columns);
        this.assign(mat);
    }

    public Matrix(int rows, int columns, double[] elements, boolean oneDimensional) {
        this(rows, columns);

        int inserted = 0;

        for(int i = 0; i < matrix.columns; i++) {
            for(int j = 0; j < matrix.rows; j++) {
                if(inserted < elements.length) {
                    matrix.put(i, j, matrix.data[i * matrix.rows + j]);
                    inserted++;
                } else {
                    break;
                }
            }
            if(inserted == elements.length)
                break;
        }

        this.meta = null;
        this.oneDimensional = oneDimensional;
    }


    public Matrix(Seqable coll, int rows, int columns) {
        this(rows, columns);
        this.meta = null;
        ISeq seq = coll.seq();
        for(int i = 0; i < (rows); i++) {
            for(int j = 0; j < (columns); j++) {
                matrix.put(i, j, ((Number) (seq.first())).doubleValue());
                seq = seq.next();
            }
        }
        if(rows==1 && columns ==1)
            this.oneDimensional = true;
    }

    /**************************************
     * MATRIX METHODS
     **************************************/
    public int rows() {
        return matrix.rows;
    }
    
    public int columns() {
        return matrix.columns;
    }

    public void assign(DoubleMatrix matrix) {
        this.matrix = matrix.dup();
        if(matrix.columns == 1 || matrix.rows == 1) {
            this.oneDimensional = true;
        }
    }
    
    public Matrix assign(DoubleFunction f) {

        this.matrix = f.apply(this.matrix);
        return this;
    }
    
    public Matrix assign(Matrix matrix, DoubleDoubleFunction f)  {
        f.apply(this.matrix,matrix.matrix);
        return this;
    }
    
    public Matrix mmul(Matrix m) {
        return Matrix.wrap(this.matrix.mmul(m.matrix));
    }
    
    public DoubleMatrix apply(ScalarFunction f) {
        DoubleMatrix transformed = DoubleMatrix.zeros(matrix.rows,matrix.columns);
        applyi(transformed, f);
        return transformed;
    }

    public static void applyi(DoubleMatrix transformed, ScalarFunction f) {
        for(int i = 0; i < (transformed.rows); i++) {
            for(int j = 0; j < (transformed.columns); j++) {
                double val = transformed.get(i, j);
                double result = f.apply(val);
                transformed.put(i, j, result);
            }
        }
    }

    public static void applyi(DoubleMatrix x, DoubleMatrix y, ScalarScalarFunction f) {
        for(int i = 0; i < (x.rows); i++) {
            for(int j = 0; j < (x.columns); j++) {
                double valx = x.get(i, j);
                double valy = y.get(i,j);
                double result = f.apply(valx,valy);
                x.put(i, j, result);
            }
        }        
    }    

    public DoubleMatrix wrapped() {
        return matrix;
    }

    public Matrix copy() {
        return Matrix.wrap(this.matrix);
    }
    

    public Matrix transpose() {
        return Matrix.wrap(this.matrix.transpose());
    }

    public Matrix viewSelection(int[] rows, int[] columns) {
        Matrix mat = new Matrix(matrix.get(rows, columns));
        if(rows.length == 1 || columns.length == 1)
            mat.oneDimensional = true;

        return(mat);
    }

    public Matrix like(int rows, int columns) {
        return new Matrix(rows, columns);
    }
    
    public static Matrix wrap(Object otherMatrix) {
        if(otherMatrix instanceof Matrix) {
            return (Matrix) otherMatrix;
        } else if(otherMatrix instanceof DoubleMatrix) {
            Matrix wrapper = new Matrix(0,0);
            wrapper.matrix = ((DoubleMatrix) otherMatrix).dup();
            if(wrapper.matrix.columns == 1 || wrapper.matrix.rows == 1)
                wrapper.oneDimensional = true;
            return wrapper;
        }  else {
            return null;
        }
    }

    public double getQuick(int r, int c) {
        return this.matrix.get(r,c);
    }
    
    public void set(int r, int c, double val) {
        this.matrix.put(r,c,val);
    }

    public Matrix viewDice() {
        Matrix m = Matrix.wrap(this.matrix.transpose());
        return m;
    }
    
    public double det() {
        Decompose.LUDecomposition<DoubleMatrix> lup = Decompose.lu(this.matrix);
        return -lup.u.diag().prod();
    }
    
    public double trace() {
        return this.matrix.diag().sum();
    }
    
    public Matrix diag() {
        if(matrix.columns==matrix.rows) {
            Matrix m = Matrix.wrap(matrix.diag());
            return m;
        } else {
            if(matrix.columns == 1 || matrix.rows == 1) {
                double[] values = this.matrix.toArray();
                DoubleMatrix toInsert = DoubleMatrix.zeros(
                        Math.max(matrix.rows,matrix.columns),
                        Math.max(matrix.rows,matrix.columns));
                for(int i=0; i<values.length; i++)
                    toInsert.put(i,i,values[i]);
                Matrix m = Matrix.wrap(toInsert);
                return m;
            } else {
                int min = Math.min(matrix.columns,matrix.rows);
                int[] indices = new int[min];
                for(int i=0; i<min; i++)
                    indices[i] = i;
                Matrix m = new Matrix(this.matrix.get(indices,indices).diag());
                return m;
            }
        }
    }

    public static Matrix cholesky(Matrix matrix) {
        return new Matrix(Decompose.cholesky(matrix.matrix));
    }
    
    public static DoubleMatrix[]  fullsvd(Matrix matrix) {
        return Singular.fullSVD(matrix.matrix);
    }

    public static DoubleMatrix[]  sparsesvd(Matrix matrix) {
        return Singular.sparseSVD(matrix.matrix);
    }

    public static DoubleMatrix eig(Matrix matrix) {
        return Eigen.eigenvalues(matrix.matrix).getReal();
    }
    public static DoubleMatrix[] symEig(Matrix matrix) {
        return Eigen.symmetricEigenvectors(matrix.matrix);
    }
    
    public static DoubleMatrix[] decompLU(Matrix matrix) {
        Decompose.LUDecomposition<DoubleMatrix> lup = Decompose.lu(matrix.matrix);
        DoubleMatrix[] result = new DoubleMatrix[2];
        result[0] = lup.l;
        
        if(lup.u.rows < matrix.matrix.rows) {
            result[1] = DoubleMatrix.concatVertically(lup.u,DoubleMatrix.zeros((matrix.matrix.rows - lup.u.rows),lup.u.columns));
        } else {
            result[1] = lup.u;
        }

        return result;
    } 
    
    public static int rank(Matrix matrix) {
        DoubleMatrix[] suv = fullsvd(matrix);
        DoubleMatrix evs = suv[1].diag();
        int counter = 0;
        for(int i=0; i<evs.columns; i++) {
            if(evs.get(i,i)>0) {
                counter++;
            }
        }
        return counter;
    }
    
    public Matrix solve(DoubleMatrix other) {
        DoubleMatrix sol = Solve.solve(this.matrix,other);
        return Matrix.wrap(sol);
    }


    public double[][] toArray() {
        return matrix.toArray2();
    }

    public double[] toArray1() {
        return matrix.toArray();
    }

    /**************************************
     * ISeq METHODS
     **************************************/

    public Object first() {
        if(this.matrix.rows == 0 || this.matrix.columns == 0) return(null);

        if(this.oneDimensional && (matrix.columns == 1 || matrix.rows == 1))
            return(matrix.get(0, 0));
        else {
            int[] subset = new int[matrix.columns];
            for(int i=0; i<subset.length; i++)
                subset[i] = i;

            return new Matrix(matrix.get(0, subset));
        }
    }

    public ISeq next() {
        if(matrix.rows == 0 || matrix.columns == 0)
            return(null);
        else if(!this.oneDimensional && matrix.rows == 1) {
            return(null);
        }
        else if(this.oneDimensional && (matrix.columns == 1 || matrix.rows == 1)) {

            if(matrix.rows > 1)       {

                int[] subsetColumns = new int[matrix.columns];
                for(int i=0; i<matrix.columns; i++)
                    subsetColumns[i] = i;
                int[] subsetRows = new int[matrix.rows-1];
                for(int i=0; i<matrix.rows-1; i++)
                    subsetRows[i] = i+1;
                return new Matrix(matrix.get(subsetRows, subsetColumns));
            } else if(matrix.columns > 1) {
                int[] subsetColumns = new int[matrix.columns-1];
                for(int i=0; i<matrix.columns-1; i++)
                    subsetColumns[i] = i+1;
                int[] subsetRows = new int[matrix.rows];
                for(int i=0; i<matrix.rows; i++)
                    subsetRows[i] = i;

                return new Matrix(matrix.get(subsetRows, subsetColumns));
            } else {
                return null;
            }
        }
        else {
            int[] subsetColumns = new int[matrix.columns];
            for(int i=0; i<matrix.columns; i++)
                subsetColumns[i] = i;
            int[] subsetRows = new int[matrix.rows-1];
            for(int i=0; i<matrix.rows-1; i++)
                subsetRows[i] = i+1;

            Matrix m = new Matrix(matrix.get(subsetRows, subsetColumns));
            m.oneDimensional = false;
            
            return m;
        }
    }

    public ISeq more() {
        ISeq result = this.next();
        if(result != null)
            return result;
        else
            return new Matrix(0, 0, 0);
    }

    public Matrix cons(Object o) {

        if(o instanceof Matrix) {
            Matrix m = (Matrix)o;
            double[][] newData = new double[matrix.rows + m.matrix.rows][matrix.columns];
            for(int i = 0; i < (matrix.rows); i++)
                for(int j = 0; j < (matrix.columns); j++)
                    newData[i][j] = matrix.get(i, j);

            for(int i = 0; i < m.matrix.rows; i++)
                for(int j = 0; j < (matrix.columns); j++)
                    newData[matrix.rows + i][j] = m.matrix.get(i, j);

            return(new Matrix(newData));
        }
        else if(o instanceof Seqable) {
            ISeq v = ((Seqable)o).seq();
            double[][] newData = new double[matrix.rows + 1][matrix.columns];
            for(int i = 0; i < (matrix.rows); i++)
                for(int j = 0; j < (matrix.columns); j++)
                    newData[i][j] = matrix.get(i, j);
            ISeq restObj = v;
            int cols = 0;
            while(cols < matrix.columns) {
                newData[matrix.rows][cols] = ((Number)(restObj.first())).doubleValue();
                restObj = restObj.next();
                cols++;
            }

            return(new Matrix(newData));
        }
        else
            return(null);
    }

    public int count() {
        if(this.oneDimensional)
            return matrix.columns*matrix.rows;
        else
            return matrix.rows;
    }

    public Matrix seq() {
        if((matrix.columns*matrix.rows) > 0)
            return this;
        else
            return null;
    }

    public IPersistentCollection empty() {
        return(new Matrix(0, 0));
    }
    
    public boolean equiv(DoubleMatrix obj) {
            return this.equals(obj);
    }

    public boolean equiv(Matrix obj) {
        return this.matrix.equals(obj.matrix);
    }

    public boolean equiv(double[] obj) {
        return this.equals(new Matrix(obj));
    }

    public boolean  equiv(double[][] obj) {
        return this.equals(new Matrix(obj));
    }
    
    public boolean equiv(Seqable coll) {

        ISeq thisSeq = this.seq();
        ISeq otherSeq = coll.seq();
        while(thisSeq != null || otherSeq != null) {
            Object thisFirst = thisSeq.first();
            Object otherFirst = otherSeq.first();
            boolean isEqual;
            if(thisFirst instanceof Double) {
                if(otherFirst instanceof Long) {
                    isEqual = ((Double) thisFirst)==((Long)otherFirst).doubleValue();
                } else if(otherFirst instanceof Float) {
                    isEqual = ((Double) thisFirst)==((Float)otherFirst).doubleValue();
                } else if(otherFirst instanceof Integer) {
                    isEqual = ((Double) thisFirst)==((Integer)otherFirst).doubleValue();
                } else if(otherFirst instanceof Short) {
                    isEqual = ((Double) thisFirst)==((Short)otherFirst).doubleValue();
                } else if(otherFirst instanceof Double) {
                    isEqual = ((Double) thisFirst)==((Double)otherFirst).doubleValue();
                } else {
                    isEqual = false;
                }
            } else {
                isEqual = thisFirst.equals(otherFirst);
            }
            
            if(isEqual) {
                thisSeq = thisSeq.next();
                otherSeq = otherSeq.next();
            } else {
                return false;
            }
        }

        return thisSeq == null && otherSeq == null;
    }

    public boolean equiv(Iterable obj) {
        Iterator it = obj.iterator();
        ISeq thisSeq = this.seq();

        while(it.hasNext() || thisSeq != null ){
            Object thisFirst = thisSeq.first();
            Object otherFirst = it.next();
            boolean isEqual;
            if(thisFirst instanceof Double) {
                if(otherFirst instanceof Long) {
                    isEqual = ((Double) thisFirst)==((Long)otherFirst).doubleValue();
                } else if(otherFirst instanceof Float) {
                    isEqual = ((Double) thisFirst)==((Float)otherFirst).doubleValue();
                } else if(otherFirst instanceof Integer) {
                    isEqual = ((Double) thisFirst)==((Integer)otherFirst).doubleValue();
                } else if(otherFirst instanceof Short) {
                    isEqual = ((Double) thisFirst)==((Short)otherFirst).doubleValue();
                } else if(otherFirst instanceof Double) {
                    isEqual = ((Double) thisFirst)==((Double)otherFirst).doubleValue();
                } else {
                    isEqual = false;
                }
            } else {
                isEqual = thisFirst.equals(otherFirst);
            }

            if(isEqual) {
                thisSeq = thisSeq.next();
            } else {
                return false;
            }
        }
        return thisSeq == null && !it.hasNext();
    }

    public boolean equiv(Object obj) {
        if(obj instanceof Matrix) {
            return equiv((Matrix) obj);
        } if(obj instanceof DoubleMatrix) {
            return equiv((DoubleMatrix) obj);
        } else if(obj instanceof Seqable) {
            return equiv((Seqable) obj);
        } else if(obj instanceof Iterable) {
            return equiv((Iterable) obj);
        } else {
            return equals(obj);
        }
    }

    public String toString() {
        return matrix.toString().replace(";","\n");
    }

    /**************************************
     * IOBJ METHODS
     **************************************/
    public IPersistentMap meta(){
        return this.meta;
    }

    public Matrix withMeta(IPersistentMap meta) {
        return new Matrix(meta, matrix.toArray2());
    }
}
