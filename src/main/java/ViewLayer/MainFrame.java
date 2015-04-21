package ViewLayer;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

//Okno zbieraj¹ce wszystkie panele

public class MainFrame extends JFrame {

	int wysokosc;
	int szerokosc;
	JPanel leftPanel, northPanel;
	OrdersPanel newOrdersPanel;
	OrdersPanel readyMealPanel;
	OrdersPanel handedOnPlatePanel;
	MenuPanel menuPanel;
	SettingsFrame settingsPanel;

	public MainFrame(){
		
		obliczWielkoscOkna();
		setSize(szerokosc, wysokosc);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		//gorny panel
		northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
		add(northPanel, BorderLayout.EAST);

		//tytul
		JLabel title = new JLabel(" INTELIGENTNY KELNER");
		title.setFont(new Font("Serif", Font.BOLD, 36));
		northPanel.add(title);

		//ustawienia algorytmow
		settingsPanel = new SettingsFrame();
		northPanel.add(settingsPanel);

		add(northPanel, BorderLayout.PAGE_START);

		leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		this.add(leftPanel, BorderLayout.WEST);

		//utworzenie przykladowej listy zamowien
		String [][] orderList = {{"Zamowienie 1", "1"}, {"Zamowienie 2", "2"}};
		newOrdersPanel = new OrdersPanel(orderList, "Zamowienia:");
		leftPanel.add(newOrdersPanel);

		//przetestowanie odswie¿alnoœci listy zamowien
		String [][] orderList2 = {{"Testtowe 1", "1"}, {"Zamowienie 2", "2"}};
		newOrdersPanel.setOrdersList(orderList2);

		//lista gotowych posilkow
		readyMealPanel = new OrdersPanel(orderList2, "Gotowe do zabrania:");
		leftPanel.add(readyMealPanel);

		//lsita posilkow na tacy kelnera
		handedOnPlatePanel = new OrdersPanel(orderList, "Na tacy kelnera:");
		leftPanel.add(handedOnPlatePanel);

		//Menu
		String [][] listaMenu = {{"Danie 1"}, {"Danie 2"}, {"Danie 3"}, {"Danie 4"}, {"Danie 5"}};
		menuPanel = new MenuPanel(listaMenu);
		add(menuPanel, BorderLayout.EAST);


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
