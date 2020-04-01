package addition;

public class Logic {

    public Model process(String parameter1, String parameter2) {

        int num1, num2;
        String answer;
        try {
            num1 = Integer.parseInt(parameter1);
            num2 = Integer.parseInt(parameter2);
            answer = num1 + " + " + num2 + " = " + (num1 + num2);
        } catch (NumberFormatException e) {
            answer = "Invalid parameters";
        }
        return new Model(answer);
    }

}
