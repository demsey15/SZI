package ViewLayer;

import java.awt.*;

import javax.swing.*;

//Okno zbieraj¹ce wszystkie panele

public class MainFrame extends JFrame {

	int wysokosc;
	int szerokosc;
	
	public MainFrame(){
		
		obliczWielkoscOkna();
		setSize(szerokosc, wysokosc);
		setVisible(true);



		validate();


		
	}
	
	public void obliczWielkoscOkna() {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		wysokosc = screen.height;
		szerokosc = screen.width;
		
		if (wysokosc / 9 == szerokosc / 16) {
			System.out.println("Proporcje siê zgadzaja");
			setSize(szerokosc, wysokosc);
		}
		else {
			int p = szerokosc / 16;
			if ( p*9 <= wysokosc )
			{
				System.out.println("Proporcje sie nie zgadzaja, ale jest ok");
				wysokosc = p * 9;
			}
			else {
				System.out.println("Rozdzielczosc nieprawidlowa!");
			}
			
		}
	}
}
