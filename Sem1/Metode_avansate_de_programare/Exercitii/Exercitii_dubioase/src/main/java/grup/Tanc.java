package grup;

public class Tanc {
    private int viata;

    public Tanc(int viata){
        this.viata = viata;
    }

    public void lupta() {   // aici vine, pierde 1000 hp si dupa ia de la repara inca 800, dar cum ala arunca eroare si idiotu asta o prinde, mai scade 200, si in cazul in care inca are peste 1000 viata se mai repeta procesul pana are sub 1000 hp
        try {
            viata = viata - 1000;
            repara();
        }
        catch (Exception e) {
            viata = viata - 200;
            if(viata >= 1000){
                lupta();
            }
        }
    }

    public void repara() throws Exception { // aici prinde mereu 800 hp
        try {
            viata = viata + 300;
            asteapta();
            viata = viata + 100;
        }
        catch (Exception e) {
            throw new Exception();
        }
        finally {
            viata = viata + 400;
        }
        viata = viata + 100;
    }

    public void asteapta() throws Exception {
        throw new Exception();
    }

    public int getViata() {
        return this.viata;
    }

    public static void main(String[] args) {
        Tanc t1 = new Tanc(2500); // 1500 2200 2100 1100 1800 1700 700 1400 1300 300 1000 900 500
        Tanc t2 = new Tanc(1700); // 900
        Tanc t3 = new Tanc(900); // -100 700 500
        Tanc[] grupa = {t1, t2, t3};
        for (Tanc t : grupa) {
            t.lupta();
            System.out.print(t.getViata() + " ");
        }
    }
}
