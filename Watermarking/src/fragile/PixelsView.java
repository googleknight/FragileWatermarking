package fragile;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class PixelsView {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PixelsView window = new PixelsView();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
	
		Button btnOpen = new Button(shell, SWT.NONE);
		btnOpen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser chooser = new JFileChooser(new File("H:\\Desktop\\image\\"));
				chooser.setDialogTitle("Open a watermark");
				chooser.setFileFilter(new FileTypeFilter(".png", "PNG file"));
				chooser.setFileFilter(new FileTypeFilter(".jpg", "JPEG file"));
				chooser.setFileFilter(new FileTypeFilter(".bmp", "BMP file"));
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File img = chooser.getSelectedFile();
					makeblackwhite(img);
					
				}
			}
		});
		btnOpen.setBounds(161, 60, 75, 25);
		btnOpen.setText("open");
	}
		public void makeblackwhite(File img)
		{
			
				BufferedImage bimg;
				try {
					bimg = ImageIO.read(img);
				int pixel[][] = new int[bimg.getWidth()][bimg.getHeight()];

				for (int i = 0; i < bimg.getWidth(); i++)
					for (int j = 0; j < bimg.getHeight(); j++) {
						pixel[i][j] = bimg.getRGB(i, j);
					}
				String content=" ";
				int count=0;
				for (int i = 0; i < bimg.getWidth(); i++) {
					
					for (int j = 0; j < bimg.getHeight(); j++) {
						Color c1 = new Color(pixel[i][j], true);
						int red1 = c1.getRed();
						int green1 = c1.getGreen();
						int blue1 = c1.getBlue();
						int alpha1 = c1.getAlpha();
						count++;
						content=content+"\n  Pixel "+i+" "+j+": "+Integer.toBinaryString(red1)+" "+Integer.toBinaryString(green1)
								+" "+Integer.toBinaryString(blue1)+" "+Integer.toBinaryString(alpha1);
						
					}
					if (count>5000)
						break;
				}


				File file = new File("H:\\Desktop\\filename.txt");

				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(content);
				bw.close();


				} catch (IOException e) {
					e.printStackTrace();
				}
				
		}
}

