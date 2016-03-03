package com.openprice.parser.linepredictor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.openprice.parser.LineType;

public class SimpleLinePredcitorTest {
    final SimpleLinePredcitor pred = new SimpleLinePredcitor();

    @Test
    public void itemTest1() throws Exception{
        assertEquals(LineType.Unpredictable, pred.classify("aa"));
    }

    @Test
    public void itemTest2() throws Exception{
        assertEquals(LineType.Unpredictable, pred.classify("a a"));
    }

    @Test
    public void itemTest3() throws Exception{
        assertEquals(LineType.Unpredictable, pred.classify(""));
    }

    @Test
    public void itemTest4() throws Exception{
        assertEquals(LineType.Unpredictable, pred.classify("a a a"));
    }

    @Test
    public void itemTest5() throws Exception{
        assertEquals(LineType.Unpredictable, pred.classify("a1 a a"));
    }

    @Test
    public void itemTest6() throws Exception{
        assertEquals(LineType.Unpredictable, pred.classify("F2000"));
    }

    @Test
    public void itemTest7() throws Exception{
        assertEquals(LineType.Unpredictable, pred.classify("2oo0"));
    }

    @Test
    public void isUnitPriceTest1(){
        assertEquals(LineType.UnitPrice, pred.classify("$6.48 int 2, j8.96 ea"));
    }

    @Test
    public void isUnitPriceTest2(){
        assertEquals(LineType.WeightPrice, pred.classify("0.405 kg Net 3 $2(   "));
    }

    @Test
    public void isUnitPriceTest3(){
        assertEquals(LineType.UnitPrice, pred.classify("1    3 $6.48 ea   "));
    }

    @Test
    public void isUnitPriceTest4(){
        assertEquals(LineType.UnitPrice, pred.classify("1.015 kg [3, $1.701‘k9"));
    }

    @Test
    public void isUnitPriceTest5(){
        assertEquals(LineType.WeightPrice, pred.classify("7.015k9@$1.?01’k9                       11.73"));
    }

    @Test
    public void isUnitPriceTest6(){
        assertEquals(LineType.UnitPrice, pred.classify("2 0 $1.98                              15.98"));
    }

    @Test
    public void isUnitPriceTest7(){
        assertEquals(LineType.UnitPrice, pred.classify("2 0 $0.02                                0-04"));
    }

    @Test
    public void isUnitPriceTest8(){
        assertEquals(LineType.UnitPrice, pred.classify("2 @$1.99                                              3.98"));
    }

    @Test
    public void isUnitPriceTest9(){
        assertEquals(LineType.UnitPrice, pred.classify("2 @ S2. 99                                            5.98"));
    }

    @Test
    public void isUnitPriceTest10(){
        assertEquals(LineType.WeightPrice, pred.classify("1.415 kg ~ $3.24/ kg                                  4.58"));
    }

    @Test
    public void isUnitPriceTest11(){
        assertEquals(LineType.WeightPrice, pred.classify("    0. 460 kg @ $3. 90/kg                                 1. 79"));
    }

    @Test
    public void isUnitPriceTest13(){
        assertEquals(LineType.UnitPrice, pred.classify("   $3 .28 lnt 4, $3.67 ea"));
    }

    @Test
    public void isUnitPriceTest14(){
        assertEquals(LineType.UnitPrice, pred.classify("   1 @$3.28 l~t 4                                        3.28"));
    }

    @Test
    public void isUnitPriceTest15(){
        assertEquals(LineType.UnitPrice, pred.classify("   $1.99 ea or 2/$3.58"));
    }

    @Test
    public void isUnitPriceTest16(){
        assertEquals(LineType.UnitPrice, pred.classify("   3 @$0 .05                                             0.15"));
    }

    @Test
    public void isUnitPriceTest17(){
        assertEquals(LineType.UnitPrice, pred.classify("   1 @ $1.99 ea    "));
    }

    @Test
    public void isUnitPriceTest18(){
        assertEquals(LineType.UnitPrice, pred.classify("  $1.28 lnt 6, $1.48 ea"));
    }

    @Test
    public void isUnitPriceTest19(){
        assertEquals(LineType.UnitPrice, pred.classify("  2 @ $0.05                              "));
    }

    @Test
    public void isUnitPriceTest20(){
        assertEquals(LineType.WeightPrice, pred.classify("  0.860 kg @$3.26/kg                          "));
    }

    @Test
    public void isUnitPriceTest21(){
        assertEquals(LineType.WeightPrice, pred.classify("  0.830 kg@ $1.72/kg                      "));
    }

    @Test
    public void isUnitPriceTest22(){
        assertEquals(LineType.UnitPrice, pred.classify("  $3.98 ea or 3/$10 .71                  "));
    }

    @Test
    public void isUnitPriceTest23(){
        assertEquals(LineType.UnitPrice, pred.classify("  1 @$3.98 ea              "));
    }

    @Test
    public void isUnitPriceTest24(){
        assertEquals(LineType.UnitPrice, pred.classify("  1 @$3.98 ea              "));
    }

    @Test
    public void isUnitPriceTest25(){
        assertEquals(LineType.UnitPrice, pred.classify("      $1 .99 ea or 2/S3 .58"));
    }

    @Test
    public void isUnitPriceTest26(){
        assertEquals(LineType.UnitPrice, pred.classify("      1 @ $1.99 ea  "));
    }

    @Test
    public void itemTest10(){
        assertEquals(LineType.Item, pred.classify("      05870325083 CORD TRIM JUNGL GMRJ         4.00  "));
    }

    @Test
    public void itemTest11(){
        assertEquals(LineType.Item, pred.classify("   08978200269 GARDEN WAFER                     MRJ      2.56  "));
    }

    @Test
    public void itemTest12(){
        assertEquals(LineType.Item, pred.classify("   693491804007 RICE STICK                      HRJ      1.08  "));
    }

    @Test
    public void itemTest13(){
        assertEquals(LineType.Item, pred.classify("   06340004440    CNTRY HVST BRD                MRJ     2.98 "));
    }

    @Test
    public void itemTest13ItsOkay(){
        assertEquals(LineType.Item, pred.classify("  34-BAKERV COMMERCIAL "));
    }

    @Test
    public void itemTest14(){
        assertEquals(LineType.Item, pred.classify(" (2)9               PLASTIC BAGS              GRO     0.10"));
    }

}
