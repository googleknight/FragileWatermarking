package fragile;


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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;


public class FrontEnd {

	protected Shell shlFargileWatermarking;
	BufferedImage bwimage;
	BufferedImage watermark;
	BufferedImage outputimage;
	Label label;
	File cover;
	int waterflg=0,coverflg=0;
	private Text text;
	
	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FrontEnd window = new FrontEnd();
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
		centerWindow(shlFargileWatermarking);
		
		label = new Label(shlFargileWatermarking, SWT.NONE);
		label.setEnabled(false);
		label.setBounds(327, 279, 142, 15);
		label.setText("<--Copy the secret key");
		shlFargileWatermarking.open();
		shlFargileWatermarking.layout();
		while (!shlFargileWatermarking.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlFargileWatermarking = new Shell();
		shlFargileWatermarking.setSize(505, 411);
		shlFargileWatermarking.setText("Watermarking");

		Button btnOpenWatermark = new Button(shlFargileWatermarking, SWT.NONE);
		btnOpenWatermark.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser chooser = new JFileChooser(new File("D:\\"));
				chooser.setDialogTitle("Open a watermark");
				chooser.setFileFilter(new FileTypeFilter(".png", "PNG file"));
				chooser.setFileFilter(new FileTypeFilter(".jpg", "JPEG file"));
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					waterflg=1;
					File img = chooser.getSelectedFile();
					makeblackwhite(img);
				}
			}
		});
		btnOpenWatermark.setBounds(166, 107, 142, 25);
		btnOpenWatermark.setText("Open Watermark");

		
		Button btnOpenCoverImage = new Button(shlFargileWatermarking, SWT.NONE);
		btnOpenCoverImage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			if(waterflg!=1)
				JOptionPane.showMessageDialog(null, "Please select an image for watermark!!");
			else
				{
					JFileChooser chooser = new JFileChooser(new File("D:\\"));
					chooser.setDialogTitle("Open Cover image");
					chooser.setFileFilter(new FileTypeFilter(".png", "PNG file"));
					chooser.setFileFilter(new FileTypeFilter(".jpg", "JPEG file"));
					int returnVal = chooser.showOpenDialog(null);
					if (returnVal == JFileChooser.APPROVE_OPTION && waterflg==1) {
						coverflg=1;
						cover = chooser.getSelectedFile();
						try {
							encrypt(cover);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
				}
			}		
			}
		});
		btnOpenCoverImage.setBounds(166, 163, 142, 25);
		btnOpenCoverImage.setText("Open Cover Image");


		Button btnEncryptAndSave = new Button(shlFargileWatermarking, SWT.NONE);
		btnEncryptAndSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(waterflg!=1)
					JOptionPane.showMessageDialog(null, "Please select an image for watermark!!");
				else if(coverflg!=1)
					JOptionPane.showMessageDialog(null, "Please select the coverimage!!");
				else
				{
					JFileChooser fs=new JFileChooser(new File("D:\\"));
					fs.setDialogTitle("Save a file");
					fs.setFileFilter(new FileTypeFilter(".png","Image File"));
					int result=fs.showSaveDialog(null);
					if(result==JFileChooser.APPROVE_OPTION)
					{
						File fi=fs.getSelectedFile();
						File bw=new File("D:\\tempblackwhite.png");
						try {
							
							ImageIO.write(outputimage, "png", fi);
							ImageIO.write(bwimage, "png", bw);
							int w,h;
							if(bwimage.getWidth()<100)
								w=2;
							else if(bwimage.getWidth()<1000)
								w=3;
							else
								w=4;
							if(bwimage.getHeight()<100)
								h=2;
							else if(bwimage.getHeight()<1000)
								h=3;
							else
								h=4;
							text.setText("10"+bwimage.getWidth()+bwimage.getHeight()+w+h);
							label.setEnabled(true);
						} catch (IOException e2) {
							JOptionPane.showMessageDialog(null, e2.getMessage());
						}
				}
			}
			}
		});
		btnEncryptAndSave.setText("Encrypt and save");
		btnEncryptAndSave.setBounds(166, 218, 142, 25);
		
		Label lblYourSecretKey = new Label(shlFargileWatermarking, SWT.NONE);
		lblYourSecretKey.setBounds(44, 279, 98, 15);
		lblYourSecretKey.setText("Your Secret Key");
		
		text = new Text(shlFargileWatermarking, SWT.BORDER);
		text.setBounds(166, 276, 142, 21);
		
		Label lblEmbedding = new Label(shlFargileWatermarking, SWT.NONE);
		lblEmbedding.setAlignment(SWT.CENTER);
		lblEmbedding.setFont(SWTResourceManager.getFont("Segoe UI", 24, SWT.BOLD));
		lblEmbedding.setBounds(0, 28, 479, 55);
		lblEmbedding.setText("Watermark Embedding ");
		
		Button btnBack = new Button(shlFargileWatermarking, SWT.NONE);
		btnBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					shlFargileWatermarking.dispose();
					MainPage window = new MainPage();
					window.open();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnBack.setBounds(166, 317, 142, 25);
		btnBack.setText("Back");
		

	}

	
	
	public void makeblackwhite(File img)
	{
		try {
			watermark = ImageIO.read(img);
			bwimage = new BufferedImage(watermark.getWidth(), watermark.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
			int threshold;
			bwimage.getGraphics().drawImage(watermark, 0, 0, null);
			    WritableRaster raster = bwimage.getRaster();
			    int[] pixels = new int[watermark.getWidth()];
			    for (int y = 0; y < watermark.getHeight(); y++) {
			        raster.getPixels(0, y, watermark.getWidth(), 1, pixels);
			        int min=pixels[0],max=pixels[0];
			        for (int i = 1; i < pixels.length; i++)
			        {
			        	if(pixels[i]>max)
			        		max=pixels[i];
			        	if(pixels[i]<min)
			        		min=pixels[i];
			        }
			        threshold=(min+max)/2;
			        for (int i = 0; i < pixels.length; i++) {
			            if (pixels[i] < threshold) 
			            	pixels[i] = 0;
			            else
			            	pixels[i] = 255;
			        }
			        raster.setPixels(0, y, watermark.getWidth(), 1, pixels);
			    }
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2.getMessage());
		}
		
	}

	
	
		
	public void encrypt(File cover) throws Exception {
		int w=watermark.getWidth(),h=watermark.getHeight();
		BufferedImage bimg = ImageIO.read(cover);
		outputimage = new BufferedImage(bimg.getWidth(),bimg.getHeight(), BufferedImage.TYPE_INT_ARGB);
		if(w*h>(bimg.getHeight()*bimg.getWidth()*4))
		{
			JOptionPane.showMessageDialog(null, "Please select small watermark image and bigger cover image.");
			waterflg=0;
			coverflg=0;
			return;
		}
		WritableRaster raster = bwimage.getRaster();
	    int[] pixels = new int[watermark.getWidth()];   
		
		int pixel[][] = new int[bimg.getWidth()][bimg.getHeight()];
		for (int i = 0; i < bimg.getWidth(); i++)
			for (int j = 0; j < bimg.getHeight(); j++) {
				pixel[i][j] = bimg.getRGB(i, j);
			}
		int temp;
		int[] pos = new int[4];
		int outw=0,outh=0,flag=0;
		raster.getPixels(0, 0, watermark.getWidth(), 1, pixels);
		for (int i = 0; i < bimg.getWidth(); i++)
			for (int j = 0; j < bimg.getHeight(); j++)
			{
				temp = pixel[i][j];
				pos[0] = (temp >> 24) & 0x00000001;
				pos[1] = (temp >> 16) & 0x00000001;
				pos[2] = (temp >> 8) & 0x00000001;
				pos[3] = (temp) & 0x00000001;
				int val = 0, bit = 0;
				for (int k = 0; k < 4; k++) 
				{
					if(flag!=1)
					{
						if(outw<w && outh<h)
						{						
							if (pixels[outw]==255)
								 bit=1;
						    else
								 bit=0;
							outw++;
						}
						else if(outw>=w && outh<h)
						{
							outw=0;
							outh++;
							if(outh<h)
								raster.getPixels(0,outh, w, 1, pixels);
							else
								flag=1;
						}
					}
					switch (k) {
					case 3:
						val = 1;
						break;
					case 2:
						val = 256;
						break;
					case 1:
						val = 65536;
						break;
					case 0:
						val = 16777216;
						break;
					}
										
					if (bit == 0 && pos[k] == 1) 
						temp = temp & (~val);
					if (bit == 1 && pos[k] == 0)
						temp = temp ^ val;
				}
				outputimage.setRGB(i, j, temp);
			}
	}		
}
