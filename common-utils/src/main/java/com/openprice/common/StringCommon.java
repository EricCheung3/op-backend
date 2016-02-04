package com.openprice.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

/**
 * common operations for strings
 */
@Slf4j
public class StringCommon {

    public static final String EMPTY = "";
    public static final String WIDE_SPACES="    ";


    public static List<String> sortByStringLength(final List<String> orig){
        return orig
                .stream()
                .sorted((n1, n2)->n2.length() - n1.length())
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     *
     * @param line
     * @param slash the position of slash '/'
     * @return at most two digits that are before the slash position
     */
    public static String twoDigitsBeforeSlash(final String line,final int slash)
            throws Exception{
        if(line.charAt(slash)!='/'){
            throw new Exception("line "+line +" does not have the slash sign at position "+slash);
        }
        StringBuilder twoDigits=new StringBuilder();
        int count=0;
        for(int i=slash; i>=0 && count<2;i--){
            if(Character.isDigit(line.charAt(i))){
                twoDigits.append(line.charAt(i)+"");
                count++;
            }
        }
        return twoDigits.reverse().toString();
    }

    public static String getOnlyNumbersAndLetters(String str) {
        return str.replaceAll("[^A-Za-z0-9]", EMPTY);
    }

    public static int countChars(final String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++)
            if (Character.isAlphabetic(str.charAt(i)))
                count++;
        return count;
    }

    // -------------
    // Untested

    /**
     * Note it is assumed that line is always longer than subStr
     *
     * @param line
     * @param subStr
     *            an address field value like "4821" which is line1 value in
     *            address;
     * @return
     */
    public static double matchStringToSubString(String line, String subStr) {
        line = removeAllSpaces(line).toLowerCase();
        subStr = removeAllSpaces(subStr).toLowerCase();
        if (line.isEmpty() || subStr.isEmpty() || line.length() < subStr.length()) {
            return 0;
        }

        double scoreMax = 0;
        for (int i = 0; i <= line.length() - subStr.length(); i++) {
            String slice = line.substring(i, i + subStr.length());
            double score = Levenshtein.compare(slice, subStr);
            if (score > scoreMax) {
                scoreMax = score;
            }
        }
        return scoreMax;
    }

    //two-way. symmetric
    public static double matchStringToSubStringTwoWay(final String str1, final String str2){
        return Math.max(matchStringToSubString(str1, str2),
                        matchStringToSubString(str2, str1));
    }

    // penalized if both of line and subStr contains lots of numbers
    public static double bestSliceMatchingWithNumberPenalized(String line, String subStr) {
        return matchStringToSubString(line, subStr) * numberRatio(line, subStr);
    }

    // only
    public static double numberRatio(String line, String subStr) {
        line = line.replaceAll("\\s+", "");
        subStr = subStr.replaceAll("\\s+", "");
        final int[] countSubStrt = StringCommon.countDigitAndChars(subStr);
        if (countSubStrt[0] != subStr.length())
            return 1.0;

        final int[] countLine = StringCommon.countDigitAndChars(line);
        if (countLine[0] == 0)
            return 1.0;
        final double ratio = (countSubStrt[0] + 0.0) / countLine[0];
        if (ratio > 1)
            return 1.0;
        return ratio;
    }

    /**
     * flatten the string list into a single string
     *
     * @param list
     *            given String list
     * @return the concatenation of all the String in the list with whitespace
     *         added between.
     */
    public static String flatten(final List<String> list, final String separator) {
        if (list.isEmpty()) {
            return "";
        }
        String result = list.get(0);
        for (int i = 1; i < list.size(); i++)
            result += separator + list.get(i);
        return result;
    }

    // to do: using nio
    public static String formatPath(String path, String fileName) {
        // logger.debug("path is "+path);
        if (!path.startsWith("/"))
            path = "/" + path;
        if (!path.endsWith("/") && !fileName.startsWith("/"))
            path += "/";
        if (!fileName.endsWith("/"))
            fileName += "/";
        // logger.debug("path is "+path);
        return path + fileName;
    }

    public static String formatPath(String path) {
        // logger.debug("path is "+path);
        if (!path.startsWith("/"))
            path = "/" + path;
        if (!path.endsWith("/"))
            path = path + "/";
        // logger.debug("path is "+path);
        return path;
    }

    public static int[] countDigitAndLetters(final String str){
        int[] count=new int[2];
        for(int i=0; i<str.length();i++)
            if(Character.isDigit(str.charAt(i)))
                count[0]++;
            else if(Character.isLetter(str.charAt(i)))
                count[1]++;
        return count;
    }


    public static int[] countDigitAndAlphabets(final String str) {
        int[] count = new int[2];
        for (int i = 0; i < str.length(); i++)
            if (Character.isDigit(str.charAt(i)))
                count[0]++;
            else if (Character.isLetter(str.charAt(i)))
                count[1]++;
        return count;
    }

    // contains "one and only one" alphabet or not
    public static boolean containsOneOnlyOneLetter(final String str) {
        Set<Character> charSet = new HashSet<Character>();
        final String noSpace = str.replaceAll("\\s+", "");
        for (int i = 0; i < noSpace.length(); i++) {
            if (Character.isLetter(noSpace.charAt(i)))
                charSet.add(noSpace.charAt(i));
        }
        return charSet.size() == 1;
    }

    public static int[] countDigitAndChars(final String str) {
        int[] count = new int[2];
        for (int i = 0; i < str.length(); i++)
            if (Character.isDigit(str.charAt(i)))
                count[0]++;
            else if (Character.isAlphabetic(str.charAt(i)))
                count[1]++;
        return count;
    }

    // todo:"+" is used to escape quantity price. So should refer to
    // quantityPriceParser
    public static String formatName(final String name) {
        // replace comma in names because comma is the field separater in output
        // files
        // ";" was used as the splitter for the name candidate file.
        return name.replaceAll("\\?", "2").replaceAll("\\+", "").replaceAll(",", "").replaceAll(";", "");

    }

    public static String firstTwoChars(final String str) {
        String res = "";
        for (int i = 0; i < str.length() && res.length() < 2; i++) {
            // if(Character.isDigit(str.charAt(i)) ||
            // Character.isAlphabetic(str.charAt(i))) //Android
            // NoSuchMethodException
            if (Character.isDigit(str.charAt(i)) || Character.isLetter(str.charAt(i)))
                res += str.charAt(i) + "";
        }
        return res;
    }

    // "12:1 1" will be changed to "12:11"
    public static String fixMinuteSecond(String afterYear) throws Exception {
        int colon = afterYear.indexOf(":");
        // logger.debug("colon="+colon);
        String result = "";
        if (colon > 0) {
            // get two digits after colon
            result = afterYear.substring(0, colon + 1) + firstTwoChars(afterYear.substring(colon + 1));
            /*
             * int space=afterYear.indexOf(":", colon); //
             * logger.debug("space="+space); if(space==2){ // logger.debug(
             * "space is found"); return
             * afterYear.substring(0,colon+3)+afterYear.charAt(colon+3); }
             */
        } else {
            String first = firstDigits(afterYear, 4);
            result = first.substring(0, 2) + ":" + first.substring(2);

        }
        return result;
    }

    // number of spaces in the head of the string
    public static int headSpaces(final String str) {
        int n = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ')
                n++;
            else
                break;
        }
        return n;
    }

    // remove the trailing charaters at the end of price if any. In addition,
    // remove any chars after digits.
    public static String removeTrailingChars(String price, String tail) {
        price = price.trim();
        String result = price;
        for (int i = price.length() - 1; i >= 0; i--) {
            char c = price.charAt(i);
            if (Character.isDigit(c))
                break;
            if (tail.contains(c + "")) {
                result = result.substring(0, result.length() - 1);
            }
        }
        // logger.debug("removeTrailingChars:"+result);
        if (!isNumeric(price)) {
            result = getOnlyDigitsDots(result);
            // result=getOnlyLettersDigits(result);
        }
        // logger.debug("removeTrailingChars:"+result);
        /*
         * wrong code. a very good exam. for(int i=0; i<tail.length();i++){ char
         * c=tail.charAt(i); if(result.endsWith(c+"")){
         * logger.debug("before:"+result);
         * result=result.substring(0,result.length()-1);
         * logger.debug("after:"+result); } }
         */
        return result.trim();
    }

    // get digits in Tail
    public static String getOnlyDigits(final String str) {
        return digitsBetween(str, 0, str.length(), str.length());
    }

    public static String getOnlyDigitsDots(final String str) {
        return digitsDotsBetween(str, 0, str.length(), str.length());
    }

    // get the string after the last dollar sign
    public static String[] afterLastDollar(final String line) throws Exception {
        int dollar = line.lastIndexOf("$");
        if (dollar < 0)
            throw new Exception("No dollar found in " + line);
        return new String[] { line.substring(dollar + 1), line.substring(dollar + 1) };
    }

    /*
     * new; find the last letter char from position "until" backward
     */
    public static int lastLetter2(final String str, final int until) {
        int last = -1;
        for (int i = until; i >= 0; i--) {
            if (Character.isLetter(str.charAt(i))) {
                last = i;
                break;
            }
        }
        return last;
    }

    public static int lastLetter(final String str) {
        return lastLetter2(str, str.length() - 1);
    }

    // last num digits from str
    public static String lastDigits(final String str, final int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            if (Character.isDigit(str.charAt(i))) {
                sb.append(str.charAt(i));
                if (sb.length() == num)
                    break;
            }
        }
        return sb.reverse().toString();
    }

    public static String firstDigits(final String str, int num) {
        return digitsBetween(str, 0, str.length(), num);
    }

    /*
     * get the digits in the string in string format from begin to end
     *
     * @param num the number of digits to get
     *
     * @param str the string input
     *
     * @param begin the begin index of str
     *
     * @param end the end index of str (excluding)
     */
    public static String digitsBetween(final String str, final int begin, final int end, final int num) {
        if (num <= 0)
            return "";
        StringBuilder sb = new StringBuilder();
        for (int i = Math.max(0, begin); i < Math.min(str.length(), end); i++) {
            if (Character.isDigit(str.charAt(i))) {
                sb.append(str.charAt(i));
                if (sb.length() == num)
                    break;
            }
        }
        return sb.toString();
    }

    // updated to keep spaces
    public static String digitsDotsBetween(final String str, final int begin, final int end, final int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = Math.max(0, begin); i < Math.min(str.length(), end); i++) {
            if (Character.isDigit(str.charAt(i)) || str.charAt(i) == '.' || str.charAt(i) == ' ') {
                sb.append(str.charAt(i));
                if (sb.length() == num)
                    break;
            }
        }
        return sb.toString();
    }

    // str "approximately" contains the substring subStr or not
    public static boolean stringMatchesSubString(String str, String subStr, final double threshold) throws Exception {
        if (threshold == 0.0)
            return true;
        if (str == null)
            str = "";
        if (subStr == null)
            subStr = "";

        // sometimes ocr can give lower case results
        subStr = subStr.toLowerCase();
        str = str.toLowerCase();

        subStr = subStr.replaceAll("\\s+", "");
        String str2 = StringCommon.firstNonSpaceNonDigitChars(str, subStr.length());
        // String str2=StringCommon.firstCharsSameSet(str, subStrNoSpace);
        double score = Levenshtein.compare(str2, subStr);
        // logger.debug("!!!!!score="+score+", string1="+str2+",
        // string2="+subStr);
        return score >= threshold;
    }

    /**
     * get the match score of a line string and a header string
     *
     * @param str
     *            non-null non-empty line string
     * @param head
     *            non-null head string
     * @return [description]
     */
    public static double matchStringToHeader(String str, String head) {
        assert str != null;
        if (str.isEmpty() && head.isEmpty())
            return 1.0;
        if (str.isEmpty() && !head.isEmpty())
            return 0;
        assert head != null;
        if (head.isEmpty())
            return 0;

        str = str.replaceAll("\\s+", "");
        str = str.toLowerCase();

        head = head.replaceAll("\\s+", "");
        head = head.toLowerCase();

        String strHead = StringCommon.firstCharsSameSet(str, head);
        double score = Levenshtein.compare(head, strHead);
        //log.debug("!!!!!score="+score+", str="+strHead+", head="+head);
        return score;
    }

    // the header of String str matches head
    public static boolean stringMatchesHead(String str, String head, final double threshold) {
        if (threshold == 0.0)
            return true;
        return matchStringToHeader(str, head) >= threshold;
    }

    public static String stringMatchesHead(final String str, final List<String> head, final double threshold)
            throws Exception {
        for (int i = 0; i < head.size(); i++) {
            // logger.debug(i+", head ="+head.get(i)+", string ="+str);
            if (StringCommon.stringMatchesHead(str, head.get(i), threshold))
                return head.get(i);
        }
        return "";
    }

    public static String stringMatchesSubString(final String str, final List<String> subStr, final double threshold)
            throws Exception {
        for (int i = 0; i < subStr.size(); i++) {
            if (StringCommon.stringMatchesSubString(str, subStr.get(i), threshold))
                return subStr.get(i);
        }
        return "";
    }

    /*
     * //string matches the head from position start public boolean
     * stringMatchesHead(final String str, final String head, final int start){
     *
     *
     * if(str.length()<head.length()+start) return false; //return
     * Levenshtein.compare(str.substring(start, start+head.length()),
     * head)>TessSimilarity;
     *
     * }
     */

    // get n first nonSpace Chars
    public static String firstNonSpaceChars(String strNoSpace, final int n) {
        strNoSpace = strNoSpace.replaceAll("\\s+", "");
        return strNoSpace.substring(0, Math.min(n, strNoSpace.length()));
    }

    // get n last nonSpace and non-digit Chars
    public static String lastNonSpaceChars(String strNoSpace, final int n) {
        strNoSpace = strNoSpace.replaceAll("\\s+", "");
        log.debug("strNoSpace=" + strNoSpace);
        int len = Math.min(n, strNoSpace.length());
        return strNoSpace.substring(strNoSpace.length() - len, strNoSpace.length());
    }

    // get n first nonSpace and non-digit Chars
    public static String firstNonSpaceNonDigitChars(String strNoSpace, final int n) {
        strNoSpace = strNoSpace.replaceAll("\\s+", "");
        strNoSpace = strNoSpace.replaceAll("[0-9]", "");
        return strNoSpace.substring(0, Math.min(n, strNoSpace.length()));
    }

    // get n first Chars that are in the string (set) or is a letter or a digit
    public static String firstCharsSameSet(String str, final String charSet) {
        StringBuilder sb = new StringBuilder();
        int sum = 0;
        for (int i = 0; sum < charSet.length() && i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isLetter(c) || Character.isDigit(c) || charSet.contains((c + ""))) {
                sb.append(str.charAt(i));
                sum++;
            }
        }
        return sb.toString();
    }

    /*
     * given string str, remove the first Non-space characters from string
     * strNoSpace for str="A BC D E", strNoSpace="ABC", should retain "D E".
     */
    public static String removeFirstNonSpaceChars(final String str, final String strNoSpace) {
        String s1 = str.replaceAll("\\s+", "");
        String s2 = strNoSpace.replaceAll("\\s+", "");
        /*
         * //string 1 JEPOSIT0.25 should contain string 2 DEPOSIT
         * if(!s1.contains(s2)){ throw new Exception("string 1 "+s1+
         * " should contain string 2 "+s2); }
         */
        int p1 = 0, p2 = 0;
        while (p2 < s2.length()) {
            if (p1 >= str.length())
                break;
            if (str.charAt(p1) == ' ')
                p1++;
            else {
                // if(s2.charAt(p2)==str.charAt(p1)){
                p1++;
                p2++;
                // }
            }
        }
        /*
         * String head=str.substring(0,p1).replaceAll("\\s+",""); assert
         * head.equals(s2);
         */
        // logger.debug("p1="+p1+",p2="+p2+",
        // str.substring(p1)="+str.substring(p1));
        return str.substring(p1);
    }

    // get only digit and chars and preserver spaces
    public static String getDigitAndLetter(final String str) {
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < sb.length(); i++) {
            if (Character.isDigit(sb.charAt(i)) || Character.isLetter(sb.charAt(i)) || sb.charAt(i) == ' ') {
                continue;
            } else {
                sb.deleteCharAt(i);
            }
        }
        return sb.toString();
    }

    // format phone into like 780-907-8237
    public static String formatPhone(String phone) throws Exception {
        if (phone.contains("-")) {
            // remove spaces in 780 - 328-8237
            phone = phone.replaceAll("\\s+", "");
        }
        String regex = "[0-9]+";
        if (!phone.replaceAll("-", "").matches(regex)) {
            throw new Exception("Make sure phone number is all numeric.");
        }
        return phone;
    }

    // format price into close-to numeric format
    public static String formatPrice(String priceStr){
        // logger.debug("priceStr="+priceStr);
        priceStr=priceStr.trim();
        if(priceStr.isEmpty()) return priceStr;

        //only keep the last word if there are too many spaces
        String[] words=priceStr.split("\\s{5,}");
        priceStr=words[words.length-1];

        //change "'1 . 73" to "4.73"
        int tmp=priceStr.indexOf("'1");
        if(tmp>=0){
            priceStr=priceStr.substring(0, tmp)+"4"+priceStr.substring(tmp+2);
            priceStr=priceStr.trim();
        }

        //remove the beginning dots in like ". 1.00" but not ".76", and "..76" will be changed to ".76"
        int numHeadDots=0; //number of continuous dots in the beginning
        for(int i=0; i<priceStr.length(); i++){
            if(priceStr.charAt(i)=='.'){
                numHeadDots++;
            }else{
                break;
            }
        }
        if(numHeadDots>0){
            int firstDot=priceStr.indexOf(".");
            //      logger.debug("firstDot="+firstDot);
            int lastDot=priceStr.lastIndexOf(".");
            //       logger.debug("lastDot="+lastDot);
            if(lastDot!=numHeadDots-1)//last dot is not in the heading continuous dots, then remove all begining
                priceStr=priceStr.substring(numHeadDots).trim();
            else{
                priceStr=priceStr.substring(numHeadDots-1).trim();//keep the last one
            }
        }
        // logger.debug("str1:"+priceStr);

        //if price is like "38 99" "3 99", then likely it is "38.99" "3.99"
        priceStr=priceStr.replaceAll("['%]", ".");
        //if(priceStr.length()==4 || priceStr.length()==5){
        if(!priceStr.contains(".")&& priceStr.contains(" "))
        {
            priceStr=priceStr.replaceAll("\\s+", ".");
        }
        //}
        // logger.debug("str2:"+priceStr);

        //remove spaces
        priceStr=priceStr.replaceAll("\\s+", "");
        //replace 'O' 'o' with 0
        priceStr=priceStr.replaceAll("[Oo]", "0");
        //fix OCR mistaking . for '
        //change g to "9"
        priceStr=priceStr.replaceAll("[g]", "9");
        //replace all '?' with 2.
        priceStr=priceStr.replaceAll("\\?", "2");

        //handling with "-" and "~"
        //"-" in the end is moved to the beginning: negative price
        priceStr=priceStr.replaceAll("~", "-");
        int lastOfCross=priceStr.indexOf("-");
        if(lastOfCross==priceStr.length()-1){
            priceStr=priceStr.substring(0, priceStr.length()-1);
            priceStr="-"+priceStr;
        }
        //"-" (in the middle) is changed to "." if there is no dot
        if(!priceStr.contains(".")&& priceStr.contains("-")){
            priceStr=priceStr.replaceAll("[~-]", ".");
        }else{
            if(priceStr.indexOf(".")==0){
                if(priceStr.indexOf(".")!=priceStr.lastIndexOf(".")){
                    priceStr=priceStr.substring(1);
                    priceStr=priceStr.replaceAll("[~-]", ".");
                }else{
                    //logger.debug("if0");
                    if(priceStr.contains("-")|| priceStr.contains("~")){
                        // logger.debug("if1");
                        priceStr=priceStr.substring(1);
                        priceStr=priceStr.replaceAll("[~-]", ".");
                    } else
                        priceStr="0"+priceStr.trim();
                }
            }
        }
        // logger.debug("str3:"+priceStr);
        // System.exit(1);

        priceStr=priceStr.replaceAll("[_]", ".");

        // logger.debug("str4:"+priceStr);


        final String onlyDigit=priceStr.replaceAll("[^0-9._]", StringCommon.EMPTY);
        if(isNumeric(onlyDigit))
            return onlyDigit;

        //get only alphabetic chars and dot
        priceStr=priceStr.replaceAll("[^A-Za-z0-9.-]", "");

        if(isNumeric(priceStr))
            return priceStr;

        return ignoreAfterDot(priceStr);
    }

    /**
     * return
     * 7.0 for 7.llJ
     * 15.98 for 15.98   G
     */

    public static String ignoreAfterDot(final String priceStr){
        int dot=priceStr.indexOf(".");
        if(dot<0)
            return priceStr;
        return priceStr.substring(0, dot+1)+"0";
    }

    public static boolean isNumeric(String s){
        boolean ret = true;
        try {
            Double.parseDouble(s);
        }catch (NumberFormatException e) {
            ret = false;
        }
        return ret;
    }

    // the number of occurences of a char in a string
    public static int countChar(String str, char c) {
        int n = 0;
        char[] strArray = str.toCharArray();
        for (char i : strArray)
            if (i == c)
                n++;
        return n;
    }

    // string contains digit or not
    public static boolean containsDigits(String str) {
        char[] strArray = str.toCharArray();
        for (char c : strArray)
            if (Character.isDigit(c))
                return true;
        return false;
    }

    public static double similarityExact(String s1, String s2) {
        s1 = s1.trim();
        s2 = s2.trim();
        log.debug("@Similairty of " + s1 + " vs " + s2);
        if (s1.equalsIgnoreCase(s2)) {
            return 1.0;
        }
        return 0.0;
    }

    public static double similarityRatioOfMatch(String s1, String s2) {
        List<Character> list1 = getLetters(s1);
        List<Character> list2 = getLetters(s2);
        int m = Math.min(list1.size(), list2.size());
        if (m == 0)
            return 0.0;
        int match = 0;
        for (int i = 0; i < m; i++) {
            if (list1.get(i) == list2.get(i))
                match++;
        }
        return match / (double) m;
    }

    public static String getOnlyUpperCase(String s) {
        String letters = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isUpperCase(c))
                letters += c;
        }
        return letters;
    }

    public static String getOnlyLetters(String s) {
        return getOnlyLettersAnd(s, "");
    }

    // get only letters and some extra symbols in symbol
    public static String getOnlyLettersAnd(String s, String symbol) {
        String letters = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetter(c) || symbol.contains(c + ""))
                letters += c;
        }
        return letters;
    }

    // get only non-digits chars
    public static String filterDigits(final String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i)))
                continue;
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }

    public static List<Character> getLetters(String s) {
        List<Character> list = new ArrayList<Character>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetter(c) || c == '*' || Character.isDigit(c))
                list.add(c);
        }
        return list;
    }

    public static String getOnlyLettersDigits(String s) {
        return getOnlyLettersDigitsAnd(s, "");
    }

    public static String getOnlyLettersDigitsAnd(String s, String symbol) {
        String letters = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetter(c) || Character.isDigit(c) || symbol.contains(c + ""))
                letters += c;
        }
        return letters;
    }

    public static boolean similar(String s1, String s2) {
        double score = similarityRatioOfMatch(s1, s2);
        // logger.debug(s1+":"+s2+", score is "+score);
        return score > 0.9;
    }

    public static String removeAllSpaces(final String str){
        return str.replaceAll("\\s+", StringCommon.EMPTY);
    }

    public static String lowerCaseNoSpaces(final String str){
        return removeAllSpaces(str.toLowerCase());
    }

}

