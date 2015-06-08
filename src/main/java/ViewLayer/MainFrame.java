package ViewLayer;
import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.Control;
import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.Map;
import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.OrdersService;

import java.awt.*;
import java.io.IOException;
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
	MapPanel mapPanel;
	static public MainFrame instance;

	public static MainFrame getInstance(){
		if (instance == null){
			return new MainFrame();
		}else {
			return instance;
		}

	}
	private MainFrame(){

		instance = this;
		obliczWielkoscOkna();
		this.pack();
		this.setAlwaysOnTop(true);
		//setSize(szerokosc, wysokosc);
		//setSize(szerokosc/8 *2 + mapPanel.wysokoscPola*21, wysokosc);

		System.out.print("Szerokosc to: " + szerokosc);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setBackground(Color.white);

		//Mapa
		MapPanel mapPanel = new MapPanel(szerokosc,wysokosc);
		add(mapPanel, BorderLayout.CENTER);

		//gorny panel
		northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());//Layout(northPanel, BoxLayout.X_AXIS));
		northPanel.setPreferredSize(new Dimension(szerokosc,wysokosc/16));
		northPanel.setBackground(Color.white);
		add(northPanel, BorderLayout.EAST);

		//tytul
		JLabel title = new JLabel(" INTELIGENTNY KELNER");
		title.setFont(new Font("Serif", Font.BOLD, 36));
		title.setPreferredSize(new Dimension(szerokosc / 3, wysokosc / 16));
		northPanel.add(title, BorderLayout.WEST);

		//ustawienia algorytmow
		settingsPanel = new SettingsFrame(mapPanel);
		northPanel.add(settingsPanel, BorderLayout.CENTER);

		add(northPanel, BorderLayout.PAGE_START);

		leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		this.add(leftPanel, BorderLayout.WEST);

		//utworzenie przykladowej listy zamowien
		String [][] orderList = {{"", ""}};
		newOrdersPanel = new OrdersPanel(orderList, "Zamowienia:", szerokosc, wysokosc);
		leftPanel.add(newOrdersPanel);

		//przetestowanie odswie¿alnoœci listy zamowien
		String [][] orderList2 = {{"", ""}};
		newOrdersPanel.setOrdersList(orderList2);

		//lista gotowych posilkow
		readyMealPanel = new OrdersPanel(orderList2, "Gotowe do zabrania:", szerokosc, wysokosc);
		leftPanel.add(readyMealPanel);

		//lsita posilkow na tacy kelnera
		handedOnPlatePanel = new OrdersPanel(orderList, "Na tacy kelnera:", szerokosc, wysokosc);
		leftPanel.add(handedOnPlatePanel);

		//Menu
		String [][] listaMenu = new String[0][];
		try {
			listaMenu = OrdersService.getInstance().getMenuToDisplay();
		} catch (IOException e) {
			e.printStackTrace();
		}

		menuPanel = new MenuPanel(listaMenu, szerokosc,wysokosc);
		add(menuPanel, BorderLayout.EAST);

		pack();
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

	public OrdersPanel getNewOrdersPanel(){
		return newOrdersPanel;
	}

	public OrdersPanel getReadyMealPanel(){
		return readyMealPanel;
	}

	public OrdersPanel getHandedOnPlatePanel(){
		return handedOnPlatePanel;
	}
}
