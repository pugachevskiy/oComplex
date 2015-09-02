package com.openComplex.app.DynamicalSystems.IteratedMaps;


public class IMModel {
    public double[] coeff = new double[5];

    private double[] fix;
    private String[] type;
    private String[] info;


    private double zeroApproach =  0.00000001;

    public void solve () {
        fix = new double[coeff.length - 1];

        coeff[1] = coeff[1] - 1;
        double estimateX = -Double.MAX_VALUE, x;
        for (int i = 0; i < coeff.length - 1; i++) {
            estimateX = searchX(estimateX);
            if(estimateX < Double.MAX_VALUE) {
                try{
                    x = newton(estimateX);
                    fix[i] = x;
                }
                catch (StackOverflowError e) {
                    fix[i] = Double.MAX_VALUE;
                }
            } else {
                fix[i] = Double.MAX_VALUE;
            }
        }
        type = fixpointType(coeff, fix);
        info = checkMultiplicity(coeff, fix);

//        for(int i = 0; i < coeff.length -1; i++) {
//            System.out.println("fix: " + fix[i] + " type: " + type[i] + " info: " + info[i]);
//        }
    }

    private double searchX(double lastEstimateX) {
        double temp, previous, x;
        x = -10;
        previous = compute(coeff, -10);
        for (int i = 0; i < 40; i++) {
            temp = compute(coeff, x);
            if((temp == 0 ||(previous*temp < 0)) &&
                    (x > lastEstimateX || lastEstimateX == -Double.MAX_VALUE)) {
                return x;
            }
            previous = temp;
            x = x + 0.5;
        }
        return Double.MAX_VALUE;
    }

    private double newton(double x)
    {
        if (Math.abs(compute(coeff, x)) <= zeroApproach) {
            return x;
        }
        else {
            return newton(x - compute(coeff, x) / compute(derivate(coeff), x));
        }
    }

    private String[] fixpointType(double[] coeff, double[] fix) {
        String[] type = new String[fix.length];
        for (int i = 0; i < fix.length; i++) {
            if(fix[i] < Double.MAX_VALUE) {
                double [] firstDerivationCoeff = derivate(coeff);
                double firstDerivationSolution = compute(firstDerivationCoeff, fix[i]);
                if(firstDerivationSolution < 1) {
                    type[i] = "asymptotic stable";
                } else if (firstDerivationSolution > 1) {
                    type[i] = "unstable";
                } else {
                    double [] secondDerivationCoeff = derivate(firstDerivationCoeff);
                    double secondDerivationSolution = compute(secondDerivationCoeff, fix[i]);
                    if(secondDerivationSolution != 0) {
                        type[i] = "semi-asymptotic";
                    } else {
                        double [] thirdDerivationCoeff = derivate(secondDerivationCoeff);
                        double thirdDerivationSolution = compute(thirdDerivationCoeff, fix[i]);
                        if(thirdDerivationSolution < 0) {
                            type[i] = "asymptotic stable";
                        } else if(thirdDerivationSolution > 0) {
                            type[i] = "unstable";
                        }
                    }
                }
            } else {
                type[i] = "---";
            }
        }

        return type;
    }

    private double compute(double[] coeff, double x) {
        double temp = 0;
        for (int i = 0; i < coeff.length; i++) {
            temp = temp + coeff[i] * Math.pow(x, i);
        }
        return temp;
    }

    private double[] derivate(double[] coeff) {
        double[] temp = new double[coeff.length-1];
        for(int i = 0; i < coeff.length; i++) {
            if(i > 0) {
                temp[i-1] = coeff[i] * i;
            }
        }
        return temp;
    }


    private String[] checkMultiplicity(double[] coeff, double[] fix) {

        String[] info = new String[fix.length];

        int degree;

        if(coeff[4] == 0 && coeff[3] == 0 && coeff[2] == 0 && coeff [1] == 0 && coeff[0] == 0) {
            for(int i = 0; i < coeff.length -1; i++) {
                info[i] = "infinite fixpoint";
            }
        } else if(coeff[4] == 0 && coeff[3] == 0 && coeff[2] == 0) {
            degree = 1;
            for(int i = 0; i < coeff.length -1; i++) {
                if(fix[i] != Double.MAX_VALUE) {
                    degree = degree -1;
                    info[i] = "simple zero";
                } else {
                    info[i] = "no fixpoint";
                }
            }
        } else if(coeff[4] == 0 && coeff[3] == 0) {
            degree = 2;
            for(int i = 0; i < coeff.length -1; i++) {
                if (fix [i] != Double.MAX_VALUE) {
                    if (Math.abs(compute(derivate(coeff), fix[i])) < zeroApproach) {
                            degree = degree - 2;
                            info[i] = "double zero";
                        } else  {
                            degree = degree - 1;
                            info[i] = "simple zero";
                    }
                }else {
                    if (degree <= 0) {
                        info[i] = "no fixpoint";
                    } else {
                        degree = degree - 1;
                        info[i] = "imaginary fixpoint";
                    }
                }
            }
        } else if(coeff[4] == 0) {
            degree = 3;
            for(int i = 0; i < coeff.length -1; i++) {
                if (fix [i] != Double.MAX_VALUE) {
                    if (Math.abs(compute(derivate(coeff), fix[i])) < zeroApproach && Math.abs(compute(derivate(derivate(coeff)), fix[i])) < zeroApproach) {
                        degree = degree - 3;
                        info[i] = "triple zero";
                    } else if (Math.abs(compute(derivate(coeff), fix[i])) < zeroApproach) {
                        degree = degree - 2;
                        info[i] = "double zero";
                    } else  {
                        degree = degree - 1;
                        info[i] = "simple zero";
                    }
                }else {
                    if (degree <= 0) {
                        info[i] = "no fixpoint";
                    } else {
                        degree = degree - 1;
                        info[i] = "imaginary fixpoint";
                    }
                }
            }
        } else {
            degree = 4;
            for(int i = 0; i < coeff.length -1; i++) {

                if (fix [i] != Double.MAX_VALUE) {
                    if (Math.abs(compute(derivate(coeff), fix[i])) < zeroApproach && Math.abs(compute(derivate(derivate(coeff)), fix[i])) < zeroApproach
                            && Math.abs(compute(derivate(derivate(derivate(coeff))), fix[i])) < zeroApproach) {
                        degree = degree - 4;
                        info[i] = "quad zero";
                    } else if (Math.abs(compute(derivate(coeff), fix[i])) < zeroApproach &&
                            Math.abs(compute(derivate(derivate(coeff)), fix[i])) < zeroApproach) {
                        degree = degree - 3;
                        info[i] = "triple zero";
                    } else if (Math.abs(compute(derivate(coeff), fix[i])) < zeroApproach) {
                        degree = degree - 2;
                        info[i] = "double zero";
                    } else  {
                        degree = degree - 1;
                        info[i] = "simple zero";
                    }
                }else {
                    if (degree <= 0) {
                        info[i] = "no fixpoint";
                    } else {
                        degree = degree - 1;
                        info[i] = "imaginary fixpoint";
                    }
                }
            }
        }
    return info;
    }

    public double[] getFix() {
        return fix;
    }

    public String[] getType() {
        return type;
    }

    public String[] getInfo() {
        return info;
    }

}
