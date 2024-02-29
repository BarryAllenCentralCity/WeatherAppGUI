import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.json.simple.JSONObject;

public class WeatherAppGUI extends JFrame {
	
	private JSONObject weatherData;
	
	public WeatherAppGUI() {
		// setting up and adding title
		super("Weather App");
		
		// configuring end on close
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// setting size of GUI
		setSize(450, 650);
		
		// centering the GUI
		setLocationRelativeTo(null);
		
		// layout manager - null - manually position our components within GUI
		setLayout(null);
		
		// prevent resize
		 setResizable(false);
		
		 addGUIComponents();
	}
	
	private void addGUIComponents() {
		
		
		// search Field
		JTextField searchTextField = new JTextField();
		
		// location and size of search field
		searchTextField.setBounds(15, 15, 351, 45);
		
		// font style and size
		searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
		
		add(searchTextField);
		
		
		// weather Image
		JLabel weatherImage = new JLabel(loadImage("src/assets/cloudy.png"));
		weatherImage.setBounds(0, 125, 450, 217);
		
		add(weatherImage);
		
		// temperature Text
		JLabel temperatureText = new JLabel("10C");
		
		temperatureText.setBounds(0, 350, 450, 54);
		temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));
		
		temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
		add(temperatureText);
		
		// weather desc
		
		JLabel weatherDesc = new JLabel("Cloudy");
		
		weatherDesc.setBounds(0, 405, 450, 36);
		weatherDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
		weatherDesc.setHorizontalAlignment(SwingConstants.CENTER);
		
		add(weatherDesc);
		
		// humidity Image
		JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
		humidityImage.setBounds(15, 500, 74, 66);
		
		add(humidityImage);
		
		// humidity Text
		JLabel humidityText = new JLabel("<html><b>Humidity</b> 80%</html>");
		humidityText.setBounds(90, 500, 85, 55);
		humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
		
		add(humidityText);
		
		// windspeed Image
		JLabel windspeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
		windspeedImage.setBounds(220, 500, 74, 66);
		
		add(windspeedImage);
		
		// windspeed text
		JLabel windspeedText = new JLabel("<html><b>Windspeed</b> 15km/h</html>");
		windspeedText.setBounds(310, 500, 85, 55);
		windspeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
		
		add(windspeedText);
		
		// search button 
		JButton searchButton = new JButton(loadImage("src/assets/search.png"));
				
		// changing cursor while hovering on button
		searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		searchButton.setBounds(375, 13, 47, 45);
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// get location from user
				String userInput = searchTextField.getText();	
				
				// validate input - remove whitesapce - non-empty text
				if(userInput.replaceAll("\\s", "").length() <= 0) {
					return;
				}
				
				// retrieve weather data
				weatherData = WeatherApp.getWeatherData(userInput);
				
				// update GUI
				
				// update Image
				String weatherCondition = (String) weatherData.get("weather_condition");
				switch(weatherCondition) {
					case "Clear":
						weatherImage.setIcon(loadImage("src/assets/clear.png"));
						break;
					case "Cloudy":
						weatherImage.setIcon(loadImage("src/assets/cloudy.png"));
						break;
					case "Rain":
						weatherImage.setIcon(loadImage("src/assets/rain.png"));
						break;
					case "Snow":
						weatherImage.setIcon(loadImage("src/assets/snow.png"));
						break;	
				}
				
				// update temperature text
				double temperature	= (double) weatherData.get("temperature");
				temperatureText.setText(temperature + "C");
				
				// update weather Condition Text
				weatherDesc.setText(weatherCondition);
				
				// update humidity
				long humidity = (long) weatherData.get("humidity");
				humidityText.setText("<html><b>Humidity</b> "+ humidity + "%</html>");
				
				// update windspeed text 
				double windspeed = (double) weatherData.get("windspeed");
				windspeedText.setText("<html><b>Windspeed</b> "+ windspeed + "km/h</html>");
				}
		});
		add(searchButton);
	}
	
	private ImageIcon loadImage(String resourcePath) {
		try {
			// reading Image from Path
			BufferedImage image = ImageIO.read(new File(resourcePath));
			
			return new ImageIcon(image);
		} catch (IOException e) {
			// handle exception
			e.printStackTrace();
		}
		System.out.println("Image Not Found");
		return null;
	}
}
