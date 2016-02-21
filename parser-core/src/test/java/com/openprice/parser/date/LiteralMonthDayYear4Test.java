package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LiteralMonthDayYear4Test {

    private final LiteralMonthDayYear4 literalMDY4 = new LiteralMonthDayYear4();

    @Test
    public void patternLiterMonthDayYearTest1() throws Exception{
        assertEquals("Jan192015", literalMDY4.parse("Jan192015  sfdgsd "));
//        assertEquals(literalMDY4.parse("Jan 19 2015"));
//        assertEquals(literalMDY4.parse("Jan 19,2015"));
//        assertEquals(literalMDY4.parse("Jan 19, 2015"));
//        assertEquals(literalMDY4.parse("Jan 19,   2015"));
//        assertEquals(literalMDY4.parse("Jan 19.   2015"));
//        assertEquals(literalMDY4.parse("Jan 19_   2015"));
//        assertEquals(literalMDY4.parse("Jan  19,   2015"));
//        assertEquals(literalMDY4.parse("Jan  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Jan 09 2015"));
//        assertEquals(literalMDY4.parse("Jan 09,2015"));
//        assertEquals(literalMDY4.parse("Jan 09, 2015"));
//        assertEquals(literalMDY4.parse("Jan 09,   2015"));
//        assertEquals(literalMDY4.parse("Jan 09.   2015"));
//        assertEquals(literalMDY4.parse("Jan 09_   2015"));
//        assertEquals(literalMDY4.parse("Jan  09,   2015"));
//        assertEquals(literalMDY4.parse("Jan  09 ,   2015"));
//
//        assertEquals(literalMDY4.parse("January 19 2015"));
//        assertEquals(literalMDY4.parse("January 19,2015"));
//        assertEquals(literalMDY4.parse("January 19, 2015"));
//        assertEquals(literalMDY4.parse("January 19,   2015"));
//        assertEquals(literalMDY4.parse("January 19.   2015"));
//        assertEquals(literalMDY4.parse("January 19_   2015"));
//        assertEquals(literalMDY4.parse("January  19,   2015"));
//        assertEquals(literalMDY4.parse("January  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Jan 9 2015"));
//        assertEquals(literalMDY4.parse("Jan 9,2015"));
//        assertEquals(literalMDY4.parse("Jan 9, 2015"));
//        assertEquals(literalMDY4.parse("Jan 9,   2015"));
//        assertEquals(literalMDY4.parse("Jan 9.   2015"));
//        assertEquals(literalMDY4.parse("Jan 9_   2015"));
//        assertEquals(literalMDY4.parse("Jan  9,   2015"));
//        assertEquals(literalMDY4.parse("Jan  9 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Feb 19 2015"));
//        assertEquals(literalMDY4.parse("Feb 19,2015"));
//        assertEquals(literalMDY4.parse("Feb 19, 2015"));
//        assertEquals(literalMDY4.parse("Feb 19,   2015"));
//        assertEquals(literalMDY4.parse("Feb 19.   2015"));
//        assertEquals(literalMDY4.parse("Feb 19_   2015"));
//        assertEquals(literalMDY4.parse("Feb  19,   2015"));
//        assertEquals(literalMDY4.parse("Feb  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Feb 09 2015"));
//        assertEquals(literalMDY4.parse("Feb 09,2015"));
//        assertEquals(literalMDY4.parse("Feb 09, 2015"));
//        assertEquals(literalMDY4.parse("Feb 09,   2015"));
//        assertEquals(literalMDY4.parse("Feb 09.   2015"));
//        assertEquals(literalMDY4.parse("Feb 09_   2015"));
//        assertEquals(literalMDY4.parse("Feb  09,   2015"));
//        assertEquals(literalMDY4.parse("Feb  09 ,   2015"));
//
//        assertEquals(literalMDY4.parse("February 19 2015"));
//        assertEquals(literalMDY4.parse("February 19,2015"));
//        assertEquals(literalMDY4.parse("February 19, 2015"));
//        assertEquals(literalMDY4.parse("February 19,   2015"));
//        assertEquals(literalMDY4.parse("February 19.   2015"));
//        assertEquals(literalMDY4.parse("February 19_   2015"));
//        assertEquals(literalMDY4.parse("February  19,   2015"));
//        assertEquals(literalMDY4.parse("February  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Feb 9 2015"));
//        assertEquals(literalMDY4.parse("Feb 9,2015"));
//        assertEquals(literalMDY4.parse("Feb 9, 2015"));
//        assertEquals(literalMDY4.parse("Feb 9,   2015"));
//        assertEquals(literalMDY4.parse("Feb 9.   2015"));
//        assertEquals(literalMDY4.parse("Feb 9_   2015"));
//        assertEquals(literalMDY4.parse("Feb  9,   2015"));
//        assertEquals(literalMDY4.parse("Feb  9 ,   2015"));
//
//
//        assertEquals(literalMDY4.parse("Mar 19 2015"));
//        assertEquals(literalMDY4.parse("Mar 19,2015"));
//        assertEquals(literalMDY4.parse("Mar 19, 2015"));
//        assertEquals(literalMDY4.parse("Mar 19,   2015"));
//        assertEquals(literalMDY4.parse("Mar 19.   2015"));
//        assertEquals(literalMDY4.parse("Mar 19_   2015"));
//        assertEquals(literalMDY4.parse("Mar  19,   2015"));
//        assertEquals(literalMDY4.parse("Mar  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Mar 09 2015"));
//        assertEquals(literalMDY4.parse("Mar 09,2015"));
//        assertEquals(literalMDY4.parse("Mar 09, 2015"));
//        assertEquals(literalMDY4.parse("Mar 09,   2015"));
//        assertEquals(literalMDY4.parse("Mar 09.   2015"));
//        assertEquals(literalMDY4.parse("Mar 09_   2015"));
//        assertEquals(literalMDY4.parse("Mar  09,   2015"));
//        assertEquals(literalMDY4.parse("Mar  09 ,   2015"));
//
//        assertEquals(literalMDY4.parse("March 19 2015"));
//        assertEquals(literalMDY4.parse("March 19,2015"));
//        assertEquals(literalMDY4.parse("March 19, 2015"));
//        assertEquals(literalMDY4.parse("March 19,   2015"));
//        assertEquals(literalMDY4.parse("March 19.   2015"));
//        assertEquals(literalMDY4.parse("March 19_   2015"));
//        assertEquals(literalMDY4.parse("March  19,   2015"));
//        assertEquals(literalMDY4.parse("March  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Mar 9 2015"));
//        assertEquals(literalMDY4.parse("Mar 9,2015"));
//        assertEquals(literalMDY4.parse("Mar 9, 2015"));
//        assertEquals(literalMDY4.parse("Mar 9,   2015"));
//        assertEquals(literalMDY4.parse("Mar 9.   2015"));
//        assertEquals(literalMDY4.parse("Mar 9_   2015"));
//        assertEquals(literalMDY4.parse("Mar  9,   2015"));
//        assertEquals(literalMDY4.parse("Mar  9 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Apr 19 2015"));
//        assertEquals(literalMDY4.parse("Apr 19,2015"));
//        assertEquals(literalMDY4.parse("Apr 19, 2015"));
//        assertEquals(literalMDY4.parse("Apr 19,   2015"));
//        assertEquals(literalMDY4.parse("Apr 19.   2015"));
//        assertEquals(literalMDY4.parse("Apr 19_   2015"));
//        assertEquals(literalMDY4.parse("Apr  19,   2015"));
//        assertEquals(literalMDY4.parse("Apr  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Apr 09 2015"));
//        assertEquals(literalMDY4.parse("Apr 09,2015"));
//        assertEquals(literalMDY4.parse("Apr 09, 2015"));
//        assertEquals(literalMDY4.parse("Apr 09,   2015"));
//        assertEquals(literalMDY4.parse("Apr 09.   2015"));
//        assertEquals(literalMDY4.parse("Apr 09_   2015"));
//        assertEquals(literalMDY4.parse("Apr  09,   2015"));
//        assertEquals(literalMDY4.parse("Apr  09 ,   2015"));
//
//        assertEquals(literalMDY4.parse("April 19 2015"));
//        assertEquals(literalMDY4.parse("April 19,2015"));
//        assertEquals(literalMDY4.parse("April 19, 2015"));
//        assertEquals(literalMDY4.parse("April 19,   2015"));
//        assertEquals(literalMDY4.parse("April 19.   2015"));
//        assertEquals(literalMDY4.parse("April 19_   2015"));
//        assertEquals(literalMDY4.parse("April  19,   2015"));
//        assertEquals(literalMDY4.parse("April  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Apr 9 2015"));
//        assertEquals(literalMDY4.parse("Apr 9,2015"));
//        assertEquals(literalMDY4.parse("Apr 9, 2015"));
//        assertEquals(literalMDY4.parse("Apr 9,   2015"));
//        assertEquals(literalMDY4.parse("Apr 9.   2015"));
//        assertEquals(literalMDY4.parse("Apr 9_   2015"));
//        assertEquals(literalMDY4.parse("Apr  9,   2015"));
//        assertEquals(literalMDY4.parse("Apr  9 ,   2015"));
//
//        assertEquals(literalMDY4.parse("May 19 2015"));
//        assertEquals(literalMDY4.parse("May 19,2015"));
//        assertEquals(literalMDY4.parse("May 19, 2015"));
//        assertEquals(literalMDY4.parse("May 19,   2015"));
//        assertEquals(literalMDY4.parse("May 19.   2015"));
//        assertEquals(literalMDY4.parse("May 19_   2015"));
//        assertEquals(literalMDY4.parse("May  19,   2015"));
//        assertEquals(literalMDY4.parse("May  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("May 09 2015"));
//        assertEquals(literalMDY4.parse("May 09,2015"));
//        assertEquals(literalMDY4.parse("May 09, 2015"));
//        assertEquals(literalMDY4.parse("May 09,   2015"));
//        assertEquals(literalMDY4.parse("May 09.   2015"));
//        assertEquals(literalMDY4.parse("May 09_   2015"));
//        assertEquals(literalMDY4.parse("May  09,   2015"));
//        assertEquals(literalMDY4.parse("May  09 ,   2015"));
//
//        assertEquals(literalMDY4.parse("May 19 2015"));
//        assertEquals(literalMDY4.parse("May 19,2015"));
//        assertEquals(literalMDY4.parse("May 19, 2015"));
//        assertEquals(literalMDY4.parse("May 19,   2015"));
//        assertEquals(literalMDY4.parse("May 19.   2015"));
//        assertEquals(literalMDY4.parse("May 19_   2015"));
//        assertEquals(literalMDY4.parse("May  19,   2015"));
//        assertEquals(literalMDY4.parse("May  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("May 9 2015"));
//        assertEquals(literalMDY4.parse("May 9,2015"));
//        assertEquals(literalMDY4.parse("May 9, 2015"));
//        assertEquals(literalMDY4.parse("May 9,   2015"));
//        assertEquals(literalMDY4.parse("May 9.   2015"));
//        assertEquals(literalMDY4.parse("May 9_   2015"));
//        assertEquals(literalMDY4.parse("May  9,   2015"));
//        assertEquals(literalMDY4.parse("May  9 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Jun 19 2015"));
//        assertEquals(literalMDY4.parse("Jun 19,2015"));
//        assertEquals(literalMDY4.parse("Jun 19, 2015"));
//        assertEquals(literalMDY4.parse("Jun 19,   2015"));
//        assertEquals(literalMDY4.parse("Jun 19.   2015"));
//        assertEquals(literalMDY4.parse("Jun 19_   2015"));
//        assertEquals(literalMDY4.parse("Jun  19,   2015"));
//        assertEquals(literalMDY4.parse("Jun  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Jun 09 2015"));
//        assertEquals(literalMDY4.parse("Jun 09,2015"));
//        assertEquals(literalMDY4.parse("Jun 09, 2015"));
//        assertEquals(literalMDY4.parse("Jun 09,   2015"));
//        assertEquals(literalMDY4.parse("Jun 09.   2015"));
//        assertEquals(literalMDY4.parse("Jun 09_   2015"));
//        assertEquals(literalMDY4.parse("Jun  09,   2015"));
//        assertEquals(literalMDY4.parse("Jun  09 ,   2015"));
//
//        assertEquals(literalMDY4.parse("June 19 2015"));
//        assertEquals(literalMDY4.parse("June 19,2015"));
//        assertEquals(literalMDY4.parse("June 19, 2015"));
//        assertEquals(literalMDY4.parse("June 19,   2015"));
//        assertEquals(literalMDY4.parse("June 19.   2015"));
//        assertEquals(literalMDY4.parse("June 19_   2015"));
//        assertEquals(literalMDY4.parse("June  19,   2015"));
//        assertEquals(literalMDY4.parse("June  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Jun 9 2015"));
//        assertEquals(literalMDY4.parse("Jun 9,2015"));
//        assertEquals(literalMDY4.parse("Jun 9, 2015"));
//        assertEquals(literalMDY4.parse("Jun 9,   2015"));
//        assertEquals(literalMDY4.parse("Jun 9.   2015"));
//        assertEquals(literalMDY4.parse("Jun 9_   2015"));
//        assertEquals(literalMDY4.parse("Jun  9,   2015"));
//        assertEquals(literalMDY4.parse("Jun  9 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Jul 19 2015"));
//        assertEquals(literalMDY4.parse("Jul 19,2015"));
//        assertEquals(literalMDY4.parse("Jul 19, 2015"));
//        assertEquals(literalMDY4.parse("Jul 19,   2015"));
//        assertEquals(literalMDY4.parse("Jul 19.   2015"));
//        assertEquals(literalMDY4.parse("Jul 19_   2015"));
//        assertEquals(literalMDY4.parse("Jul  19,   2015"));
//        assertEquals(literalMDY4.parse("Jul  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Jul 09 2015"));
//        assertEquals(literalMDY4.parse("Jul 09,2015"));
//        assertEquals(literalMDY4.parse("Jul 09, 2015"));
//        assertEquals(literalMDY4.parse("Jul 09,   2015"));
//        assertEquals(literalMDY4.parse("Jul 09.   2015"));
//        assertEquals(literalMDY4.parse("Jul 09_   2015"));
//        assertEquals(literalMDY4.parse("Jul  09,   2015"));
//        assertEquals(literalMDY4.parse("Jul  09 ,   2015"));
//
//        assertEquals(literalMDY4.parse("July 19 2015"));
//        assertEquals(literalMDY4.parse("July 19,2015"));
//        assertEquals(literalMDY4.parse("July 19, 2015"));
//        assertEquals(literalMDY4.parse("July 19,   2015"));
//        assertEquals(literalMDY4.parse("July 19.   2015"));
//        assertEquals(literalMDY4.parse("July 19_   2015"));
//        assertEquals(literalMDY4.parse("July  19,   2015"));
//        assertEquals(literalMDY4.parse("July  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Jul 9 2015"));
//        assertEquals(literalMDY4.parse("Jul 9,2015"));
//        assertEquals(literalMDY4.parse("Jul 9, 2015"));
//        assertEquals(literalMDY4.parse("Jul 9,   2015"));
//        assertEquals(literalMDY4.parse("Jul 9.   2015"));
//        assertEquals(literalMDY4.parse("Jul 9_   2015"));
//        assertEquals(literalMDY4.parse("Jul  9,   2015"));
//        assertEquals(literalMDY4.parse("Jul  9 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Aug 19 2015"));
//        assertEquals(literalMDY4.parse("Aug 19,2015"));
//        assertEquals(literalMDY4.parse("Aug 19, 2015"));
//        assertEquals(literalMDY4.parse("Aug 19,   2015"));
//        assertEquals(literalMDY4.parse("Aug 19.   2015"));
//        assertEquals(literalMDY4.parse("Aug 19_   2015"));
//        assertEquals(literalMDY4.parse("Aug  19,   2015"));
//        assertEquals(literalMDY4.parse("Aug  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Aug 09 2015"));
//        assertEquals(literalMDY4.parse("Aug 09,2015"));
//        assertEquals(literalMDY4.parse("Aug 09, 2015"));
//        assertEquals(literalMDY4.parse("Aug 09,   2015"));
//        assertEquals(literalMDY4.parse("Aug 09.   2015"));
//        assertEquals(literalMDY4.parse("Aug 09_   2015"));
//        assertEquals(literalMDY4.parse("Aug  09,   2015"));
//        assertEquals(literalMDY4.parse("Aug  09 ,   2015"));
//
//        assertEquals(literalMDY4.parse("August 19 2015"));
//        assertEquals(literalMDY4.parse("August 19,2015"));
//        assertEquals(literalMDY4.parse("August 19, 2015"));
//        assertEquals(literalMDY4.parse("August 19,   2015"));
//        assertEquals(literalMDY4.parse("August 19.   2015"));
//        assertEquals(literalMDY4.parse("August 19_   2015"));
//        assertEquals(literalMDY4.parse("August  19,   2015"));
//        assertEquals(literalMDY4.parse("August  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Aug 9 2015"));
//        assertEquals(literalMDY4.parse("Aug 9,2015"));
//        assertEquals(literalMDY4.parse("Aug 9, 2015"));
//        assertEquals(literalMDY4.parse("Aug 9,   2015"));
//        assertEquals(literalMDY4.parse("Aug 9.   2015"));
//        assertEquals(literalMDY4.parse("Aug 9_   2015"));
//        assertEquals(literalMDY4.parse("Aug  9,   2015"));
//        assertEquals(literalMDY4.parse("Aug  9 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Sep 19 2015"));
//        assertEquals(literalMDY4.parse("Sep 19,2015"));
//        assertEquals(literalMDY4.parse("Sep 19, 2015"));
//        assertEquals(literalMDY4.parse("Sep 19,   2015"));
//        assertEquals(literalMDY4.parse("Sep 19.   2015"));
//        assertEquals(literalMDY4.parse("Sep 19_   2015"));
//        assertEquals(literalMDY4.parse("Sep  19,   2015"));
//        assertEquals(literalMDY4.parse("Sep  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Sep 09 2015"));
//        assertEquals(literalMDY4.parse("Sep 09,2015"));
//        assertEquals(literalMDY4.parse("Sep 09, 2015"));
//        assertEquals(literalMDY4.parse("Sep 09,   2015"));
//        assertEquals(literalMDY4.parse("Sep 09.   2015"));
//        assertEquals(literalMDY4.parse("Sep 09_   2015"));
//        assertEquals(literalMDY4.parse("Sep  09,   2015"));
//        assertEquals(literalMDY4.parse("Sep  09 ,   2015"));
//
//        assertEquals(literalMDY4.parse("September 19 2015"));
//        assertEquals(literalMDY4.parse("September 19,2015"));
//        assertEquals(literalMDY4.parse("September 19, 2015"));
//        assertEquals(literalMDY4.parse("September 19,   2015"));
//        assertEquals(literalMDY4.parse("September 19.   2015"));
//        assertEquals(literalMDY4.parse("September 19_   2015"));
//        assertEquals(literalMDY4.parse("September  19,   2015"));
//        assertEquals(literalMDY4.parse("September  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Sep 9 2015"));
//        assertEquals(literalMDY4.parse("Sep 9,2015"));
//        assertEquals(literalMDY4.parse("Sep 9, 2015"));
//        assertEquals(literalMDY4.parse("Sep 9,   2015"));
//        assertEquals(literalMDY4.parse("Sep 9.   2015"));
//        assertEquals(literalMDY4.parse("Sep 9_   2015"));
//        assertEquals(literalMDY4.parse("Sep  9,   2015"));
//        assertEquals(literalMDY4.parse("Sep  9 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Oct 19 2015"));
//        assertEquals(literalMDY4.parse("Oct 19,2015"));
//        assertEquals(literalMDY4.parse("Oct 19, 2015"));
//        assertEquals(literalMDY4.parse("Oct 19,   2015"));
//        assertEquals(literalMDY4.parse("Oct 19.   2015"));
//        assertEquals(literalMDY4.parse("Oct 19_   2015"));
//        assertEquals(literalMDY4.parse("Oct  19,   2015"));
//        assertEquals(literalMDY4.parse("Oct  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Oct 09 2015"));
//        assertEquals(literalMDY4.parse("Oct 09,2015"));
//        assertEquals(literalMDY4.parse("Oct 09, 2015"));
//        assertEquals(literalMDY4.parse("Oct 09,   2015"));
//        assertEquals(literalMDY4.parse("Oct 09.   2015"));
//        assertEquals(literalMDY4.parse("Oct 09_   2015"));
//        assertEquals(literalMDY4.parse("Oct  09,   2015"));
//        assertEquals(literalMDY4.parse("Oct  09 ,   2015"));
//
//        assertEquals(literalMDY4.parse("October 19 2015"));
//        assertEquals(literalMDY4.parse("October 19,2015"));
//        assertEquals(literalMDY4.parse("October 19, 2015"));
//        assertEquals(literalMDY4.parse("October 19,   2015"));
//        assertEquals(literalMDY4.parse("October 19.   2015"));
//        assertEquals(literalMDY4.parse("October 19_   2015"));
//        assertEquals(literalMDY4.parse("October  19,   2015"));
//        assertEquals(literalMDY4.parse("October  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Oct 9 2015"));
//        assertEquals(literalMDY4.parse("Oct 9,2015"));
//        assertEquals(literalMDY4.parse("Oct 9, 2015"));
//        assertEquals(literalMDY4.parse("Oct 9,   2015"));
//        assertEquals(literalMDY4.parse("Oct 9.   2015"));
//        assertEquals(literalMDY4.parse("Oct 9_   2015"));
//        assertEquals(literalMDY4.parse("Oct  9,   2015"));
//        assertEquals(literalMDY4.parse("Oct  9 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Nov 19 2015"));
//        assertEquals(literalMDY4.parse("Nov 19,2015"));
//        assertEquals(literalMDY4.parse("Nov 19, 2015"));
//        assertEquals(literalMDY4.parse("Nov 19,   2015"));
//        assertEquals(literalMDY4.parse("Nov 19.   2015"));
//        assertEquals(literalMDY4.parse("Nov 19_   2015"));
//        assertEquals(literalMDY4.parse("Nov  19,   2015"));
//        assertEquals(literalMDY4.parse("Nov  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Nov 09 2015"));
//        assertEquals(literalMDY4.parse("Nov 09,2015"));
//        assertEquals(literalMDY4.parse("Nov 09, 2015"));
//        assertEquals(literalMDY4.parse("Nov 09,   2015"));
//        assertEquals(literalMDY4.parse("Nov 09.   2015"));
//        assertEquals(literalMDY4.parse("Nov 09_   2015"));
//        assertEquals(literalMDY4.parse("Nov  09,   2015"));
//        assertEquals(literalMDY4.parse("Nov  09 ,   2015"));
//
//        assertEquals(literalMDY4.parse("November 19 2015"));
//        assertEquals(literalMDY4.parse("November 19,2015"));
//        assertEquals(literalMDY4.parse("November 19, 2015"));
//        assertEquals(literalMDY4.parse("November 19,   2015"));
//        assertEquals(literalMDY4.parse("November 19.   2015"));
//        assertEquals(literalMDY4.parse("November 19_   2015"));
//        assertEquals(literalMDY4.parse("November  19,   2015"));
//        assertEquals(literalMDY4.parse("November  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Nov 9 2015"));
//        assertEquals(literalMDY4.parse("Nov 9,2015"));
//        assertEquals(literalMDY4.parse("Nov 9, 2015"));
//        assertEquals(literalMDY4.parse("Nov 9,   2015"));
//        assertEquals(literalMDY4.parse("Nov 9.   2015"));
//        assertEquals(literalMDY4.parse("Nov 9_   2015"));
//        assertEquals(literalMDY4.parse("Nov  9,   2015"));
//        assertEquals(literalMDY4.parse("Nov  9 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Dec 19 2015"));
//        assertEquals(literalMDY4.parse("Dec 19,2015"));
//        assertEquals(literalMDY4.parse("Dec 19, 2015"));
//        assertEquals(literalMDY4.parse("Dec 19,   2015"));
//        assertEquals(literalMDY4.parse("Dec 19.   2015"));
//        assertEquals(literalMDY4.parse("Dec 19_   2015"));
//        assertEquals(literalMDY4.parse("Dec  19,   2015"));
//        assertEquals(literalMDY4.parse("Dec  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Dec 09 2015"));
//        assertEquals(literalMDY4.parse("Dec 09,2015"));
//        assertEquals(literalMDY4.parse("Dec 09, 2015"));
//        assertEquals(literalMDY4.parse("Dec 09,   2015"));
//        assertEquals(literalMDY4.parse("Dec 09.   2015"));
//        assertEquals(literalMDY4.parse("Dec 09_   2015"));
//        assertEquals(literalMDY4.parse("Dec  09,   2015"));
//        assertEquals(literalMDY4.parse("Dec  09 ,   2015"));
//
//        assertEquals(literalMDY4.parse("December 19 2015"));
//        assertEquals(literalMDY4.parse("December 19,2015"));
//        assertEquals(literalMDY4.parse("December 19, 2015"));
//        assertEquals(literalMDY4.parse("December 19,   2015"));
//        assertEquals(literalMDY4.parse("December 19.   2015"));
//        assertEquals(literalMDY4.parse("December 19_   2015"));
//        assertEquals(literalMDY4.parse("December  19,   2015"));
//        assertEquals(literalMDY4.parse("December  19 ,   2015"));
//
//        assertEquals(literalMDY4.parse("Dec 9 2015"));
//        assertEquals(literalMDY4.parse("Dec 9,2015"));
//        assertEquals(literalMDY4.parse("Dec 9, 2015"));
//        assertEquals(literalMDY4.parse("Dec 9,   2015"));
//        assertEquals(literalMDY4.parse("Dec 9.   2015"));
//        assertEquals(literalMDY4.parse("Dec 9_   2015"));
//        assertEquals(literalMDY4.parse("Dec  9,   2015"));
//        assertEquals(literalMDY4.parse("Dec  9 ,   2015"));
    }
}
