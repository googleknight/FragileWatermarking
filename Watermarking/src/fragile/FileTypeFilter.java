package fragile;

import java.io.File;

import javax.swing.filechooser.*;

public class FileTypeFilter extends FileFilter{
	private final String extension;
	private final String description;
	public FileTypeFilter(String extension,String description) {
		this.extension=extension;
		this.description=description;
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean accept(File f) {
		if(f.isDirectory())
			return true;
		return f.getName().endsWith(extension);
	}

	@Override
	public String getDescription() {
		return description+String.format(" (*%s)",extension);
	}

}
