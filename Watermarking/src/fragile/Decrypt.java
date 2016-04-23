package fragile;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class Decrypt {

	protected Shell shlFragileWatermarkingExtraction;
	BufferedImage outputimage;
	private Text textcontent;
	int wflag=0,keyflag=0;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Decrypt window = new Decrypt();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void centerWindow(Shell shell) {

        Rectangle bds = shell.getDisplay().getBounds();

        Point p = shell.getSize();

        int nLeft = (bds.width - p.x) / 2;
        int nTop = (bds.height - p.y) / 2;

        shell.setBounds(nLeft, nTop, p.x, p.y);
    }

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		centerWindow(shlFragileWatermarkingExtraction);
		shlFragileWatermarkingExtraction.open();
		shlFragileWatermarkingExtraction.layout();
		while (!shlFragileWatermarkingExtraction.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlFragileWatermarkingExtraction = new Shell();
		shlFragileWatermarkingExtraction.setSize(469, 392);
		shlFragileWatermarkingExtraction.setText("Watermarking");
		
		Button btnOpenDecryptImage = new Button(shlFragileWatermarkingExtraction, SWT.NONE);
		btnOpenDecryptImage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(textcontent.getText()=="")
					JOptionPane.showMessageDialog(null, "Please enter they key!");
				else
				{
					JFileChooser chooser = new JFileChooser(new File("D:\\"));
					chooser.setDialogTitle("Open a watermark");
					chooser.setFileFilter(new FileTypeFilter(".png", "PNG file"));
					int returnVal = chooser.showOpenDialog(null);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						wflag=1;
						File img = chooser.getSelectedFile();
						try{
							decrypt(img);
						}
						catch(Exception e1)
						{ e1.printStackTrace();}
					}
				}
			}
		});
		btnOpenDecryptImage.setBounds(195, 198, 130, 25);
		btnOpenDecryptImage.setText("Open Encrypted Image");
		
		Button btnNewButton = new Button(shlFragileWatermarkingExtraction, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(textcontent.getText()=="")
					JOptionPane.showMessageDialog(null, "Please enter they key!");
				else if(wflag!=1)
					JOptionPane.showMessageDialog(null, "Please select the image!");
				else
				{
					JFileChooser fs=new JFileChooser(new File("D:\\"));
					fs.setDialogTitle("Save a file");
					fs.setFileFilter(new FileTypeFilter(".png","Image File"));
					int result=fs.showSaveDialog(null);
					if(result==JFileChooser.APPROVE_OPTION)
					{
						File fi=fs.getSelectedFile();
						try {
							
							ImageIO.write(outputimage, "png", fi);
						} catch (IOException e2) {
	
							JOptionPane.showMessageDialog(null, e2.getMessage());
						}
					}
				}
			}
		});
		btnNewButton.setBounds(195, 250, 130, 25);
		btnNewButton.setText("Extract and save");
		
		textcontent = new Text(shlFragileWatermarkingExtraction, SWT.BORDER);
		textcontent.setBounds(195, 146, 130, 21);
		
		Label lblEnterYourSecret = new Label(shlFragileWatermarkingExtraction, SWT.NONE);
		lblEnterYourSecret.setBounds(52, 149, 119, 15);
		lblEnterYourSecret.setText("Enter your Secret Key");
		
		Button btnNewButton_1 = new Button(shlFragileWatermarkingExtraction, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					shlFragileWatermarkingExtraction.dispose();
					MainPage window = new MainPage();
					window.open();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(195, 302, 130, 25);
		btnNewButton_1.setText("Back");
		
		Label lblWatermarkExtraction = new Label(shlFragileWatermarkingExtraction, SWT.NONE);
		lblWatermarkExtraction.setAlignment(SWT.CENTER);
		lblWatermarkExtraction.setFont(SWTResourceManager.getFont("Segoe UI", 24, SWT.BOLD));
		lblWatermarkExtraction.setBounds(52, 38, 328, 58);
		lblWatermarkExtraction.setText("Watermark Extraction");
		
		


	}
	public void decrypt(File img)
	{
		
		try{
			long key=Long.parseLong(textcontent.getText());
			int lenh=(int)(key%10)+2;
			int lenw=(int)(key%100)/10;
			int h=(int)(key%((int)Math.pow(10,lenh)));
			h/=100;
			int w=(int)(key%Math.pow(10,lenh+lenw));
			w=w/((int)Math.pow(10,lenh));
			BufferedImage bimg = ImageIO.read(img);
			outputimage = new BufferedImage(w,h,BufferedImage.TYPE_BYTE_GRAY);
			int pixel[][] = new int[bimg.getWidth()][bimg.getHeight()];
			for (int i = 0; i < bimg.getWidth(); i++)
				for (int j = 0; j < bimg.getHeight(); j++) {
					pixel[i][j] = bimg.getRGB(i, j);
				}
			int outw=0,outh=0,flag=0;
			outputimage.getGraphics().drawImage(outputimage, 0, 0, null);
		    WritableRaster raster = outputimage.getRaster();
		    int[] pixels = new int[w];
		    raster.getPixels(0,outh, w, 1, pixels);
			for (int i = 0; i < bimg.getWidth() && flag!=1; i++)
			{
				for (int j = 0; j < bimg.getHeight() && flag!=1; j++)
				{
					Color c1 = new Color(pixel[i][j], true);
					int red1 = c1.getRed();
					int green1 = c1.getGreen();
					int blue1 = c1.getBlue();
					int alpha1 = c1.getAlpha();
					
					int[] c = new int[4];
					c[0] = alpha1 & 0x01;
					c[1] = red1 & 0x01;
					c[2] = green1 & 0x01;
					c[3] = blue1 & 0x01;
					for(int k=0;k<4;k++)
					{
						
						if(outw<w && outh<h)
						{
							
							if (c[k]==0)
							 {	 
								 pixels[outw]=0;
								 
							 }
							 else
							 {
								 
								 pixels[outw] = 255;
							 }
							 outw++;
						}
						else if(outw>=w && outh<h)
						{
							raster.setPixels(0, outh, w, 1, pixels);
							
							outw=0;
							outh++;
							if(outh<h)
								raster.getPixels(0,outh, w, 1, pixels);
							else
							{
								flag=1;
								break;
							}
						}
						else if(flag==1)
						{	
							break;
						}
					}
				}
			}
		}
		catch(Exception e3)
		{
			e3.printStackTrace();
		}
	}
}
