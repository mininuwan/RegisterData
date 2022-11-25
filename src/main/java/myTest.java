import java.sql.SQLOutput;

public class myTest {
    public static void main(String[] args) {
        String strMsg = "Super5 is3 Star9 Bob2 a4";
        String strArray[] = strMsg.split(" ");
        for (int i = 0; i < strArray.length; i++) {
            //System.out.println(strArray[i]);
        }
        //String strOrderedArray[] = new String[strArray.length];
        String strOrderedArray[] = new String[10];

        for(int j=0; j<strArray.length; j++)
        {
            for (int i=0; i< strArray[j].length(); i++)
            {
                if(Character.isDigit(strArray[j].charAt(i)))
                {
                    char ch = strArray[j].charAt(i);
                    //System.out.println(ch);
                    int idx = Integer.parseInt(String.valueOf(ch));

                    strOrderedArray[idx] = strArray[j];

                }
            }
        }

        String tempArray[] = new String[strArray.length];
        int j=0;

        for (int i = 0; i < strOrderedArray.length; i++) {
            if(strOrderedArray[i] != null)
            {
                tempArray[j] = strOrderedArray[i];
                j++;
            }
        }

        for (int i = 0; i < tempArray.length; i++) {
            tempArray[i] = tempArray[i].replaceAll("[0-9]","");
            System.out.print(tempArray[i] + " ");
        }

    }
}
