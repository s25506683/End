import java.util.Scanner;

public class Q1 {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		boolean abc =true;
		while (abc) {
			String originalStr = input.nextLine().replaceAll(" ", "");
			String Xreverse = reverse(originalStr);
			if (originalStr.equals("0")) {
				abc = false;
				System.exit(0);
			} else {
				if (SUM(Xreverse) < 1000) {
					System.out.printf("%04d\n", SUM(Xreverse));
				} else {
					System.out.println(SUM(Xreverse) % 10000);
				}
			}
			
		}
		input.close();
	}

	public static String reverse(String original) {
		String resultStr = "";
		for (int i = original.length() - 1; i >= 0; i--) {

			resultStr = resultStr + original.charAt(i);
		}
		return resultStr;
	}

	public static int SUM(String str) {
		if (str == null) {
			return 0;
		}
		char[] charArr = str.toCharArray();
		int sum = 0;
		int num = 0;
		int cur = 0;

		for (int i = 0; i < charArr.length; i++) {
			cur = charArr[i] - '0';
			if (cur < 0 || cur > 9) {
				sum += num;
				num = 0;
			} else {
				num = num * 10 + cur;
			}
		}
		sum += num;
		return sum;
	}
}