package view;

import java.awt.*;

import model.*;

public class CountPane extends AbstractDisplayPane {
	private static final long serialVersionUID = -7503377039578042533L;

	public CountPane(Model model) {
		super(model);
		setSize(200, 200);
	}

	public void drawCar(/*Graphics g*/Graphics graphics, Location location, Color color)
	{
		Dimension size;
        Image carParkImage;
		if (carParkImage == null) {
            return;
        }

        Dimension currentSize = getSize();
        if (size.equals(currentSize)) {
            graphics.drawImage(carParkImage, 0, 0, null);
        }
        else {
            // Rescale the previous image.
            graphics.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
        }
		
		graphics.setColor(color);	
        graphics.fillRect(
                location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                60 + location.getPlace() * 10,
                20 - 1,
                10 - 1);

		//originele code
//		int amount=getModel().getAmount();
//		boolean ready=false;
//		int counter=1;
//
//		g.setColor(Color.WHITE);
//		g.fillRect(0, 0, 200, 200);
//		g.setColor(Color.RED);
//
//		for(int y=20;y<180 && !ready; y+=5)
//		{
//			for(int x=20;x<170 && !ready; x+=5)
//			{
//				ready=counter>amount;
//				counter++;
//				if (!ready) g.fillRect(x, y, 4, 4);
//
//			}
//		}
	}

}
