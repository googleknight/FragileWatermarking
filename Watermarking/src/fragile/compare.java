package fragile;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

public class compare {

	protected Shell shlCompareImages;
	private File img1,img2;
	private Text bertext;
	private Text psnrtext;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			compare window = new compare();
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
		centerWindow(shlCompareImages);
		
		Button btnBack = new Button(shlCompareImages, SWT.NONE);
		btnBack.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					shlCompareImages.dispose();
					MainPage window = new MainPage();
					window.open();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			
			
			}
		});
		btnBack.setBounds(243, 304, 75, 25);
		btnBack.setText("Back");
		
		Label lblBitErrorRatiober = new Label(shlCompareImages, SWT.NONE);
		lblBitErrorRatiober.setBounds(127, 200, 108, 25);
		lblBitErrorRatiober.setText("Bit Error Ratio(BER)");
		
		Label lblPeakToSignal = new Label(shlCompareImages, SWT.NONE);
		lblPeakToSignal.setBounds(127, 249, 183, 25);
		lblPeakToSignal.setText("Peak Signal to Noise Ratio(PSNR)");
		
		bertext = new Text(shlCompareImages, SWT.BORDER);
		bertext.setBounds(338, 197, 76, 21);
		
		psnrtext = new Text(shlCompareImages, SWT.BORDER);
		psnrtext.setBounds(338, 246, 76, 21);
		shlCompareImages.open();
		shlCompareImages.layout();
		while (!shlCompareImages.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlCompareImages = new Shell();
		shlCompareImages.setSize(581, 383);
		shlCompareImages.setText("Compare Images");
		
		Label lblCompare = new Label(shlCompareImages, SWT.NONE);
		lblCompare.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
		lblCompare.setBounds(210, 43, 169, 41);
		lblCompare.setText("Compare Images");
		
		Button btnSelectImage = new Button(shlCompareImages, SWT.NONE);
		btnSelectImage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser chooser = new JFileChooser(new File("D:\\"));
				chooser.setDialogTitle("Open a watermark");
				chooser.setFileFilter(new FileTypeFilter(".png", "PNG file"));
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					img1 = chooser.getSelectedFile();
				}
			}
		});
		btnSelectImage.setBounds(127, 133, 97, 25);
		btnSelectImage.setText("Select Image 1");
		
		Button btnSelectImage_1 = new Button(shlCompareImages, SWT.NONE);
		btnSelectImage_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser chooser = new JFileChooser(new File("D:\\"));
				chooser.setDialogTitle("Open a watermark");
				chooser.setFileFilter(new FileTypeFilter(".png", "PNG file"));
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					img2 = chooser.getSelectedFile();
					solve(img1,img2);
				}
			}
		});
		btnSelectImage_1.setText("Select Image 2");
		btnSelectImage_1.setBounds(338, 133, 97, 25);
	}
	private void solve(File img1,File img2)
	{
		BufferedImage bimg1;
		BufferedImage bimg2;
		double count=0;
		double mse=0;
		try {
			bimg1 = ImageIO.read(img1);
			bimg2= ImageIO.read(img2);
			WritableRaster raster1=bimg1.getRaster();
			WritableRaster raster2=bimg2.getRaster();
			int[] pixels1 = new int[bimg1.getWidth()];
			int[] pixels2 = new int[bimg2.getWidth()];
			for (int i = 0; i < bimg1.getHeight(); i++) {
				raster1.getPixels(0, i, bimg1.getWidth(), 1, pixels1);
				raster2.getPixels(0, i, bimg2.getWidth(), 1, pixels2);
				for(int j=0;j<bimg1.getWidth();j++)
				{
					if(pixels1[j]==pixels2[j])
						count++;
					else
						mse++;
				}
			}
			double area=bimg1.getHeight()*bimg1.getWidth();
			mse=mse/area;
			double psnr;
			psnr=10*Math.log10(Math.pow(255,2)/mse);
			double bit_error=(area-count)/area;
			bertext.setText(""+bit_error);
			psnrtext.setText(""+psnr);			
		}
		catch(Exception e0)
		{
			JOptionPane.showMessageDialog(null, e0);
		}	
		
	}
}
