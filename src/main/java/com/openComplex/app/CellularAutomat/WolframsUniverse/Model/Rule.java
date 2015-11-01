package com.openComplex.app.CellularAutomat.WolframsUniverse.Model;

/**
 *  on 14/09/15.
 */
public class Rule {
    private boolean[] rule = new boolean[8];

    public void setRule(int ruleInt) {
        String stringBool = Integer.toBinaryString(ruleInt);
        char[] charBool = {0, 0, 0, 0, 0, 0, 0, 0};

        char[] stringToChar = stringBool.toCharArray();

        int index = 8 - stringToChar.length;
        for (char aStringToChar : stringToChar) {
            charBool[index] = aStringToChar;
            index = index + 1;
        }

        resetRule();

        for (int i = 0; i < charBool.length; i++) {
            rule[i] = charBool[i] == '1';
       //     System.out.print(rule[i] + " ");
       //     System.out.println();
        }

    }

    public boolean[] getRule(){
        return rule;
    }

    private void resetRule() {
        for (int i = 0; i < rule.length; i++) {
            rule[i] = false;
        }
    }


}
