package fragile;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

public class MainPage {

	protected Shell shlWatermarking;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainPage window = new MainPage();
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
		centerWindow(shlWatermarking);
		
		Button btnCompareImages = new Button(shlWatermarking, SWT.NONE);
		btnCompareImages.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					shlWatermarking.dispose();
					compare window = new compare();
					window.open();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnCompareImages.setBounds(291, 125, 115, 25);
		btnCompareImages.setText("Compare Images");
		shlWatermarking.open();
		shlWatermarking.layout();
		while (!shlWatermarking.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlWatermarking = new Shell();
		shlWatermarking.setSize(450, 300);
		shlWatermarking.setText("Watermarking");
		
		Button btnEmbedAWatermark = new Button(shlWatermarking, SWT.NONE);
		btnEmbedAWatermark.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					shlWatermarking.dispose();
					FrontEnd window = new FrontEnd();
					window.open();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnEmbedAWatermark.setBounds(10, 125, 124, 25);
		btnEmbedAWatermark.setText("Embed a watermark");
		
		Button btnExtractWatermark = new Button(shlWatermarking, SWT.NONE);
		btnExtractWatermark.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					shlWatermarking.dispose();
					Decrypt window = new Decrypt();
					window.open();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnExtractWatermark.setBounds(148, 125, 124, 25);
		btnExtractWatermark.setText("Extract watermark");
		
		Label lblFragileWatermarking = new Label(shlWatermarking, SWT.CENTER);
		lblFragileWatermarking.setFont(SWTResourceManager.getFont("Segoe UI", 24, SWT.BOLD));
		lblFragileWatermarking.setBounds(45, 25, 342, 51);
		lblFragileWatermarking.setText("Fragile Watermarking");
		
		Button btnExit = new Button(shlWatermarking, SWT.NONE);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlWatermarking.dispose();
			}
		});
		btnExit.setBounds(160, 189, 99, 25);
		btnExit.setText("Exit");

	}
}
