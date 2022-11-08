import java.util.*;


public class Main {
    public static ArrayList<Integer> odds = new ArrayList<>(Arrays.asList(10000, 7500, 5000, 2500, 1000,
            500, 300, 200, 150, 100, 90, 80, 70, 60, 50, 40, 30, 25, 20, 15, 10, 9, 8, 7, 6, 5, 4,
            3, 2, 1));

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int balanta = 0;
        boolean flag = true;


        System.out.println("Welcome to lucky six!");
        while (flag) {
            System.out.print("Introduceti suma pe care doriti sa o depuneti: \r");
            balanta += scanner.nextInt();
            while (balanta > 0) {
                System.out.println("Balanta " + balanta + " lei");
                ArrayList<Integer> winningNumbers = drawWinningNumbers();

                System.out.print("Nr Aleatorii dorite: \r");
                int numarAleatorii = scanner.nextInt();

                System.out.print("Introduceti miza: \r");
                int miza = scanner.nextInt();
                if (balanta < miza * numarAleatorii) {
                    System.out.println("Ne pare rau, nu aveti suficienti bani in balanta!");
                    System.out.println("Apasati 1 pentru a depune, sau orice altceva pentru a renunta - \r");
                    String input = scanner.next();
                    if ((input.equals("1"))) {
                        break;
                    } else {
                        flag = false;
                        break;
                    }


                }

                Random random = new Random();
                ArrayList<String> stelute = new ArrayList<>();
                int trifoi1 = random.nextInt(29) + 1;
                //int trifoi1 = 29;
                int trifoi2 = random.nextInt(30) + 1;
                //int trifoi2 = 30;
                while (trifoi2 <= trifoi1) {
                    trifoi2 = random.nextInt(30) + 1;
                }
                for (int i = 0; i < 30; i++) {
                    stelute.add(i, "");
                }
                stelute.add(trifoi1, "☆");
                stelute.add(trifoi2, "☆");

                ArrayList<Integer> myNumbers = bilete(numarAleatorii, winningNumbers, odds, stelute);
                System.out.println("--------------------------------");
                int castigRunda = printWinnings(myNumbers, winningNumbers, miza, trifoi1, trifoi2);
                //trifoi();
                balanta += castigRunda - (miza * numarAleatorii);

            }
        }
    }

    public static int printWinnings(ArrayList<Integer> allMyNumbers, ArrayList<Integer> winningNumbers, int miza, int trifoi1, int trifoi2) {
        int castigTotal = 0;
        ArrayList<Integer> castiguri = new ArrayList<>();
        for (int i = 0; i < allMyNumbers.size(); i += 6) {
            ArrayList<Integer> aleatorie = new ArrayList<>();
            for (int j = 0; j <= 5; j++) {
                aleatorie.add(allMyNumbers.get(i + j));
            }
            ArrayList<Integer> index = compareArrays(winningNumbers, aleatorie);
            if (index.contains(-1)) {
                System.out.println("Biletul " + (((i + 1) / 6) + 1) + " - 0 lei");
                castiguri.add(0);
            } else {
                int max = Collections.max(index);
                int castig = 0;
                //System.out.println(index);
                if (index.contains(trifoi1 + 4) && index.contains(trifoi2 + 4)) {
                    castig = (odds.get(max - 5) * miza) * 3;
                    //System.out.println("doi trifoi " + trifoi1 + " si " + trifoi2);
                } else if (index.contains(trifoi1 + 4) || index.contains(trifoi2 + 4)) {
                    castig = (odds.get(max - 5) * miza) * 2;
                    //System.out.println("un trifoi " + trifoi1 + " sau " + trifoi2);

                } else if (!(index.contains(trifoi1 + 4) && index.contains(trifoi2 + 4)) && (index.contains(trifoi1 + 4) || index.contains(trifoi2 + 4))) {
                    castig = odds.get(max - 5) * miza;
                }
                castiguri.add(castig);
                castigTotal += castig;

                System.out.println("Biletul " + (((i + 1) / 6) + 1) + " - " + castig + " lei");
            }
        }
        System.out.println("--------------------------------");
        if (castigTotal > 0) {
            System.out.println("Felicitari, ati castigat " + castigTotal + " lei");
        } else{
            System.out.println("Necastigator!");
        }
        int maxCastig = Collections.max(castiguri);
        int nrBilet = castiguri.indexOf(Collections.max(castiguri));
        if (maxCastig != 0) {
            System.out.println("Cel mai mult ati castigat pe biletul " + (nrBilet + 1) + ": " + maxCastig + " lei");
        }
        return castigTotal;
    }

    public static ArrayList<Integer> bilete(int nrAleatorii, ArrayList<Integer> winningNumbers, ArrayList<Integer> odds, ArrayList<String> stelute) throws InterruptedException {
        ArrayList<Integer> allMyNumbers = new ArrayList<>();
        for (int i = 1; i <= nrAleatorii; i++) {
            ArrayList<Integer> Aleatorie = aleatorie();
            allMyNumbers.addAll(Aleatorie);
            Collections.sort(Aleatorie);
            System.out.println("Bilet " + i + ": " + Aleatorie);
        }
        printWinningWithOdds(winningNumbers, odds, allMyNumbers, stelute);
        return allMyNumbers;
    }


    public static ArrayList<Integer> drawWinningNumbers() {
        ArrayList<Integer> winningNumbers = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 35; i++) {
            while (true) {
                int winningNumber = random.nextInt(48) + 1;
                if (!winningNumbers.contains(winningNumber)) {
                    winningNumbers.add(winningNumber);
                    break;
                }
            }
        }
        return winningNumbers;
    }

    public static void printWinningWithOdds(ArrayList<Integer> winningNumbers, ArrayList<Integer> odds, ArrayList<Integer> myNumbers, ArrayList<String> stelute) throws InterruptedException {
        ArrayList<Integer> index = compareArrays(winningNumbers, myNumbers);
        int sleep = 500;

        for (int i = 0; i < 35; i++) {
            if (i < 5) {
                if (index.contains(i)) {
                    System.out.println(" " + (i + 1) + ".  " + "\u001B[32m" + winningNumbers.get(i) + "          X" + "\u001B[0m");
                    Thread.sleep(sleep);
                } else {
                    System.out.println(" " + (i + 1) + ".  " + winningNumbers.get(i));
                    Thread.sleep(sleep);
                }
            } else {
                if (i < 9) {
                    if (index.contains(i)) {
                        System.out.println(" " + (i + 1) + ".  " + stelute.get(i - 4) + "\u001B[32m" + winningNumbers.get(i) + " - " + odds.get(i - 5) + "  X" + "\u001B[0m");
                        Thread.sleep(sleep);
                    } else {
                        System.out.println(" " + (i + 1) + ".  " + stelute.get(i - 4) + winningNumbers.get(i) + " - " + odds.get(i - 5));
                        Thread.sleep(sleep);
                    }
                } else {
                    if (index.contains(i)) {
                        System.out.println((i + 1) + ".  " + stelute.get(i - 4) + "\u001B[32m" + winningNumbers.get(i) + " - " + odds.get(i - 5) + "  X" + "\u001B[0m");
                        Thread.sleep(sleep);
                    } else {
                        System.out.println((i + 1) + ".  " + stelute.get(i - 4) + winningNumbers.get(i) + " - " + odds.get(i - 5));
                        Thread.sleep(sleep);
                    }
                }
            }
        }
    }


    public static ArrayList<Integer> aleatorie() {
        ArrayList<Integer> aleatorie = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            while (true) {
                int myNumber = random.nextInt(48) + 1;
                if (!aleatorie.contains(myNumber)) {
                    aleatorie.add(myNumber);
                    break;
                }
            }
        }
        return aleatorie;
    }

    public static ArrayList<Integer> compareArrays(ArrayList<Integer> winningNumbers, ArrayList<Integer> myNumbers) {
        ArrayList<Integer> index = new ArrayList<>();
        for (int i = 0; i < myNumbers.size(); i++) {
            index.add(winningNumbers.indexOf(myNumbers.get(i)));
        }
        return index;
    }
}